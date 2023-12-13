package com.bn.t525_2.pojo;

import java.util.Objects;

public class Book {
    private Integer book_id;
    private String isbn;
    private String bookName;
    private Double price;
    private String publish_date;
    private String author;
    private String publisher;

    public Book() {
    }

    public Book(Integer book_id, String isbn, String book_name, Double price, String publish_date, String author, String publisher) {
        this.book_id = book_id;
        this.isbn = isbn;
        this.bookName= book_name;
        this.price = price;
        this.publish_date = publish_date;
        this.author = author;
        this.publisher = publisher;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBook_name() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", isbn='" + isbn + '\'' +
                ", book_name='" + bookName + '\'' +
                ", price=" + price +
                ", publish_date='" + publish_date + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(book_id, book.book_id) && Objects.equals(isbn, book.isbn) && Objects.equals(bookName, book.bookName) && Objects.equals(price, book.price) && Objects.equals(publish_date, book.publish_date) && Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, isbn, bookName, price, publish_date, author, publisher);
    }
}
