package com.bn.finalp.util;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.BitSet;
import java.util.function.Function;
@Slf4j
@Component
public class RedisBloomUtil {

    private final RedisTemplate<String, byte[]> redisTemplate;
    private final int bitSetSize;
    private final Function<String, Integer> hashFunction;

    @Autowired
    public RedisBloomUtil(RedisTemplate<String, byte[]> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.bitSetSize = 1_000_000; // Adjust the size as needed
        this.hashFunction = this::simpleHash; // Simple hash function, consider a more robust one
    }

    public void add(String key, String value) {
        int hash = hashFunction.apply(value);
        BitSet bitSet = getBitSet(key);
        bitSet.set(hash);
        saveBitSet(key, bitSet);
    }

    public boolean mightContain(String key, String value) {
        int hash = hashFunction.apply(value);
        BitSet bitSet = getBitSet(key);
        return bitSet.get(hash);
    }

    private BitSet getBitSet(String key) {
        byte[] bytes = redisTemplate.opsForValue().get(key);
        if (bytes != null) {
            return BitSet.valueOf(bytes);
        }
        return new BitSet(bitSetSize);
    }

    private void saveBitSet(String key, BitSet bitSet) {
        byte[] bytes = bitSet.toByteArray();
        redisTemplate.opsForValue().set(key, bytes);
    }

    private int simpleHash(String value) {
        // Simple hash function for demonstration purposes
        return Math.abs(value.hashCode()) % bitSetSize;
    }
}