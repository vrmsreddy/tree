package com.ms.jpa.advance.specification;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhongjun on 6/24/17.
 */
public class FilterTest {
    @Test
    public void parseTest() throws Exception {

        String s;
        Filter filter;

        s = "a~isnull~~and~b~isnull~";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());

        s = "a~isnull~";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());


        s = "a~eq~a";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());

        s = "(a~eq~a)";
        filter = Filter.parse(s);
        Assert.assertEquals("a~eq~a", filter.toString());

        s = "a~eq~a~and~b~eq~b";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());

        s = "a~eq~a~and~(b~eq~b)";
        filter = Filter.parse(s);
        Assert.assertEquals("a~eq~a~and~b~eq~b", filter.toString());

        s = "a~eq~a~and~(b~eq~b~or~c~eq~c)";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());

        s = "a~eq~a~and~(b~eq~b~or~c~eq~c)~and~d~eq~d";
        Filter.parse(s);
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());


        s = "a~eq~a~and~((b~eq~b~or~c~eq~c)~and~d~eq~d)";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());

        s = "a~eq~a~and~((b~eq~b~or~c~eq~c)~and~(d~eq~d))";
        filter = Filter.parse(s);
        Assert.assertEquals("a~eq~a~and~((b~eq~b~or~c~eq~c)~and~d~eq~d)", filter.toString());

        s = "a~eq~a~and~((b~eq~b~or~c~eq~c)~and~(d~eq~d~or~e~eq~e))";
        filter = Filter.parse(s);
        Assert.assertEquals(s, filter.toString());


    }

}