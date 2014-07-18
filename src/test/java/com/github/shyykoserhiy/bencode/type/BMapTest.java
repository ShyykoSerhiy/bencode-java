package com.github.shyykoserhiy.bencode.type;

import com.github.shyykoserhiy.bencode.exception.InvalidSortingInBMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class BMapTest {

    @Test
    public void testToBencodedValue() throws Exception {
        BMap map = new BMap();
        map.put(new BString("publisher"), new BString("bob"));
        map.put(new BString("publisher-webpage"), new BString("www.example.com"));
        map.put(new BString("publisher.location"), new BString("home"));
        Assert.assertEquals(map.toBencodedValue(), "d9:publisher3:bob17:publisher-webpage" +
                "15:www.example.com18:publisher.location4:homee");
    }

    @Test
    public void testFromBencodedValue() throws Exception {
        BMap expected = new BMap();
        expected.put(new BString("publisher"), new BString("bob"));
        expected.put(new BString("publisher-webpage"), new BString("www.example.com"));
        expected.put(new BString("publisher.location"), new BString("home"));
        Assert.assertEquals(BMap.fromBencodedValue("d9:publisher3:bob17:publisher-webpage" +
                "15:www.example.com18:publisher.location4:homee", new AtomicInteger()), expected);
    }

    @Test(expectedExceptions = InvalidSortingInBMap.class)
    public void testFromBencodedValueInvalidSorting() throws Exception {
        BMap.fromBencodedValue("d17:publisher-webpage" +
                "15:www.example.com9:publisher3:bob18:publisher.location4:homee", new AtomicInteger());
    }
}