import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class HashtableMapTests {

    /**
     * Test that put and get work correctly together.
     * This test puts a key-value pair into the hashtable,
     * and then retrieves it using get and checks the retrieved value.
     */
    @Test
    public void testPutAndGet() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("One", 1);
        int value = hashtable.get("One");
        Assertions.assertEquals(1, value);
    }

    /**
     * Test that containsKey returns the correct result.
     * This test puts a key-value pair into the hashtable,
     * and then checks that containsKey returns true for the key.
     */
    @Test
    public void testContainsKey() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("Two", 2);
        Assertions.assertTrue(hashtable.containsKey("Two"));
    }

    /**
     * Test that remove behaves correctly.
     * This test puts a key-value pair into the hashtable,
     * removes it, and then checks that get throws a NoSuchElementException
     * and containsKey returns false.
     */
    @Test
    public void testRemove() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("Three", 3);
        hashtable.remove("Three");
        Assertions.assertThrows(NoSuchElementException.class, () -> hashtable.get("Three"));
        Assertions.assertFalse(hashtable.containsKey("Three"));
    }

    /**
     * Test that trying to put a null key throws an IllegalArgumentException.
     */
    @Test
    public void testNullKey() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> hashtable.put(null, 1));
    }

    /**
     * Test that trying to put a duplicate key throws an IllegalArgumentException.
     */
    @Test
    public void testDuplicateKey() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("Four", 4);
        Assertions.assertThrows(IllegalArgumentException.class, () -> hashtable.put("Four", 4));
    }

    /**
     * Test that the hashtable correctly resizes itself when load factor exceeds 0.75.
     */
    @Test
    public void testArrayGrowth() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        int initialCapacity = hashtable.getCapacity();
        for (int i = 0; i < initialCapacity; i++) {
            hashtable.put("Key" + i, i);
        }
        // Add one more item to trigger resize
        hashtable.put("Key" + initialCapacity, initialCapacity);
        Assertions.assertTrue(hashtable.getCapacity() > initialCapacity);
    }

    /**
     * Test that trying to get or remove a non-existent key throws a NoSuchElementException.
     */
    @Test
    public void testNoSuchElement() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        Assertions.assertThrows(NoSuchElementException.class, () -> hashtable.get("NonExistentKey"));
        Assertions.assertThrows(NoSuchElementException.class, () -> hashtable.remove("NonExistentKey"));
    }

    /**
     * Test that the clear method correctly removes all entries from the hashtable.
     */
    @Test
    public void testClear() {
        HashtableMap<String, Integer> hashtable = new HashtableMap<>();
        hashtable.put("Five", 5);
        hashtable.clear();
        Assertions.assertEquals(0, hashtable.getSize());
        Assertions.assertFalse(hashtable.containsKey("Five"));
    }
}
