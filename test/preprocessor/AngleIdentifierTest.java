package preprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import components.FigureNode;
import input.components.exception.NotInDatabaseException;
import input.InputFacade;

/**
 * @author Dr. C Alvin, Della Avent, Ellie Johnson, Jack Roberts
 * @date 4/19/24
 */

class AngleIdentifierTest
{
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	
	protected void init(String filename)
	{
		FigureNode fig = InputFacade.extractFigure(filename);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
	}
	
	//      A                                 
	//     / \                                
	//    B___C                               
	//   / \ / \                              
	//  /   X   \  X is not a specified point (it is implied) 
	// D_________E
	//
	// This figure contains 44 angles
	//
	@Test
	void test_crossing_symmetric_triangle() throws NotInDatabaseException
	{
		init("crossing_symmetric_triangle.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 25, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _points.getPoint(3,3);

		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));
		Segment a_star_e = new Segment(a_star, _points.getPoint("E"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_e));
			
			expectedAngles.add(new Angle(ac, ce));
			
			expectedAngles.add(new Angle(a_star_d, a_star_c));
			
			expectedAngles.add(new Angle(ab, bd));
			
			// right angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			
			expectedAngles.add(new Angle(a_star_d, a_star_e));

			expectedAngles.add(new Angle(a_star_c, a_star_e));
			
			// 
			//
			expectedAngles.add(new Angle(a_star_b, ab));
			expectedAngles.add(new Angle(be, ab));

			expectedAngles.add(new Angle(a_star_b, bc));
			expectedAngles.add(new Angle(be, bc));

			expectedAngles.add(new Angle(a_star_b, bd));
			expectedAngles.add(new Angle(be, bd));
			
			expectedAngles.add(new Angle(ac, a_star_c));
			expectedAngles.add(new Angle(ac, cd));

			expectedAngles.add(new Angle(bc, a_star_c));
			expectedAngles.add(new Angle(cd, bc));
			
			expectedAngles.add(new Angle(a_star_c, ce));
			expectedAngles.add(new Angle(cd, ce));
			
