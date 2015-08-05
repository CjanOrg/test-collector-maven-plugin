/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Bruno P. Kinoshita
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cjan.test_collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Tests for {@link Test}.
 *
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public class TestTest {

    private final static String NAME = "TEST-Method1";
    private final static Status STATUS = Status.FAILURE;
    private final static String METADATA = "br.eti.kinoshita.TestA#testMethodA";
    private final static String TO_STRING = "Test [name=" + NAME + ", status=" + STATUS + ", metadata=" + METADATA
            + "]";

    @org.junit.Test
    public void testTestAccessorMethods() {
        org.cjan.test_collector.Test t = new org.cjan.test_collector.Test(NAME, STATUS, METADATA);
        assertEquals("Wrong name value", NAME, t.getName());
        assertEquals("Wrong status value", STATUS, t.getStatus());
        assertEquals("Wrong metadata value", METADATA, t.getMetadata());
    }

    @org.junit.Test
    public void testComparingObjects() {
        org.cjan.test_collector.Test t1 = new org.cjan.test_collector.Test(NAME, STATUS, METADATA);
        org.cjan.test_collector.Test t2 = new org.cjan.test_collector.Test(NAME, STATUS, METADATA);
        assertEquals("Objects mismatch", t1, t2);
        org.cjan.test_collector.Test t3 = new org.cjan.test_collector.Test(NAME + "A", STATUS, METADATA);
        org.cjan.test_collector.Test t4 = new org.cjan.test_collector.Test(NAME, STATUS, METADATA);
        assertFalse("Objects were incorrectly marked as equals", t3.equals(t4));
    }

    @org.junit.Test
    public void testToString() {
        org.cjan.test_collector.Test t = new org.cjan.test_collector.Test(NAME, STATUS, METADATA);
        assertEquals("toString() didn't match", TO_STRING, t.toString());
    }

}
