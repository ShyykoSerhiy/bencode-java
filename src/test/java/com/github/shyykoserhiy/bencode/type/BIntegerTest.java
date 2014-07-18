package com.github.shyykoserhiy.bencode.type;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;


public class BIntegerTest {
    @Test
    public void testFromBencodedValue() throws Exception {
        Assert.assertEquals(BInteger.fromBencodedValue("i756e", new AtomicInteger()), new BInteger(756));
        Assert.assertEquals(BInteger.fromBencodedValue("i0e", new AtomicInteger()), new BInteger(0));
    }

    @Test
    public void testToBencodedValue() throws Exception {
        Assert.assertEquals(new BInteger(0).toBencodedValue(), "i0e");
        Assert.assertEquals(new BInteger(756).toBencodedValue(), "i756e");
    }
}