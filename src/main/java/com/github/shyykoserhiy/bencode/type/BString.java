package com.github.shyykoserhiy.bencode.type;

import com.github.shyykoserhiy.bencode.exception.BParsingException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shyyko
 */
public class BString implements IBValue, Comparable<BString> {
    public static final char DELIMITER = ':';

    private final String value;

    public BString(String value) {
        assert value != null : "value may not be null";
        this.value = value;
    }

    /**
     * @param bencodedValue bencoded value
     * @param index         startingIndex
     * @return Java object with this value
     */
    public static BString fromBencodedValue(String bencodedValue, AtomicInteger index) throws BParsingException {
        final int lengthStartIndex = index.get();
        for (; index.get() < bencodedValue.length(); index.incrementAndGet()) {
            char currentCharacter = bencodedValue.charAt(index.get());
            if (currentCharacter == DELIMITER) {
                break;
            }
            if (!Character.isDigit(currentCharacter)) {
                throw new BParsingException("Invalid bencoded string");
            }
        }
        if (index.get() >= bencodedValue.length()) {
            throw new BParsingException("No string after length number");
        }
        int length = Integer.parseInt(bencodedValue.substring(lengthStartIndex, index.get()));
        final int startStringIndex = index.get() + 1;
        final int endStringIndex = startStringIndex + length;
        index.addAndGet(length);
        return new BString(bencodedValue.substring(startStringIndex, endStringIndex));
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toBencodedValue() {
        return String.valueOf(value.length()) + DELIMITER + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BString bString = (BString) o;

        return value.equals(bString.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(BString o) {
        return value.compareTo(o.getValue());
    }

    @Override
    public String toString() {
        return "BString{" +
                "value='" + value + '\'' +
                '}';
    }
}
