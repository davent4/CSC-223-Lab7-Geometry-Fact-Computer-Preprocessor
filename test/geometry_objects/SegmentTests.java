package geometry_objects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;
import input.components.point.PointNode;
import utilities.math.MathUtilities;

class SegmentTests {

	@Test
	void testCollectOrderedPointsHorizontalLine() {
		// s =>  p1 --------- p2
		Segment s = new Segment(new Point("pt1", 0,2), new Point("pt2", 5,2));
		assertTrue(MathUtilities.doubleEquals(s.slope(), 0));

		ArrayList<Point> pArray = new ArrayList<Point> (Arrays.asList(	new Point("A", -2,2), //pre endpoint 1 (x off)
																		new Point("c", 5,2), //endpoint 2
																		new Point("a", 0,2), //endpoint 1
																		new Point("b", 3,2), //middle
																		new Point("B", 7,2), //past endpoint 2 (x off)
																		new Point("C", 10 ,3), // x and y off
																		new Point("D", 0, 3))); //above endpt 1 y off

		Set<Point> points = new HashSet<Point>();
		points.addAll(pArray);
		assertTrue(points.containsAll(pArray));

		SortedSet<Point> sorted = s.collectOrderedPointsOnSegment(points);
		assertEquals(sorted.size(), 3);
		assertTrue(sorted.contains(new Point("a", 0,2)));
		assertTrue(sorted.contains(new Point("b", 3,2)));
		assertTrue(sorted.contains(new Point("c", 5,2)));
		assertFalse(sorted.contains(new Point("A", -2,2)));
		assertFalse(sorted.contains(new Point("B", 7,2)));
		assertFalse(sorted.contains(new Point("C", 10,3)));
		assertFalse(sorted.contains(new Point("D", 0,3)));

		//checks order
		assertEquals(sorted.toString(), "[a[0.0, 2.0], b[3.0, 2.0], c[5.0, 2.0]]");
	}

	@Test
	void testCollectOrderedPointsVerticalLine() {
		//			E
		// s => D	p2(d)
		//			c
		//			|
		//			| C
		//			b
		//			|
		//			|
		//			p1(a)		B
		//			A
		Segment s = new Segment(new Point("pt1", 0,0), new Point("pt2", 0,5));

		ArrayList<Point> pArray = new ArrayList<Point> (Arrays.asList(	new Point("D", -2,5),
																		new Point("c", 0,4.95),
																		new Point("B", 2,0),
																		new Point("a", 0,0), 
																		new Point("E", 0,5.1),
																		new Point("b", 0,3),
																		new Point("A", 0,-2),
																		new Point("C", .5,4),
																		new Point("d", 0,5))); 
		Set<Point> points = new HashSet<Point>();
		points.addAll(pArray);
		assertTrue(points.containsAll(pArray));

		SortedSet<Point> sorted = s.collectOrderedPointsOnSegment(points);
		assertEquals(sorted.size(), 4);
		assertTrue(sorted.contains(new Point("a", 0,0)));
		assertTrue(sorted.contains(new Point("b", 0,3)));
		assertTrue(sorted.contains(new Point("c", 0,4.95)));
		assertTrue(sorted.contains(new Point("d", 0,5)));
		assertFalse(sorted.contains(new Point("A", 0,-2)));
		assertFalse(sorted.contains(new Point("B", 2,0)));
		assertFalse(sorted.contains(new Point("C", 0.5,4)));
		assertFalse(sorted.contains(new Point("D", -2,5)));
		assertFalse(sorted.contains(new Point("E", 0,5.1)));

		//checks order
		assertEquals(sorted.toString(), "[a[0.0, 0.0], b[0.0, 3.0], c[0.0, 4.95], d[0.0, 5.0]]");
	}

	@Test
	void testCollectOrderedPointsNegSlopeLine() {
		//	   G
	//   s => F p1(a) E
		//		Db
		//		  \
		//		   \
		//		    \
		//		     \C
		//		B	  p2(c)	A
		//			   H
		Segment s = new Segment(new Point("pt1", 5,0), new Point("pt2", 0,5));
		assertTrue(MathUtilities.doubleEquals(s.slope(), -1));

		ArrayList<Point> pArray = new ArrayList<Point> (Arrays.asList(	new Point("c", 5,0), 
																		new Point("b", 4.9,0.1), 
																		new Point("a", 0,5), 
																		new Point("A", 5.1,0), 
																		new Point("B", 0,0),
																		new Point("C", 5,0.1),
																		new Point("D", 0,4.9),
																		new Point("E", 1,5),
																		new Point("F", -1,5),
																		new Point("G", -0.1,5.1),
																		new Point("H", 5.1,-0.1))); 
		Set<Point> points = new HashSet<Point>();
		points.addAll(pArray);
		assertTrue(points.containsAll(pArray));

		SortedSet<Point> sorted = s.collectOrderedPointsOnSegment(points);
		assertEquals(sorted.size(), 3);
		assertTrue(sorted.contains(new Point("c", 5,0)));
		assertTrue(sorted.contains(new Point("b", 4.9,0.1)));
		assertTrue(sorted.contains(new Point("a", 0,5)));
		assertFalse(sorted.contains(new Point("A", 5.1,0)));
		assertFalse(sorted.contains(new Point("B", 0,0)));
		assertFalse(sorted.contains(new Point("C", 5,0.1)));
		assertFalse(sorted.contains(new Point("D", 0,4.9)));
		assertFalse(sorted.contains(new Point("E", 1,5)));
		assertFalse(sorted.contains(new Point("F", -1,5)));
		assertFalse(sorted.contains(new Point("G", -0.1,5.1)));
		assertFalse(sorted.contains(new Point("H", 5.1,-0.1)));

		//checks order
		assertEquals(sorted.toString(), "[a[0.0, 5.0], b[4.9, 0.1], c[5.0, 0.0]]");
	}

