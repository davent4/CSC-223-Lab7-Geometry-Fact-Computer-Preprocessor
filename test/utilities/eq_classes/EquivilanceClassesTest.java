package utilities.eq_classes;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;
/**
 * Test EquivalenceClasses
 * @author Jackson Tedesco, Tony Song, and Della Avent
 * @data 2/8/2024
 * @param <T>
 */

class EquivilanceClassesTest {

	@Test
	void addTest() {
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		EquivalenceClasses<Integer> _classes = new EquivalenceClasses<>(c);

		assertFalse(_classes.add(null));
		assertEquals(0, _classes.numClasses());
		assertFalse(_classes.contains(null));
		
		assertTrue(_classes.add(2));
		assertEquals(1, _classes.numClasses());
		assertTrue(_classes.contains(2));

		assertFalse(_classes.add(2));
		assertEquals(1, _classes.numClasses());
		assertEquals(1, _classes.size());
		assertTrue(_classes.contains(2));
		
		assertFalse(_classes.add(4));
		assertEquals(1, _classes.numClasses());
		assertEquals(2, _classes.size());
		assertTrue(_classes.contains(4));

		assertTrue(_classes.add(3));
		assertEquals(2, _classes.numClasses());
		assertEquals(3, _classes.size());
		assertTrue(_classes.contains(3));
		
		assertFalse(_classes.add(9));
		assertEquals(2, _classes.numClasses());
		assertEquals(4, _classes.size());
		assertTrue(_classes.contains(9));
		
		assertFalse(_classes.add(null));
		assertEquals(2, _classes.numClasses());
		assertEquals(4, _classes.size());
		assertFalse(_classes.contains(null));
		
		c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 3 == y % 3 ? 0 : 1; }
		};
		
		_classes = new EquivalenceClasses<>(c);
		
		assertTrue(_classes.add(3));
		assertEquals(1, _classes.numClasses());
		assertTrue(_classes.contains(3));
		
		assertTrue(_classes.add(4));
		assertEquals(2, _classes.numClasses());
		assertEquals(2, _classes.size());
		assertTrue(_classes.contains(4));

		assertFalse(_classes.add(6));
		assertEquals(2, _classes.numClasses());
		assertEquals(3, _classes.size());
		assertTrue(_classes.contains(6));
		
		assertTrue(_classes.add(5));
		assertEquals(3, _classes.numClasses());
		assertEquals(4, _classes.size());
		assertTrue(_classes.contains(5));
		
	}

	@Test
	void containsTest() {
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		EquivalenceClasses<Integer> _classes = new EquivalenceClasses<>(c);
		assertFalse(_classes.contains(8));
		assertFalse(_classes.contains(null));
		
		_classes.add(2);
		_classes.add(4);
		_classes.add(3);

		assertTrue(_classes.contains(2));
		assertTrue(_classes.contains(4));
		assertTrue(_classes.contains(3));
		assertFalse(_classes.contains(8));
		
		assertFalse(_classes.contains(null));
		
		c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 3 == y % 3 ? 0 : 1; }
		};

		_classes = new EquivalenceClasses<>(c);
		assertFalse(_classes.contains(8));
		assertFalse(_classes.contains(null));
		
		_classes.add(3);
		_classes.add(6);
		_classes.add(5);

		assertTrue(_classes.contains(3));
		assertTrue(_classes.contains(6));
		assertTrue(_classes.contains(5));
		assertFalse(_classes.contains(8));
	}

	@Test
	void sizeTest() {
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		EquivalenceClasses<Integer> _classes = new EquivalenceClasses<>(c);
		assertEquals(0, _classes.size());

		_classes.add(2);
		assertEquals(1, _classes.size());
		
		_classes.add(2);
		assertEquals(1, _classes.size());
		
		_classes.add(4);
		assertEquals(2, _classes.size());	
		
		_classes.add(3);
		assertEquals(3, _classes.size());
		
		_classes.add(null);
		assertEquals(3, _classes.size());
		
		c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 3 == y % 3 ? 0 : 1; }
		};

		_classes = new EquivalenceClasses<>(c);
		
		_classes.add(3);
		assertEquals(1, _classes.size());
		
		_classes.add(5);
		assertEquals(2, _classes.size());
		
		_classes.add(4);
		assertEquals(3, _classes.size());	
	}
	
	@Test
	void numClassesTest() {
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};
		
		EquivalenceClasses<Integer> _classes = new EquivalenceClasses<>(c);
		assertEquals(0, _classes.numClasses());

		_classes.add(2);
		assertEquals(1, _classes.numClasses());
		
		_classes.add(2);
		assertEquals(1, _classes.numClasses());
		
		_classes.add(4);
		assertEquals(1, _classes.numClasses());	
		
		_classes.add(3);
		assertEquals(2, _classes.numClasses());	
		
		_classes.add(9);
		assertEquals(2, _classes.numClasses());	
		
		_classes.add(null);
		assertEquals(2, _classes.numClasses());
		
		c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 3 == y % 3 ? 0 : 1; }
		};

		_classes = new EquivalenceClasses<>(c);
		
		_classes.add(4);
		assertEquals(1, _classes.numClasses());
		
		_classes.add(3);
		assertEquals(2, _classes.numClasses());	
		
		_classes.add(5);
		assertEquals(3, _classes.numClasses());	
	}
}
