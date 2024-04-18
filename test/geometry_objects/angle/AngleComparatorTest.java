package geometry_objects.angle;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

class AngleComparatorTest {

	@Test
	public Angle makeAngle(Point ptone, Point pttwo, Point ptthree, Point ptfour)
	{
		Segment one = new Segment(ptone, pttwo);
		Segment two = new Segment(ptthree, ptfour);
		try {
			return new Angle(one, two);
		} catch (FactException e) {
		}
		return null;
	}

	@Test
	void angleComparatorTest()
	{
		//compares true case with right angle
		AngleStructureComparator compare = new AngleStructureComparator();
		Angle t0 = makeAngle(new Point (0,0), new Point(0,5), new Point(0,0), new Point(5, 0));
		Angle t1 = makeAngle(new Point (0,0), new Point(0,7), new Point(0,0), new Point(7, 0));
		assertTrue(compare.compare(t0, t1) == -1);	
		assertTrue(compare.compare(t1, t0) == 1);
		
		Angle t2 = makeAngle(new Point (0,0), new Point(3,5), new Point(0,0), new Point(5, 0));
		assertTrue(compare.compare(t0, t2) == AngleStructureComparator.STRUCTURALLY_INCOMPARABLE);
		Angle t3 = makeAngle(new Point (0,0), new Point(0,5), new Point(0,0), new Point(-5, 0));
		assertTrue(compare.compare(t0, t3) == AngleStructureComparator.STRUCTURALLY_INCOMPARABLE);
		Angle t4 = makeAngle(new Point (1,0), new Point(0,5), new Point(1,0), new Point(-5, 0));
		assertTrue(compare.compare(t0, t4) == AngleStructureComparator.STRUCTURALLY_INCOMPARABLE);
		
		Angle t5 = makeAngle(new Point (0,0), new Point(0,4), new Point(0,0), new Point(7, 0));
		assertTrue(compare.compare(t1, t5) == 1);
		Angle t6 = makeAngle(new Point (0,0), new Point(0,7), new Point(0,0), new Point(4, 0));
		assertTrue(compare.compare(t1, t6) == 1);

		Angle t7 = makeAngle(new Point (0,0), new Point(0,5), new Point(0,0), new Point(7, 0));
		assertTrue(compare.compare(t0, t7) == -1);	
		Angle t8 = makeAngle(new Point (0,0), new Point(0,5), new Point(0,0), new Point(3, 0));
		assertTrue(compare.compare(t0, t8) == 1);	
		
		Angle t9 = makeAngle(new Point(0,10), new Point(0,0), new Point(3,0), new Point(0,0));
		assertTrue(compare.compare(t1, t9) == 0);
		assertTrue(compare.compare(t9, t1) == 0);
		
		//same returns -1
		assertTrue(compare.compare(t1, t1) == -1);
	}
	
	@Test
	void angleEquals()
	{
		Angle t0 = makeAngle(new Point (0,5), new Point(0,0), new Point(5,0), new Point(0, 0));
		Angle t2 = makeAngle(new Point (0,0), new Point(0,5), new Point(0,0), new Point(5, 0));
		assertTrue(t0.equals(t2));
	}
}
