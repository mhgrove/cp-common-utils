package com.clarkparsia.common.base;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * <p>Tests for the {@link Copyables} class</p>
 *
 * @author  Michael Grove
 * @since   2.3.1
 * @version 2.3.1
 */
public class TestCopyables {
    @Test
    public void testArrayCopy() {
        MockCopyable[] aArray = new MockCopyable[] { new MockCopyable(), new MockCopyable() };

        assertArrayEquals(aArray, Copyables.copy(aArray));
    }

    @Test
    public void testArrayPrimitiveCopy() {
        int[] aArray = { 1, 2, 3, 4, 5 };

        assertArrayEquals(aArray, Copyables.copy(aArray));
    }

    @Test
    public void testOptionalCopy() {
        Optional<String> aStringOptional = Optional.of("string");
        Optional<MockCopyable> aAbsentOptional = Optional.absent();
        Optional<MockCopyable> aOptional = Optional.of(new MockCopyable());

        assertSame(aAbsentOptional, Copyables.copy(aAbsentOptional));
        assertEquals(aStringOptional, Copyables.copy(aStringOptional));
        assertSame(aStringOptional.get(), Copyables.copy(aStringOptional).get());
        assertEquals(aOptional, Copyables.copy(aOptional));
        assertFalse(aOptional.get() == Copyables.copy(aOptional).get());
    }

    @Test
    public void testCollectionOfCopyablesCopy() {
        Set<MockCopyable> aSet = Sets.newHashSet(new MockCopyable(), new MockCopyable(), new MockCopyable());

        assertEquals(aSet, Copyables.copy(aSet));
    }

    @Test
    public void testCollectionOfNonCopyable() {
        Set<String> aSet = Sets.newHashSet("a", "b", "c");

        assertEquals(aSet, Copyables.<Set<String>>copy(aSet));
    }

    @Test
    public void testCopyNull() {
        assertNull(Copyables.copy((Object) null));
    }

    @Test
    public void testCopyNonCopyable() {
        final String aStr = "not copyable";
        assertSame(aStr, Copyables.copy(aStr));
    }

    @Test
    public void testCopyable() {
        MockCopyable aObj = new MockCopyable();

        assertEquals(aObj, Copyables.copy(aObj));
    }

    private static final class MockCopyable implements Copyable<MockCopyable> {
        private final String mValue;

        private MockCopyable() {
            this(Strings2.getRandomString(10));
        }

        private MockCopyable(final String theValue) {
            mValue = theValue;
        }

        @Override
        public MockCopyable copy() {
            return new MockCopyable(mValue);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final MockCopyable that = (MockCopyable) o;

            if (mValue != null
                ? !mValue.equals(that.mValue)
                : that.mValue != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return mValue != null
                   ? mValue.hashCode()
                   : 0;
        }
    }
}
