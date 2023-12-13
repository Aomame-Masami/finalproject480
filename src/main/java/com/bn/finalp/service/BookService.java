package com.bn.finalp.service;

import com.bn.finalp.mapper.BookMapper;
import com.bn.finalp.pojo.Book;
import com.bn.finalp.pojo.BookRepository;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookMapper bookMapper;

    private final BookRepository bookRepository;

    private List<String> isbn1000;
    private final String pythonServiceUrl;
    private final RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;

    public BookService(BookRepository bookRepository, RestTemplateBuilder restTemplateBuilder) {
        this.bookRepository = bookRepository;
        this.pythonServiceUrl = "http://localhost:8000";
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    private void init() {
        isbn1000 = getFirst1000Isbns();
    }
    public List<String> getFirst1000IsbnsMapper(){
        return bookMapper.selectFirst1000ISBNs();
    }
    public List<String> getFirst1000Isbns() {
        List<String> isbns = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            if (isbns.size() < 1000) {
                isbns.add(book.getIsbn());
            }
        });
        return isbns;
    }

    public List<String> getIsbn1000() {
        return isbn1000;
    }

    public void importCsvToRedis(String csvFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue; // Skip the header row
                }

                String[] data = line.split(";(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].replaceAll("^\"|\"$", ""); // Remove surrounding double quotes
                }

                if (data.length >= 8) {
                    Book book = new Book();
                    book.setIsbn(data[0]);
                    book.setBook_Title(data[1]);
                    book.setBook_Author(data[2]);

                    try {
                        book.setYear_Of_Publication(Integer.parseInt(data[3]));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing year of publication: " + data[3]);
                        continue;
                    }

                    book.setPublisher(data[4]);
                    book.setImage_UrlS(data[5]);
                    book.setImage_UrlM(data[6]);
                    book.setImage_UrlL(data[7]);
                    bookRepository.save(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    public boolean mightContainIsbn(BloomFilter<String> bloomFilter, String isbn) {
        return bloomFilter.mightContain(isbn);
    }

    public BloomFilter<String> createBloomFilter(String csvFilePath, int expectedInsertions) {
        BloomFilter<String> bloomFilter = BloomFilter.create(
                Funnels.unencodedCharsFunnel(),
                expectedInsertions
        );

        List<String> isbnList = bookMapper.selectAllISBNs();
        for (String isbn : isbnList) {
            bloomFilter.put(isbn);
        }
        return bloomFilter;
    }


    public byte[] serializeBloomFilter(BloomFilter<String> bloomFilter) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(bloomFilter);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error serializing Bloom Filter", e);
        }
    }

    public void storeBloomFilterInRedis(String key, BloomFilter<String> bloomFilter) {
        byte[] serializedData = serializeBloomFilter(bloomFilter);
        redisTemplate.opsForValue().set(key, serializedData);
    }
    public BloomFilter<String> deserializeBloomFilter(byte[] bytes) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (BloomFilter<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error deserializing Bloom Filter", e);
        }
    }

    public BloomFilter<String> getBloomFilterFromRedis(String key) {
        byte[] serializedData = redisTemplate.opsForValue().get(key);
        if (serializedData == null) {
            throw new IllegalStateException("Bloom Filter not found in Redis");
        }
        return deserializeBloomFilter(serializedData);
    }


    public List suggestBooks(int bookIndex, int topN) {
        String url = pythonServiceUrl + "/suggest_books";
        Map<String, Object> request = Map.of("book_index", bookIndex, "top_n", topN);

        ResponseEntity<List> response = restTemplate.postForEntity(url, request, List.class);
        return response.getBody();
    }


}