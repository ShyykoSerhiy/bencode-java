package com.github.shyykoserhiy.bencode.type;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class BStringTest {
    @Test
    public void testFromBencodedValue() throws Exception {
        Assert.assertEquals(BString.fromBencodedValue("9:testValue", new AtomicInteger()), new BString("testValue"));
        Assert.assertEquals(BString.fromBencodedValue("10:testValues", new AtomicInteger()), new BString("testValues"));
        Assert.assertEquals(BString.fromBencodedValue("9:testValue//////////////)", new AtomicInteger()), new BString("testValue"));
    }

    @Test
    public void testToBencodedValue() throws Exception {
        Assert.assertEquals(new BString("testValue").toBencodedValue(), "9:testValue");
        Assert.assertEquals(new BString("testValues").toBencodedValue(), "10:testValues");
    }
}