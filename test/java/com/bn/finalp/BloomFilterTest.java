package com.bn.finalp;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFilterTest {

    private BloomFilter<String> bloomFilter;

    @BeforeEach
    public void setup() {
        int expectedInsertions = 1000;
        double falsePositiveProbability = 0.01; // 1%

        bloomFilter = BloomFilter.create(
                Funnels.unencodedCharsFunnel(),
                expectedInsertions,
                falsePositiveProbability
        );

        // Add elements to the Bloom filter
        for (int i = 0; i < expectedInsertions; i++) {
            bloomFilter.put("ISBN" + i);
        }
    }

    @Test
    public void testExistingElements() {
        // Test elements that were added
        for (int i = 0; i < 1000; i++) {
            assertTrue(bloomFilter.mightContain("ISBN" + i));
        }
    }

    @Test
    public void testNonExistingElements() {
        // Test elements that were not added
        int falsePositives = 0;
        for (int i = 1000; i < 2000; i++) {
            if (bloomFilter.mightContain("ISBN" + i)) {
                falsePositives++;
            }
        }
        System.out.println("False positives: " + falsePositives);
    }
}