			expectedAngles.add(new Angle(a_star_d, de));
			expectedAngles.add(new Angle(cd, de));
			
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(ad, de));
			
			expectedAngles.add(new Angle(a_star_e, de));
			expectedAngles.add(new Angle(be, de));

			expectedAngles.add(new Angle(ce, de));
			expectedAngles.add(new Angle(ae, de));
			
			// Larger equivalence classes
			//
			expectedAngles.add(new Angle(ac, ab));
			expectedAngles.add(new Angle(ab, ae));
			expectedAngles.add(new Angle(ad, ae));
			expectedAngles.add(new Angle(ac, ad));

			expectedAngles.add(new Angle(a_star_d, bd));
			expectedAngles.add(new Angle(cd, bd));
			expectedAngles.add(new Angle(ad, a_star_d));
			expectedAngles.add(new Angle(cd, ad));

			expectedAngles.add(new Angle(a_star_e, ce));
			expectedAngles.add(new Angle(be, ae));
			expectedAngles.add(new Angle(be, ce));
			expectedAngles.add(new Angle(a_star_e, ae));

			
			// More singletons
			//
			expectedAngles.add(new Angle(ac, bc));

			expectedAngles.add(new Angle(ab, bc));
			
			expectedAngles.add(new Angle(bc, bd));

			expectedAngles.add(new Angle(bc, ce));			
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }
		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
	}
	
	@Test
	void test_star() throws NotInDatabaseException
	{
		init("star.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 35, computedAngles.numClasses());

		//
		// ALL original segments: 5 in this figure.
		//
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));

		//
		// Implied minimal points: 5 in this figure.
		//
		Point a_star = _points.getPoint(9.30952380952381, 7.416666666666667);	//*_F
		Point b_star = _points.getPoint(7.811320754716981, 6.367924528301887);	//*_G
		Point c_star = _points.getPoint(6.888888888888889, 10);					//*_H
		Point d_star = _points.getPoint(5.628865979381444, 7.731958762886599);	//*_I
		Point e_star = _points.getPoint(8.571428571428571, 10);					//*_J
		
		//
		// Implied minimal segments: 15 in this figure.
		//
		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_e_star = new Segment(a_star, e_star);
		Segment a_star_b_star = new Segment(a_star, b_star);
		
		Segment b_star_b = new Segment(b_star, _points.getPoint("B"));
		Segment b_star_a = new Segment(b_star, _points.getPoint("A"));
		Segment b_star_d_star = new Segment(b_star, d_star);
		
		Segment c_star_d = new Segment(c_star, _points.getPoint("D"));
		Segment c_star_e = new Segment(c_star, _points.getPoint("E"));
		Segment c_star_e_star = new Segment(c_star, e_star);
		Segment c_star_d_star = new Segment(c_star, d_star);
		
		Segment d_star_a = new Segment(d_star, _points.getPoint("A"));
		Segment d_star_e = new Segment(d_star, _points.getPoint("E"));
		
		Segment e_star_d = new Segment(e_star, _points.getPoint("D"));
		Segment e_star_c = new Segment(e_star, _points.getPoint("C"));

		//
		// new non-minimal, computed segments: 10 in this figure. (15 total)
		//
		Segment a_star_a = new Segment(a_star, _points.getPoint("A"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));
		
		Segment b_star_c = new Segment(b_star, _points.getPoint("C"));
		Segment b_star_e = new Segment(b_star, _points.getPoint("E"));
		
		Segment c_star_a = new Segment(c_star, _points.getPoint("A"));
		Segment c_star_c = new Segment(c_star, _points.getPoint("C"));
		
		Segment d_star_d = new Segment(d_star, _points.getPoint("D"));
		Segment d_star_b = new Segment(d_star, _points.getPoint("B"));
		
		Segment e_star_e = new Segment(e_star, _points.getPoint("E"));
		Segment e_star_b = new Segment(e_star, _points.getPoint("B"));
		
		//
		// Angles we expect to find
		// 110 total
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			// 20 total
			expectedAngles.add(new Angle(a_star_b, a_star_e_star));
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_c, a_star_b_star));
			expectedAngles.add(new Angle(a_star_c, a_star_a));
			
			expectedAngles.add(new Angle(b_star_a, a_star_b_star));
			expectedAngles.add(new Angle(b_star_c, b_star_a));
			
			expectedAngles.add(new Angle(b_star_b, b_star_d_star));
			expectedAngles.add(new Angle(b_star_b, b_star_e));
			
			expectedAngles.add(new Angle(c_star_e, c_star_e_star));
			expectedAngles.add(new Angle(c_star_e, c_star_c));
			
			expectedAngles.add(new Angle(c_star_d, c_star_d_star));
			expectedAngles.add(new Angle(c_star_d, c_star_a));
			
			expectedAngles.add(new Angle(d_star_a, c_star_d_star));
			expectedAngles.add(new Angle(d_star_a, d_star_d));
			
			expectedAngles.add(new Angle(d_star_e, b_star_d_star));
			expectedAngles.add(new Angle(d_star_e, d_star_b));
			
			expectedAngles.add(new Angle(e_star_d, a_star_e_star));
			expectedAngles.add(new Angle(e_star_d, e_star_b));
			
			expectedAngles.add(new Angle(e_star_c, c_star_e_star));
			expectedAngles.add(new Angle(e_star_c, e_star_e));
			
			// points of star
			// 45 total
			expectedAngles.add(new Angle(d_star_a, b_star_a)); //canonical
			expectedAngles.add(new Angle(c_star_a, b_star_a));
			expectedAngles.add(new Angle(ad, b_star_a));
			expectedAngles.add(new Angle(d_star_a, a_star_a)); 
			expectedAngles.add(new Angle(d_star_a, ac)); 
			expectedAngles.add(new Angle(c_star_a, a_star_a)); 
			expectedAngles.add(new Angle(c_star_a, ac));
			expectedAngles.add(new Angle(ad, a_star_a));
			expectedAngles.add(new Angle(ad, ac));
			
			expectedAngles.add(new Angle(b_star_b, a_star_b)); //canonical
			expectedAngles.add(new Angle(d_star_b, a_star_b));
			expectedAngles.add(new Angle(be, a_star_b));
			expectedAngles.add(new Angle(b_star_b, e_star_b)); 
			expectedAngles.add(new Angle(b_star_b, bd)); 
			expectedAngles.add(new Angle(d_star_b, e_star_b)); 
			expectedAngles.add(new Angle(d_star_b, bd));
			expectedAngles.add(new Angle(be, e_star_b));
			expectedAngles.add(new Angle(be, bd));
			
			expectedAngles.add(new Angle(a_star_c, e_star_c)); //canonical
			expectedAngles.add(new Angle(b_star_c, e_star_c));
			expectedAngles.add(new Angle(ac, e_star_c));
			expectedAngles.add(new Angle(a_star_c, c_star_c)); 
			expectedAngles.add(new Angle(a_star_c, ce)); 
			expectedAngles.add(new Angle(b_star_c, c_star_c)); 
			expectedAngles.add(new Angle(b_star_c, ce));
			expectedAngles.add(new Angle(ac, c_star_c));
			expectedAngles.add(new Angle(ac, ce));

			expectedAngles.add(new Angle(e_star_d, c_star_d)); //canonical
			expectedAngles.add(new Angle(e_star_d, d_star_d));
			expectedAngles.add(new Angle(e_star_d, ad));
			expectedAngles.add(new Angle(a_star_d, c_star_d));
			expectedAngles.add(new Angle(bd, c_star_d));
			expectedAngles.add(new Angle(a_star_d, d_star_d));
			expectedAngles.add(new Angle(bd, d_star_d));
			expectedAngles.add(new Angle(a_star_d, ad));
			expectedAngles.add(new Angle(bd, ad));
			
			expectedAngles.add(new Angle(c_star_e, d_star_e)); //canonical
			expectedAngles.add(new Angle(c_star_e, b_star_e));
			expectedAngles.add(new Angle(c_star_e, be));
			expectedAngles.add(new Angle(e_star_e, d_star_e));
			expectedAngles.add(new Angle(ce, d_star_e));
			expectedAngles.add(new Angle(e_star_e, b_star_e));
			expectedAngles.add(new Angle(ce, b_star_e));
			expectedAngles.add(new Angle(e_star_e, be));
			expectedAngles.add(new Angle(ce, be));
			
			// bottom right inside corner angles of clear triangle
			// 10 total
			expectedAngles.add(new Angle(a_star_c, a_star_e_star));
			expectedAngles.add(new Angle(a_star_c, a_star_d));
			
			expectedAngles.add(new Angle(b_star_b, a_star_b_star));
			expectedAngles.add(new Angle(b_star_b, b_star_c));
			
			expectedAngles.add(new Angle(c_star_e, c_star_d_star));
			expectedAngles.add(new Angle(c_star_e, c_star_a));
			
			expectedAngles.add(new Angle(d_star_a, b_star_d_star));
			expectedAngles.add(new Angle(d_star_a, d_star_b));
			
			expectedAngles.add(new Angle(e_star_d, c_star_e_star));
			expectedAngles.add(new Angle(e_star_d, e_star_e));
			
			// bottom left inside corner angles of clear triangle
			// 10 total
			expectedAngles.add(new Angle(a_star_b, a_star_b_star));
			expectedAngles.add(new Angle(a_star_b, a_star_a));

			expectedAngles.add(new Angle(b_star_a, b_star_d_star));
			expectedAngles.add(new Angle(b_star_a, b_star_e));

			expectedAngles.add(new Angle(c_star_d, c_star_e_star));
			expectedAngles.add(new Angle(c_star_d, c_star_c));

			expectedAngles.add(new Angle(d_star_e, c_star_d_star));
			expectedAngles.add(new Angle(d_star_e, d_star_d));

			expectedAngles.add(new Angle(e_star_c, a_star_e_star));
			expectedAngles.add(new Angle(e_star_c, e_star_b));

			// inside pentagon angles
			// 20 total
			expectedAngles.add(new Angle(a_star_b_star, a_star_e_star)); //canonical
			expectedAngles.add(new Angle(a_star_b_star, a_star_d));
			expectedAngles.add(new Angle(a_star_a, a_star_e_star));
			expectedAngles.add(new Angle(a_star_a, a_star_d));
			
			expectedAngles.add(new Angle(a_star_b_star, b_star_d_star)); //canonical
			expectedAngles.add(new Angle(a_star_b_star, b_star_e));
			expectedAngles.add(new Angle(b_star_c, b_star_d_star));
			expectedAngles.add(new Angle(b_star_c, b_star_e));
			
			expectedAngles.add(new Angle(c_star_d_star, c_star_e_star)); //canonical
			expectedAngles.add(new Angle(c_star_d_star, c_star_c));
			expectedAngles.add(new Angle(c_star_a, c_star_e_star));
			expectedAngles.add(new Angle(c_star_a, c_star_c));
			
			expectedAngles.add(new Angle(c_star_d_star, b_star_d_star)); //canonical
			expectedAngles.add(new Angle(c_star_d_star, d_star_b));
			expectedAngles.add(new Angle(d_star_d, b_star_d_star));
			expectedAngles.add(new Angle(d_star_d, d_star_b));
			
			expectedAngles.add(new Angle(a_star_e_star, c_star_e_star)); //canonical
			expectedAngles.add(new Angle(a_star_e_star, e_star_e));
			expectedAngles.add(new Angle(e_star_b, c_star_e_star));
			expectedAngles.add(new Angle(e_star_b, e_star_e));
			
			// singletons (outside shape)
			// 5 total
			expectedAngles.add(new Angle(a_star_b, a_star_c));

			expectedAngles.add(new Angle(b_star_b, b_star_a));
			
			expectedAngles.add(new Angle(c_star_d, c_star_e));

			expectedAngles.add(new Angle(d_star_a, d_star_e));
			
			expectedAngles.add(new Angle(e_star_d, e_star_c));
		}
		
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }

		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
	}
}
