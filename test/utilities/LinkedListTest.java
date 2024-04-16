package utilities;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * linked list test
 * @author Jackson Tedesco, Tony Song, and Della Avent
 * @date 2/8/2024
 */

class LinkedListTest {

	@Test
	void testIsEmpty() {
		LinkedList<String> ll = new LinkedList<String>();
		
		assertTrue(ll.isEmpty());
		assertEquals(0, ll.size());
		
		ll.addToFront(null);
		assertFalse(ll.isEmpty());
		assertEquals(1, ll.size());
		
		ll.addToFront("abc");
		assertFalse(ll.isEmpty());
		assertEquals(2, ll.size());
		
		ll.remove(null);
		ll.remove("abc");
		assertTrue(ll.isEmpty());
		assertEquals(0, ll.size());
	}
	
	@Test
	void testClear() {
		LinkedList<String> ll = new LinkedList<String>();
		ll.addToFront(null);
		assertFalse(ll.isEmpty());
		assertEquals(1, ll.size());
		
		ll.clear();
		assertEquals(0, ll.size());
		assertTrue(ll.isEmpty());
	}
	
	@Test
	void testSize() {
		LinkedList<String> ll = new LinkedList<String>();
		assertEquals(0, ll.size());
		
		ll.addToFront(null);
		assertEquals(1, ll.size());
		
		ll.addToBack(null);
		assertEquals(2, ll.size());
		
		ll.remove(null);
		assertEquals(1, ll.size());
		
		ll.remove(null);
		assertEquals(0, ll.size());
	}
	
	@Test
	void testAddToFront() {
		LinkedList<String> ll = new LinkedList<String>();
		assertTrue(ll.isEmpty());
		
		ll.addToFront("");
		assertEquals(1, ll.size());
		assertFalse(ll.isEmpty());
		assertTrue(ll.contains(""));
		assertEquals(",", ll.toString());
		
		ll.addToFront(null);
		assertEquals(2, ll.size());
		assertTrue(ll.contains(null));
		assertEquals("null,,", ll.toString());
		
		ll.addToFront("abc");
		assertEquals(3, ll.size());
		assertTrue(ll.contains("abc"));
		assertEquals("abc,null,,", ll.toString());
	}
	
	@Test
	void testAddToBack() {
		LinkedList<String> ll = new LinkedList<String>();
		assertTrue(ll.isEmpty());
		
		ll.addToBack("");
		assertEquals(1, ll.size());
		assertFalse(ll.isEmpty());
		assertTrue(ll.contains(""));
		assertEquals(",", ll.toString());
		
		ll.addToBack(null);
		assertEquals(2, ll.size());
		assertTrue(ll.contains(null));
		assertEquals(",null,", ll.toString());
		
		ll.addToBack("abc");
		assertEquals(3, ll.size());
		assertTrue(ll.contains("abc"));
		assertEquals(",null,abc,", ll.toString());
	}
	
	@Test
	void testContains() {
		LinkedList<String> ll = new LinkedList<String>();
		assertFalse(ll.contains(null));
		assertFalse(ll.contains(""));
		
		ll.addToFront(null);
		assertTrue(ll.contains(null));
		
		ll.addToFront("abc");
		assertTrue(ll.contains("abc"));
		assertTrue(ll.contains(null));
		assertFalse(ll.contains(""));
	}
	
	@Test
	void testRemove() {
		LinkedList<String> ll = new LinkedList<String>();
		assertFalse(ll.remove(null));
		assertTrue(ll.isEmpty());
		
		ll.addToFront("a");
		ll.addToBack("b");
		ll.addToBack("c");
		ll.addToBack("d");
		ll.addToBack("a");
		assertEquals(5, ll.size());
		
		assertTrue(ll.remove("a"));
		assertEquals(4, ll.size());
		assertEquals("b,c,d,a,", ll.toString());
		
		assertTrue(ll.remove("a"));
		assertEquals(3, ll.size());
		assertEquals("b,c,d,", ll.toString());
		
		assertTrue(ll.remove("c"));
		assertEquals(2, ll.size());
		assertEquals("b,d,", ll.toString());
		
		assertFalse(ll.remove("e"));
		assertEquals(2, ll.size());
		assertEquals("b,d,", ll.toString());
		
		assertTrue(ll.remove("b"));
		assertEquals(1, ll.size());
		assertEquals("d,", ll.toString());
		
		assertFalse(ll.remove("z"));
		assertEquals(1, ll.size());
		assertEquals("d,", ll.toString());
		
		assertTrue(ll.remove("d"));
		assertEquals(0, ll.size());
		assertEquals("", ll.toString());
		
		assertFalse(ll.remove("d"));
		assertEquals(0, ll.size());
	}
	
	@Test
	void testReverse() {
		LinkedList<String> ll = new LinkedList<String>();
		ll.reverse();
		assertEquals("", ll.toString());
		
		ll.addToFront("a");
		assertEquals("a,", ll.toString());
		ll.reverse();
		assertEquals("a,", ll.toString());
		ll.addToBack("b");
		assertEquals("a,b,", ll.toString());
		ll.reverse();
		assertEquals("b,a,", ll.toString());
		ll.reverse();
		assertEquals("a,b,", ll.toString());
		
		ll.addToBack("c");
		assertEquals("a,b,c,", ll.toString());
		ll.reverse();
		assertEquals("c,b,a,", ll.toString());
		
		ll = new LinkedList<String>();
		ll.addToBack("a");
		ll.addToBack("b");
		ll.addToBack("c");
		ll.addToBack("a");
		ll.addToBack("");
		ll.addToBack(null);
		ll.reverse();
		assertEquals("null,,a,c,b,a,", ll.toString());
		
		ll = new LinkedList<String>();
		ll.addToBack("h");
		ll.addToBack("a");
		ll.addToBack("n");
		ll.addToBack("n");
		ll.addToBack("a");
		ll.addToBack("h");
		assertEquals("h,a,n,n,a,h,", ll.toString());
	}
	
	@Test
	void testGet() {
		LinkedList<String> ll = new LinkedList<String>();
		assertEquals(null, ll.get(0));
		assertEquals(null, ll.get(10));
		
		ll.addToFront("a");
		ll.addToBack("b");
		ll.addToBack("c");
		ll.addToBack("d");
		ll.addToBack("a");
		
		assertEquals("a", ll.get(0));
		assertEquals("c", ll.get(2));
		assertEquals("a", ll.get(4));
		assertEquals(null, ll.get(10));
	}
}