	@Test
	void testHasNegSlopeSubSegment() {
		//s =>	p2
		//		  \
		//		   \
		//		    \
		//		     \
		//			  p1
		Segment s = new Segment(new Point(5,0), new Point(0,5));
		

		assertTrue(s.HasSubSegment(s)); //itself
		Segment t1 = new Segment(new Point(4,1), new Point(1,4));
		assertTrue(s.HasSubSegment(t1)); //perfectly within
		Segment t2 = new Segment(new Point(4,1), new Point(0,5));
		assertTrue(s.HasSubSegment(t2)); //within to pt2
		Segment t3 = new Segment(new Point(5,0), new Point(1,4));
		assertTrue(s.HasSubSegment(t3)); //pt1 to within
		Segment t4 = new Segment(new Point(4,1), new Point(-1,6));
		assertFalse(s.HasSubSegment(t4)); //within to beyond
		Segment t5 = new Segment(new Point(6,-1), new Point(1,4));
		assertFalse(s.HasSubSegment(t5)); //beyond to within
		Segment t6 = new Segment(new Point(6,-1), new Point(-1,6));
		assertFalse(s.HasSubSegment(t6)); //beyond to beyond (is a subseg, not has)
		Segment t7 = new Segment(new Point(5.1,0), new Point(1,4));
		assertFalse(s.HasSubSegment(t7)); //slightly off pt1 to within
		Segment t8 = new Segment(new Point(1,4), new Point(0,5.1));
		assertFalse(s.HasSubSegment(t8)); //within to slightly off pt2 
		Segment t9 = new Segment(new Point(5,5), new Point(0,0));
		assertFalse(s.HasSubSegment(t9)); //intersect, not subsegment
	}
	
	@Test
	void testHasVerticalSubSegment() {
		// s => 	p2
		//			|
		//			| 
		//			|
		//			|
		//			p1
		Segment s = new Segment(new Point(0,0), new Point(0,5));

		assertTrue(s.HasSubSegment(s)); //itself
		Segment t1 = new Segment(new Point(0,1), new Point(0,4));
		assertTrue(s.HasSubSegment(t1)); //perfectly within
		Segment t2 = new Segment(new Point(0,1), new Point(0,5));
		assertTrue(s.HasSubSegment(t2)); //within to pt2
		Segment t3 = new Segment(new Point(0,0), new Point(0,4));
		assertTrue(s.HasSubSegment(t3)); //pt1 to within
		Segment t4 = new Segment(new Point(0,1), new Point(0,6));
		assertFalse(s.HasSubSegment(t4)); //within to beyond
		Segment t5 = new Segment(new Point(0,-1), new Point(0,4));
		assertFalse(s.HasSubSegment(t5)); //beyond to within
		Segment t6 = new Segment(new Point(0,-1), new Point(0,6));
		assertFalse(s.HasSubSegment(t6)); //beyond to beyond (s *is* a subseg of t6, not has)
		Segment t7 = new Segment(new Point(0.1,0), new Point(0,4));
		assertFalse(s.HasSubSegment(t7)); //slightly off pt1 to within
		Segment t8 = new Segment(new Point(0,1), new Point(0,5.1));
		assertFalse(s.HasSubSegment(t8)); //within to slightly off pt2 
		Segment t9 = new Segment(new Point(0,4), new Point(4,4));
		assertFalse(s.HasSubSegment(t9)); //intersect, not subsegment
	}
	
