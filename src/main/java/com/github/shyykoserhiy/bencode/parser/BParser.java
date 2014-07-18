package com.github.shyykoserhiy.bencode.parser;

import com.github.shyykoserhiy.bencode.exception.BParsingException;
import com.github.shyykoserhiy.bencode.type.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shyyko
 */
public class BParser {
    /**
     * @param toConvert string in bencoded format
     * @param index     current index
     * @return java version of bencode value
     */
    public static IBValue fromBencodedValue(String toConvert, AtomicInteger index) throws BParsingException {
        char currentCharacter = toConvert.charAt(index.get());
        if (currentCharacter == BInteger.START_SYMBOL) {
            return BInteger.fromBencodedValue(toConvert, index);
        } else if (currentCharacter == BList.START_SYMBOL) {
            return BList.fromBencodedValue(toConvert, index);
        } else if (currentCharacter == BMap.START_SYMBOL) {
            return BMap.fromBencodedValue(toConvert, index);
        } else if (Character.isDigit(currentCharacter)) {
            return BString.fromBencodedValue(toConvert, index);
        } else {
            throw new BParsingException("Invalid bencoded data at: " + index.get() + ", char " + currentCharacter);
        }
    }
}
