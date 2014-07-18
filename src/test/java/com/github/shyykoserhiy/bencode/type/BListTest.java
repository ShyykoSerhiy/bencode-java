package com.github.shyykoserhiy.bencode.type;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BListTest {

    @Test
    public void testToBencodedValue() throws Exception {
        BList list = new BList(Arrays.asList(new BString("spam"), new BString("eggs")));
        Assert.assertEquals(list.toBencodedValue(), "l4:spam4:eggse");

        list = new BList(Arrays.asList(new BString("spam"), list));
        Assert.assertEquals(list.toBencodedValue(), "l4:spaml4:spam4:eggsee");

        list = new BList(Arrays.asList(new BString("spam"), list));
        Assert.assertEquals(list.toBencodedValue(), "l4:spaml4:spaml4:spam4:eggseee");
    }

    @Test
    public void testFromBencodedValue() throws Exception {
        BList expected = new BList(Arrays.asList(new BString("spam"), new BString("eggs")));
        Assert.assertEquals(BList.fromBencodedValue("l4:spam4:eggse", new AtomicInteger()), expected);

        expected = new BList(Arrays.asList(new BString("spam"), expected));
        Assert.assertEquals(BList.fromBencodedValue("l4:spaml4:spam4:eggsee", new AtomicInteger()), expected);

        expected = new BList(Arrays.asList(new BString("spam"), expected));
        Assert.assertEquals(BList.fromBencodedValue("l4:spaml4:spaml4:spam4:eggseee", new AtomicInteger()), expected);

        expected = new BList(Arrays.asList(new BInteger(50), new BInteger(35), expected));
        Assert.assertEquals(BList.fromBencodedValue("li50ei35el4:spaml4:spaml4:spam4:eggseeee", new AtomicInteger()), expected);
    }
}