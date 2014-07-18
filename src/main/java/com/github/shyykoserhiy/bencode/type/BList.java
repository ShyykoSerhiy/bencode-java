package com.github.shyykoserhiy.bencode.type;

import com.github.shyykoserhiy.bencode.exception.BParsingException;
import com.github.shyykoserhiy.bencode.parser.BParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shyyko
 */
public class BList extends ArrayList<IBValue> implements IBValue {
    public static final char START_SYMBOL = 'l';
    public static final char END_SYMBOL = 'e';

    public BList(int initialCapacity) {
        super(initialCapacity);
    }

    public BList() {
    }

    public BList(Collection<? extends IBValue> c) {
        super(c);
    }

    /**
     * @param bencodedValue bencoded value
     * @param index         startingIndex
     * @return Java object with this value
     */
    public static BList fromBencodedValue(String bencodedValue, AtomicInteger index) throws BParsingException {
        if (!bencodedValue.startsWith(String.valueOf(START_SYMBOL), index.get())) {
            throw new BParsingException("Invalid bencoded list");
        }
        index.incrementAndGet();
        BList toReturn = new BList();
        for (; index.get() < bencodedValue.length(); index.incrementAndGet()) {
            char currentCharacter = bencodedValue.charAt(index.get());
            if (currentCharacter == END_SYMBOL) {
                break;
            }
            toReturn.add(BParser.fromBencodedValue(bencodedValue, index));
        }

        if (index.get() >= bencodedValue.length()) {
            throw new BParsingException("No ending character");
        }
        return toReturn;
    }

    @Override
    public String toBencodedValue() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('l');
        for (IBValue ibValue : this) {
            stringBuilder.append(ibValue.toBencodedValue());
        }
        stringBuilder.append('e');
        return stringBuilder.toString();
    }
}
