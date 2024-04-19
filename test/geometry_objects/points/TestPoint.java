package geometry_objects.points;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPoint { 

	@Test
	public void testHashCode() {
		Point pointA = new Point("A", 3, 4);
		Point pointB = new Point("B", 5, 6);
		Point pointC = new Point("C", 3, 4);
		Point pointD = new Point(1, 2);
		Point pointE = new Point(1, 2);

		assertNotEquals(pointA.hashCode(), pointB.hashCode());
		assertEquals(pointA.hashCode(), pointC.hashCode());
		assertEquals(pointD.hashCode(), pointE.hashCode());
	}

	@Test
	public void testLexicographicOrdering() {
		Point pointA = new Point("A", 2, 5);
		Point pointB = new Point("B", 3, 5);
		Point pointC = new Point("C", 0, 10);
		Point pointD = new Point("D", 0, 12);

		assertTrue(Point.LexicographicOrdering(pointA, pointB) < 0);
		assertTrue(Point.LexicographicOrdering(pointC, pointD) < 0);

		Point pointE = new Point("E", 0, 5);
		Point pointF = new Point("F", 0, 6);
		Point pointG = new Point("G", 0, 10);
		Point pointH = new Point("H", 0, 12);

		assertTrue(Point.LexicographicOrdering(pointE, pointF) < 0);
		assertTrue(Point.LexicographicOrdering(pointG, pointH) < 0);

		Point pointI = new Point("I", 2, 5);
		Point pointJ = new Point("J", 2, 5);

		assertEquals(0, Point.LexicographicOrdering(pointI, pointJ));
	}


	@Test
	public void testEquals() {
		Point pointA = new Point("A", 3, 4);
		Point pointB = new Point("B", 5, 6);
		Point pointC = new Point("C", 3, 4);
		Point pointD = new Point(1, 2);
		Point pointE = new Point(1, 2);

		assertFalse(pointA.equals(pointB));
		assertTrue(pointA.equals(pointC));
		assertTrue(pointD.equals(pointE));
	}

	@Test
	public void testCompareTo() {
		Point pointA = new Point("A", 3, 4);
		Point pointB = new Point("B", 5, 6);
		Point pointC = new Point("C", 3, 4);

		assertTrue(pointA.compareTo(pointB) < 0);
		assertEquals(0, pointA.compareTo(pointC));
	}
}
