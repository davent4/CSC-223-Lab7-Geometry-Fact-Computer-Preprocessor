package geometry_objects.points;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import input.components.exception.NotInDatabaseException;
/**
 * Test PointNamingFactory
 * @author Jackson Tedesco, Case Riddle, Della Avent
 * @data 3/25/2024 
 */

class TestPointNamingFactory {

	@Test
	void constructorTest() {
		List<Point> points = new LinkedList<>();
		points.add(new Point("one", 2, 7));
		points.add(new Point(3, 8));
		points.add(new Point(4, 9));
		points.add(new Point("two", 2, 7));
		
		PointNamingFactory factory = new PointNamingFactory(points);
		
		assertTrue(factory.contains(new Point(2, 7)));
		assertTrue(factory.contains(new Point(3, 8)));
		assertTrue(factory.contains(new Point(4, 9)));
		assertEquals(3, factory.size());
	}
	
	@Test
	void putTest() throws NotInDatabaseException {
		PointNamingFactory factory = new PointNamingFactory();
		assertEquals(0, factory.size());
		
		factory.put(new Point("dresden", 1.0, 2.0));
		assertEquals(1, factory.size());
		assertTrue(factory.contains(new Point("dresden", 1.0, 2.0)));
		assertEquals("dresden", factory.get(1.0, 2.0).getName());
		
		factory.put(new Point("g", 6.0, 7.0));
		assertEquals(2, factory.size());
		assertTrue(factory.contains(new Point(6.0, 7.0)));
		assertEquals("g", factory.get(6.0, 7.0).getName());
		
		factory.put(new Point("dresden", 1.0, 2.0));
		assertEquals(2, factory.size());
		
		factory.put(new Point("Harry", 1.0, 2.0));
		assertEquals(2, factory.size());
		assertTrue(factory.contains(new Point(1.0, 2.0)));
		assertEquals("dresden", factory.get(1.0, 2.0).getName());
		
		factory.put(new Point(6.0, 78.0));
		assertEquals(3, factory.size());
		assertTrue(factory.contains(new Point(6.0, 78.0)));
		assertEquals("*_A", factory.get(6.0, 78.0).getName());
		
		factory.put(new Point(8.0, 5.0));
		assertEquals(4, factory.size());
		assertTrue(factory.contains(new Point(8.0, 5.0)));
		assertEquals("*_B", factory.get(8.0, 5.0).getName());
		
		factory.put(new Point("joe", 6.0, 78.0));
		assertEquals(4, factory.size());
		assertTrue(factory.contains(new Point(6.0, 78.0)));
		assertEquals("joe", factory.get(6.0, 78.0).getName());
		
		factory.put(new Point("joe", 6.5, 89));
		assertEquals(5, factory.size());
		assertTrue(factory.contains(new Point(6.5, 89)));
		assertEquals("*_C", factory.get(6.5, 89.0).getName());
		
		factory.put("bob", 1, 5);
		assertEquals(6, factory.size());
		assertTrue(factory.contains(new Point(1, 5)));
		assertEquals("bob", factory.get(1, 5).getName());
		
		factory.put(new Point("*_k", 9, 2));
		assertEquals(7, factory.size());
		assertTrue(factory.contains(new Point(9, 2)));
		assertEquals("*_D", factory.get(9, 2).getName());
		
		factory.put(1.4, 9.8);
		assertEquals(8, factory.size());
		assertTrue(factory.contains(new Point(1.4, 9.8)));
		assertEquals("*_E", factory.get(1.4, 9.8).getName());
		
		for(int i = 0; i < 60; i++) {
			factory.put(new Point(i, i));
		}
		System.out.println(factory);
		
		assertEquals("*_AA", factory.get(21, 21).getName());
		assertEquals("*_AAA", factory.get(47, 47).getName());
		
		assertEquals("*_BB", factory.get(22, 22).getName());
		assertEquals("*_BBB", factory.get(48, 48).getName());
		
		assertEquals("*_ZZ", factory.get(46, 46).getName());
		
		assertThrows(NullPointerException.class, () -> { factory.put(null);});
	}
	
	@Test
	void testGet() throws NotInDatabaseException{
		PointNamingFactory factory = new PointNamingFactory();
		
		Point p1 = new Point("dresden", 1.0, 2.0);
		factory.put(p1);
		
		assertEquals(p1, factory.get(p1));
		assertThrows(NotInDatabaseException.class, () -> { 
			factory.get(new Point("d", 2, 4)); });
		assertThrows(NullPointerException.class, () -> { factory.get(null);});
		
		assertEquals(p1, factory.get(1.0, 2.0));
		assertThrows(NotInDatabaseException.class, () -> { 
			factory.get( 2, 4);});
	}
	
	@Test
	void testGetAll() {
		PointNamingFactory factory = new PointNamingFactory();
		
		factory.put(new Point("Hiro", 1, 2));
		factory.put(new Point("Sylar", 2, 3));
		factory.put(new Point("Gella", 3, 4));
		
		Set<Point> points = factory.getAllPoints();
		
		assertTrue(points.contains(new Point("Hiro", 1, 2)));
		assertTrue(points.contains(new Point("Sylar", 2, 3)));
		assertTrue(points.contains(new Point("Gella", 3, 4)));
		assertEquals(3, points.size());
	}
}