	@Test
	void testHasHorizontalSubSegment() {
		// s =>  p1 --------- p2
		Segment s = new Segment(new Point("pt1", 0,0), new Point("pt2", 5,0));

		assertTrue(s.HasSubSegment(s)); //itself
		Segment t1 = new Segment(new Point(1,0), new Point(4,0));
		assertTrue(s.HasSubSegment(t1)); //perfectly within
		Segment t2 = new Segment(new Point(1,0), new Point(5,0));
		assertTrue(s.HasSubSegment(t2)); //within to pt2
		Segment t3 = new Segment(new Point(0,0), new Point(4,0));
		assertTrue(s.HasSubSegment(t3)); //pt1 to within
		Segment t4 = new Segment(new Point(1,0), new Point(6,0));
		assertFalse(s.HasSubSegment(t4)); //within to beyond
		Segment t5 = new Segment(new Point(-1,0), new Point(4,0));
		assertFalse(s.HasSubSegment(t5)); //beyond to within
		Segment t6 = new Segment(new Point(-1,0), new Point(6,0));
		assertFalse(s.HasSubSegment(t6)); //beyond to beyond (is a subseg, not has)
		Segment t7 = new Segment(new Point(0,0.1), new Point(4,0));
		assertFalse(s.HasSubSegment(t7)); //slightly off pt1 to within
		Segment t8 = new Segment(new Point(1,0), new Point(5,0.1));
		assertFalse(s.HasSubSegment(t8)); //within to slightly off pt2 
		Segment t9 = new Segment(new Point(0,0), new Point(0,5));
		assertFalse(s.HasSubSegment(t9)); //intersect, not subsegment
	}

	@Test
	void testCoincideWithPosSlope() {
		//s =>	  p2
		//		 /
		//	    /
		//     /
		//	  /
		//	p1
		Segment s = new Segment(new Point(0,0), new Point(5,5));
		
		Segment t = new Segment(new Point(-5,-5), new Point(0,0));
		assertTrue(s.coincideWithoutOverlap(t)); //smoke test shared endpt1
		Segment t1 = new Segment(new Point(5,5), new Point(10,10));
		assertTrue(s.coincideWithoutOverlap(t1)); //smoke test shared endpt2
		Segment t2 = new Segment(new Point(6,6), new Point(10,10));
		assertTrue(s.coincideWithoutOverlap(t2)); //smoke sep
		Segment t3 = new Segment(new Point(0,0), new Point(6,6));
		assertFalse(s.coincideWithoutOverlap(t3)); //same line but longer
		Segment t4 = new Segment(new Point(5,5.1), new Point(6,6.1));
		assertFalse(s.coincideWithoutOverlap(t4)); //endpt slightly off
		Segment t5 = new Segment(new Point(0,1), new Point(5,6));
		assertFalse(s.coincideWithoutOverlap(t5)); //parallel
		Segment t6 = new Segment(new Point(-3,3), new Point(3,-3));
		assertFalse(s.coincideWithoutOverlap(t6)); //perpendicular, but endpt is on t6
	}
	
	//@Test
	void testCoincideWithVertical() {
		// s => 	p2
		//			|
		//			| 
		//			|
		//			|
		//			p1
		Segment s = new Segment(new Point(0,0), new Point(0,5));

		Segment t = new Segment(new Point(0,-5), new Point(0,0));
		assertTrue(s.coincideWithoutOverlap(t)); //smoke test shared endpt1
		Segment t1 = new Segment(new Point(0,5), new Point(0,10));
		assertTrue(s.coincideWithoutOverlap(t1)); //smoke test shared endpt2
		Segment t2 = new Segment(new Point(0,6), new Point(0,10));
		assertTrue(s.coincideWithoutOverlap(t2)); //smoke sep
		Segment t3 = new Segment(new Point(0,0), new Point(0,6));
		assertFalse(s.coincideWithoutOverlap(t3)); //same line but longer
		Segment t4 = new Segment(new Point(0.1,5), new Point(0.1,6));
		assertFalse(s.coincideWithoutOverlap(t4)); //endpt slightly off
		Segment t5 = new Segment(new Point(1,0), new Point(1,5));
		assertFalse(s.coincideWithoutOverlap(t5)); //parallel
		Segment t6 = new Segment(new Point(0,0), new Point(3,0));
		assertFalse(s.coincideWithoutOverlap(t6)); //perpendicular, but shared endpt
	}
	
	//@Test
	void testCoincideWithHorizontal() {
		// s =>  p1 --------- p2
		Segment s = new Segment(new Point("pt1", 0,0), new Point("pt2", 5,0));

		Segment t = new Segment(new Point(-5,0), new Point(0,0));
		assertTrue(s.coincideWithoutOverlap(t)); //smoke test shared endpt1
		Segment t1 = new Segment(new Point(5,0), new Point(10,0));
		assertTrue(s.coincideWithoutOverlap(t1)); //smoke test shared endpt2
		Segment t2 = new Segment(new Point(6,0), new Point(10,0));
		assertTrue(s.coincideWithoutOverlap(t2)); //smoke sep
		Segment t3 = new Segment(new Point(0,0), new Point(6,0));
		assertFalse(s.coincideWithoutOverlap(t3)); //same line but longer
		Segment t4 = new Segment(new Point(5,0.1), new Point(6,0.1));
		assertFalse(s.coincideWithoutOverlap(t4)); //endpt slightly off
		Segment t5 = new Segment(new Point(0,1), new Point(5,1));
		assertFalse(s.coincideWithoutOverlap(t5)); //parallel
		Segment t6 = new Segment(new Point(0,0), new Point(0,3));
		assertFalse(s.coincideWithoutOverlap(t6)); //perpendicular, but shared endpt
	}
}
