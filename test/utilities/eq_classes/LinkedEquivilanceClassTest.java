package utilities.eq_classes;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;
/**
 * LinkedEquivilanceClass test
 * @author Jackson Tedesco, Tony Song, and Della Avent
 * @data 2/8/2024
 */
class LinkedEquivilanceClassTest {
	@Test
	void testCanonical() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<Integer>(c);
		assertTrue(LEC.isEmpty());
		assertEquals(null, LEC.canonical());
		assertEquals(LEC.size(), 0);

		assertTrue(LEC.add(2));
		assertEquals(2 , LEC.canonical());
		assertFalse(LEC.isEmpty());
		assertEquals(LEC.size(), 1);


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertEquals(null, LEC.canonical());
		assertTrue(LEC.isEmpty());
		assertEquals(0, LEC.size());

		assertTrue(LEC.add(2));
		assertEquals(2 , LEC.canonical());
		assertFalse(LEC.isEmpty());
		assertEquals(1, LEC.size());
	}

	@Test
	void testIsEmpty() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertTrue(LEC.isEmpty());

		assertTrue(LEC.add(2));
		assertFalse(LEC.isEmpty());

		assertTrue(LEC.remove(2));
		assertTrue(LEC.isEmpty());


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertTrue(LEC.isEmpty());

		assertTrue(LEC.add(4));
		assertFalse(LEC.isEmpty());

		assertTrue(LEC.remove(4));
		assertTrue(LEC.isEmpty());
	}

	@Test
	void testClear() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(3));
		assertTrue(LEC.add(5));
		assertEquals(3, LEC.size());

		LEC.clear();
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());

		assertTrue(LEC.add(1));
		assertEquals(LEC.size(), 1);

		LEC.clear();
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(5));
		assertTrue(LEC.add(9));
		assertEquals(LEC.size(), 3);

		LEC.clear();
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());

		assertTrue(LEC.add(1));
		assertEquals(LEC.size(), 1);

		LEC.clear();
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());
	}

	@Test
	void testClearNonCanonical() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(3));
		assertTrue(LEC.add(5));
		assertEquals(LEC.size(), 3);

		LEC.clearNonCanonical();
		assertEquals(1, LEC.size());
		assertEquals(1, LEC.canonical());
		assertEquals("1:", LEC.toString());

		assertTrue(LEC.add(3));
		assertEquals(2, LEC.size());

		LEC.clearNonCanonical();
		assertEquals(1, LEC.size());
		assertEquals(1, LEC.canonical());
		assertEquals("1:", LEC.toString());	


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(5));
		assertTrue(LEC.add(9));
		assertEquals(LEC.size(), 3);

		LEC.clearNonCanonical();
		assertEquals(1, LEC.size());
		assertEquals(1, LEC.canonical());
		assertEquals("1:", LEC.toString());

		assertTrue(LEC.add(5));
		assertEquals(2, LEC.size());

		LEC.clearNonCanonical();
		assertEquals(1, LEC.size());
		assertEquals(1, LEC.canonical());
		assertEquals("1:", LEC.toString());
	}

	@Test
	void testSize() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertEquals(0, LEC.size());

		assertTrue(LEC.add(2));
		assertEquals(1, LEC.size());
		assertTrue(LEC.add(4));
		assertTrue(LEC.add(6));
		assertEquals(3, LEC.size());


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertEquals(0, LEC.size());

		assertTrue(LEC.add(2));
		assertEquals(1, LEC.size());
		assertTrue(LEC.add(6));
		assertTrue(LEC.add(10));
		assertEquals(3, LEC.size());
	}

	@Test
	void testAdd() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);

		//smoke test, sets canonical
		assertTrue(LEC.add(2));
		assertEquals("2:" , LEC.toString());
		assertEquals(1, LEC.size());
		assertEquals(2 , LEC.canonical());

		//add beyond canonical
		assertTrue(LEC.add(4));
		assertEquals("2:4," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//no duplicates
		assertFalse(LEC.add(4));
		assertEquals("2:4," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//add something that doesn't belong
		assertFalse(LEC.add(3));
		assertEquals("2:4," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//cannot add null values
		assertFalse(LEC.add(null));
		assertEquals("2:4," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//cannot set canonical as null
		LEC = new LinkedEquivalenceClass<>(c);
		assertFalse(LEC.add(null));
		assertEquals("" , LEC.toString());
		assertEquals(0, LEC.size());
		assertEquals(null , LEC.canonical());
		assertTrue(LEC.isEmpty());

	
		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		
		//cannot set canonical as null
		assertFalse(LEC.add(null));
		assertEquals("" , LEC.toString());
		assertEquals(0, LEC.size());
		assertEquals(null , LEC.canonical());
		assertTrue(LEC.isEmpty());
		
		//smoke test, sets canonical
		assertTrue( LEC.add(2));
		assertEquals("2:" , LEC.toString());
		assertEquals(1, LEC.size());
		assertEquals(2 , LEC.canonical());

		//add beyond canonical
		assertTrue(LEC.add(6));
		assertEquals("2:6," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//no duplicates
		assertFalse(LEC.add(6));
		assertEquals("2:6," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//add something that doesn't belong
		assertFalse(LEC.add(1));
		assertFalse(LEC.add(3));
		assertFalse(LEC.add(4));
		assertEquals("2:6," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());

		//cannot add null values
		assertFalse( LEC.add(null));
		assertEquals("2:6," , LEC.toString());
		assertEquals(2, LEC.size());
		assertEquals(2 , LEC.canonical());
	}

	@Test
	void testContains() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertFalse(LEC.contains(1));	//contain on empty
		assertFalse(LEC.contains(null));//contain on empty null

		assertTrue(LEC.add(8));
		assertTrue(LEC.add(12));
		assertTrue(LEC.add(2));
		assertTrue(LEC.add(4));
		assertEquals(LEC.canonical(), 8);
		
		assertTrue(LEC.contains(12));
		assertTrue(LEC.contains(2));
		assertTrue(LEC.contains(4));
		assertFalse(LEC.contains(16));

		assertTrue(LEC.contains(8)); //canonical
		assertTrue(LEC.contains(2)); //there but not canonical end
		assertTrue(LEC.contains(12)); //there but not canonical first
		assertFalse(LEC.contains(3)); //doesn't belong, doesn't contain
		assertFalse(LEC.contains(6));	//does belong, doesn't contain
		assertFalse(LEC.contains(null));	//null


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertFalse(LEC.contains(1));
		assertFalse(LEC.contains(null));

		assertTrue(LEC.add(8));
		assertTrue(LEC.add(4));
		assertTrue(LEC.add(12));
		assertTrue(LEC.add(20));
		assertEquals(8, LEC.canonical());
		
		assertTrue(LEC.contains(12));
		assertTrue(LEC.contains(20));
		assertTrue(LEC.contains(4));
		assertFalse(LEC.contains(24));

		assertTrue(LEC.contains(8)); 
		assertTrue(LEC.contains(12)); 
		assertTrue(LEC.contains(4)); 
		assertFalse(LEC.contains(3)); 
		assertFalse(LEC.contains(16));	
		assertFalse(LEC.contains(null));	
	}

	@Test
	void testBelongs() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertFalse(LEC.belongs(2));	//empty list
		assertFalse(LEC.belongs(null));	//empty list and null

		assertTrue(LEC.add(2));
		assertTrue(LEC.add(4));
		assertTrue(LEC.add(6));
		assertEquals(2, LEC.canonical());

		assertTrue(LEC.belongs(2));		//belong and contains
		assertTrue(LEC.belongs(12));	//does belong, doesn't contain
		assertFalse(LEC.belongs(13));	//doesn't belong
		assertFalse(LEC.belongs(null));	//null


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertFalse(LEC.belongs(2));	//empty list
		assertFalse(LEC.belongs(null));	//empty list and null

		assertTrue(LEC.add(2));
		assertTrue(LEC.add(6));
		assertTrue(LEC.add(10));
		assertEquals(2, LEC.canonical());

		assertTrue(LEC.belongs(2));	
		assertTrue(LEC.belongs(14));
		assertFalse(LEC.belongs(13));	
		assertFalse(LEC.belongs(null));	
	}

	@Test
	void testRemove() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);

		assertFalse(LEC.remove(1));		//remove from an empty list
		assertFalse(LEC.remove(null));

		assertTrue(LEC.add(2));
		assertTrue(LEC.add(4));
		assertTrue(LEC.add(6));
		assertTrue(LEC.add(8));
		assertTrue(LEC.add(10));
		assertEquals(2, LEC.canonical());

		assertFalse(LEC.remove(7));		//something that isn't there and doesn't belong
		assertFalse(LEC.remove(12));		//something that isn't there but belongs
		assertFalse(LEC.remove(-1));        //something that isn't there and doesn't belong
		assertEquals(5, LEC.size());
		assertEquals("2:4,6,8,10,", LEC.toString());

		//something that is canonical
		assertTrue(LEC.remove(2));
		assertEquals(4, LEC.size());
		assertEquals("4:6,8,10,", LEC.toString());

		//something at beginning of _rest
		assertTrue(LEC.remove(6));
		assertEquals(3, LEC.size());
		assertEquals("4:8,10,", LEC.toString());

		//something at end of _rest
		assertTrue(LEC.remove(10));
		assertEquals(2, LEC.size());
		assertEquals("4:8,", LEC.toString());	


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		
		assertFalse(LEC.remove(1));		//remove from an empty list
		assertFalse(LEC.remove(null));

		assertTrue(LEC.add(3));
		assertTrue(LEC.add(7));
		assertTrue(LEC.add(11));
		assertTrue(LEC.add(15));
		assertTrue(LEC.add(19));
		assertEquals(3, LEC.canonical());

		assertFalse(LEC.remove(8));		
		assertFalse(LEC.remove(23));	
		assertEquals(5, LEC.size());
		assertEquals("3:7,11,15,19,", LEC.toString());

		//something that is canonical
		assertTrue(LEC.remove(3));
		assertEquals(4, LEC.size());
		assertEquals("7:11,15,19,", LEC.toString());

		//something at beginning of _rest
		assertTrue(LEC.remove(11));
		assertEquals(3, LEC.size());
		assertEquals("7:15,19,", LEC.toString());

		//something at end of _rest
		assertTrue(LEC.remove(19));
		assertEquals(2, LEC.size());
		assertEquals("7:15,", LEC.toString());	
	}

	@Test
	void testRemoveCanonical() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertFalse(LEC.removeCanonical());

		assertTrue(LEC.add(1));
		assertTrue(LEC.removeCanonical());
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());

		LEC.add(2);
		LEC.add(4);
		LEC.add(6);

		assertEquals(2, LEC.canonical());
		assertTrue(LEC.removeCanonical());
		assertEquals(2, LEC.size());
		assertEquals(4, LEC.canonical());
		assertEquals("4:6,", LEC.toString());	


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertFalse(LEC.removeCanonical());

		assertTrue(LEC.add(1));
		assertTrue(LEC.removeCanonical());
		assertEquals(0, LEC.size());
		assertEquals(null, LEC.canonical());
		assertEquals("", LEC.toString());

		LEC.add(2);
		LEC.add(6);
		LEC.add(10);

		assertEquals(2, LEC.canonical());
		assertTrue(LEC.removeCanonical());
		assertEquals(2, LEC.size());
		assertEquals(6, LEC.canonical());
		assertEquals("6:10,", LEC.toString());	
	}

	@Test
	void testDemoteAndSetCanonical() 
	{
		Comparator<Integer> c = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 2 == y % 2 ? 0 : 1; }
		};

		LinkedEquivalenceClass<Integer> LEC = new LinkedEquivalenceClass<>(c);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(3));
		assertTrue(LEC.add(5));
		assertEquals(3, LEC.size());

		//smoke
		assertTrue(LEC.demoteAndSetCanonical(9));
		assertEquals(9, LEC.canonical());
		assertEquals(4, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("9:3,5,1,", LEC.toString());

		//something that doesn't belong
		assertFalse(LEC.demoteAndSetCanonical(6));
		assertEquals(9, LEC.canonical());
		assertEquals(4, LEC.size());
		assertFalse(LEC.contains(6));
		assertEquals("9:3,5,1,", LEC.toString());

		//what canonical already equals
		assertFalse(LEC.demoteAndSetCanonical(9));
		assertEquals(9, LEC.canonical());
		assertEquals(4, LEC.size());
		assertEquals("9:3,5,1,", LEC.toString());

		//new element is already in class
		assertTrue(LEC.demoteAndSetCanonical(3));
		assertEquals(3, LEC.canonical());
		assertEquals(4, LEC.size());
		assertTrue(LEC.contains(3));
		assertTrue(LEC.contains(9));
		assertEquals("3:5,1,9,", LEC.toString());

		//canonical equals null, just works like add
		LEC = new LinkedEquivalenceClass<>(c);
		assertTrue(LEC.demoteAndSetCanonical(1));
		assertEquals(1, LEC.canonical());
		assertEquals(1, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("1:", LEC.toString());

		//adding the same thing to a size 1 list
		assertFalse(LEC.demoteAndSetCanonical(1));
		assertEquals(1, LEC.canonical());
		assertEquals(1, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("1:", LEC.toString());


		Comparator<Integer> c2 = new Comparator<Integer>() {
			public int compare(Integer x, Integer y) 
			{ return x % 4 == y % 4 ? 0 : 1; }
		};

		LEC = new LinkedEquivalenceClass<Integer>(c2);
		assertTrue(LEC.add(1));
		assertTrue(LEC.add(5));
		assertTrue(LEC.add(9));
		assertEquals(3, LEC.size());
		assertEquals(1, LEC.canonical());

		//smoke
		assertTrue(LEC.demoteAndSetCanonical(13));
		assertEquals(13, LEC.canonical());
		assertEquals(4, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("13:5,9,1,", LEC.toString());

		//something that doesn't belong
		assertFalse(LEC.demoteAndSetCanonical(6));
		assertEquals(13, LEC.canonical());
		assertEquals(4, LEC.size());
		assertFalse(LEC.contains(6));
		assertEquals("13:5,9,1,", LEC.toString());

		//what canonical already equals
		assertFalse(LEC.demoteAndSetCanonical(13));
		assertEquals(13, LEC.canonical());
		assertEquals(4, LEC.size());
		assertEquals("13:5,9,1,", LEC.toString());

		//new element is already in class
		assertTrue(LEC.demoteAndSetCanonical(9));
		assertEquals(9, LEC.canonical());
		assertEquals(4, LEC.size());
		assertTrue(LEC.contains(13));
		assertTrue(LEC.contains(9));
		assertEquals("9:5,1,13,", LEC.toString());

		//when canonical equals null, just works like add
		LEC = new LinkedEquivalenceClass<>(c2);
		assertTrue(LEC.demoteAndSetCanonical(1));
		assertEquals(1, LEC.canonical());
		assertEquals(1, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("1:", LEC.toString());

		//adding the same thing to a size 1 list
		assertFalse(LEC.demoteAndSetCanonical(1));
		assertEquals(1, LEC.canonical());
		assertEquals(1, LEC.size());
		assertTrue(LEC.contains(1));
		assertEquals("1:", LEC.toString());
	}
}