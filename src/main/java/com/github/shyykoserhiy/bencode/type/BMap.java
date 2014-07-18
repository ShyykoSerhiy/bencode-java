package com.github.shyykoserhiy.bencode.type;

import com.github.shyykoserhiy.bencode.exception.BParsingException;
import com.github.shyykoserhiy.bencode.exception.InvalidSortingInBMap;
import com.github.shyykoserhiy.bencode.parser.BParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * * Tree map is used because "Keys must be strings and appear in sorted order (sorted as raw strings, not alphanumerics).
 * The strings should be compared using a binary comparison, not a culture-specific "natural" comparison."
 * <a href="https://wiki.theory.org/BitTorrentSpecification#Bencoding">BitTorrentSpecification</a>
 *
 * @author shyyko
 */
public class BMap extends TreeMap<BString, IBValue> implements IBValue {
    public static final char START_SYMBOL = 'd';
    public static final char END_SYMBOL = 'e';

    /**
     * @param bencodedValue bencoded value
     * @param index         startingIndex
     * @return Java object with this value
     */
    public static BMap fromBencodedValue(String bencodedValue, AtomicInteger index) throws BParsingException {
        if (!bencodedValue.startsWith(String.valueOf(START_SYMBOL), index.get())) {
            throw new BParsingException("Invalid bencoded map");
        }
        index.incrementAndGet();
        List<BString> keys = new ArrayList<>();
        BMap toReturn = new BMap();
        for (; index.get() < bencodedValue.length(); index.incrementAndGet()) {
            char currentCharacter = bencodedValue.charAt(index.get());
            if (currentCharacter == END_SYMBOL) {
                break;
            }
            BString key = BString.fromBencodedValue(bencodedValue, index);
            index.incrementAndGet();
            IBValue value = BParser.fromBencodedValue(bencodedValue, index);
            if (keys.size() > 0) {
                if (keys.get(keys.size() - 1).compareTo(key) > 0) {
                    throw new InvalidSortingInBMap();
                }
            }
            keys.add(key);
            toReturn.put(key, value);
        }

        if (index.get() >= bencodedValue.length()) {
            throw new BParsingException("No ending character");
        }
        return toReturn;
    }

    @Override
    public String toBencodedValue() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_SYMBOL);
        //we can iterate through entry set, because its sorted already
        for (Map.Entry<BString, IBValue> bStringIBValueEntry : entrySet()) {
            stringBuilder.append(bStringIBValueEntry.getKey().toBencodedValue());
            stringBuilder.append(bStringIBValueEntry.getValue().toBencodedValue());
        }
        stringBuilder.append(END_SYMBOL);
        return stringBuilder.toString();
    }
}
