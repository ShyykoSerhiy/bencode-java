package com.github.shyykoserhiy.bencode.type;

import com.github.shyykoserhiy.bencode.exception.BParsingException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shyyko
 */
public class BInteger implements IBValue {
    public static final char START_SYMBOL = 'i';
    public static final char END_SYMBOL = 'e';

    private final int value;

    public BInteger(int value) {
        this.value = value;
    }

    /**
     * @param bencodedValue bencoded value
     * @param index         startingIndex
     * @return Java object with this value
     */
    public static BInteger fromBencodedValue(String bencodedValue, AtomicInteger index) throws BParsingException {
        if (!bencodedValue.startsWith(String.valueOf(START_SYMBOL), index.get())) {
            throw new BParsingException("Invalid bencoded integer");
        }
        final int startIndex = index.incrementAndGet();
        if (bencodedValue.charAt(index.get()) == '-') {
            index.incrementAndGet();
        }

        for (; index.get() < bencodedValue.length(); index.incrementAndGet()) {
            char currentCharacter = bencodedValue.charAt(index.get());
            if (currentCharacter == END_SYMBOL) {
                break;
            }
            if (!Character.isDigit(currentCharacter)) {
                throw new BParsingException("Illegal character where digit is expected");
            }
        }

        if (index.get() >= bencodedValue.length()) {
            throw new BParsingException("No ending character");
        }
        return new BInteger(Integer.parseInt(bencodedValue.substring(startIndex, index.get())));
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toBencodedValue() {
        return String.valueOf(START_SYMBOL) + value + END_SYMBOL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BInteger bInteger = (BInteger) o;

        return value == bInteger.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "BInteger{" +
                "value=" + value +
                '}';
    }
}
