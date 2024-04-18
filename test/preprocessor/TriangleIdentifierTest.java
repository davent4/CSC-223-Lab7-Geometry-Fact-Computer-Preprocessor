package preprocessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import components.FigureNode;
import input.InputFacade;
import input.components.exception.NotInDatabaseException;

class TriangleIdentifierTest
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
	// This figure contains 12 triangles
	//
	@Test
	void test_crossing_symmetric_triangle() throws NotInDatabaseException
	{
		init("crossing_symmetric_triangle.json");
		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);
		Set<Triangle> computedTriangles = triIdentifier.getTriangles();
		//System.out.println(computedTriangles);
		assertEquals(12, computedTriangles.size());

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
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(bd, a_star_d, a_star_b)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc, a_star_b, a_star_c)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, a_star_c, a_star_e)));
			expectedTriangles.add(new Triangle(Arrays.asList(de, a_star_d, a_star_e)));

			expectedTriangles.add(new Triangle(Arrays.asList(bd, cd, bc)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, be, bc)));

			expectedTriangles.add(new Triangle(Arrays.asList(bd, be, de)));
			expectedTriangles.add(new Triangle(Arrays.asList(ce, cd, de)));

			expectedTriangles.add(new Triangle(Arrays.asList(ab, be, ae)));
			expectedTriangles.add(new Triangle(Arrays.asList(ac, cd, ad)));

			expectedTriangles.add(new Triangle(Arrays.asList(ad, de, ae)));
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	@Test
	void test_star() throws NotInDatabaseException
	{
		init("star.json");
		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);
		Set<Triangle> computedTriangles = triIdentifier.getTriangles();
		assertEquals(10, computedTriangles.size());

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
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_a, b_star_d_star, b_star_a)));
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_b, b_star_b, a_star_b_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_c, e_star_c, a_star_e_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(c_star_d, e_star_d, c_star_e_star)));
			expectedTriangles.add(new Triangle(Arrays.asList(c_star_e, d_star_e, c_star_d_star)));

			expectedTriangles.add(new Triangle(Arrays.asList(a_star_a, a_star_d, ad)));
			expectedTriangles.add(new Triangle(Arrays.asList(b_star_e, b_star_c, ce)));
			expectedTriangles.add(new Triangle(Arrays.asList(c_star_a, c_star_c, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(d_star_b, d_star_d, bd)));
			expectedTriangles.add(new Triangle(Arrays.asList(e_star_e, e_star_b, be)));
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	@Test
	void test_square_with_interior_triangles() throws NotInDatabaseException
	{
		init("square_four_interior_implied.json");
		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);
		Set<Triangle> computedTriangles = triIdentifier.getTriangles();
		assertEquals(8, computedTriangles.size());

		//
		// ALL original segments: 6 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied points: 1 in this figure.
		//
		Point a_star = _points.getPoint(3,3);
		
		//
		// Implied minimal segments: 4 in this figure.
		//
		Segment a_star_a = new Segment(a_star, _points.getPoint("A"));
		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));

		//
		// Triangles we expect to find
		//
		List<Triangle> expectedTriangles = new ArrayList<Triangle>();
		try {
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_a, a_star_b, ab)));
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_b, a_star_c, bc)));
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_c, a_star_d, cd)));
			expectedTriangles.add(new Triangle(Arrays.asList(a_star_a, a_star_d, ad)));

			expectedTriangles.add(new Triangle(Arrays.asList(ab, ad, bd)));
			expectedTriangles.add(new Triangle(Arrays.asList(ab, bc, ac)));
			expectedTriangles.add(new Triangle(Arrays.asList(bc, cd, bd)));
			expectedTriangles.add(new Triangle(Arrays.asList(cd, ad, ac)));
		}
		catch (FactException te) { System.err.println("Invalid triangles in triangle test."); }

		assertEquals(expectedTriangles.size(), computedTriangles.size());
		
		for (Triangle computedTriangle : computedTriangles)
		{
			assertTrue(expectedTriangles.contains(computedTriangle));
		}
	}
	
	@Test
	void test_irregular_polygon() throws NotInDatabaseException
	{
		init("fully_connected_irregular_polygon.json");
		TriangleIdentifier triIdentifier = new TriangleIdentifier(_segments);
		Set<Triangle> computedTriangles = triIdentifier.getTriangles();
		assertEquals(35, computedTriangles.size());
	}
}
