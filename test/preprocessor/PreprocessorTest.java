package preprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import components.FigureNode;
import preprocessor.delegates.ImplicitPointPreprocessor;

/**
 * @author Dr. C Alvin, Ellie Johnson, Della Avent
 * @date 4/10/24
 */

class PreprocessorTest
{
	//method to make PreProcessors with json filenames
	public Preprocessor makePreProcessor(String json_filename)
	{
		FigureNode fig = InputFacade.extractFigure(json_filename);
		
		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);
		
		PointDatabase points = pair.getKey();
		Set<Segment> segments = pair.getValue();
				
		return new Preprocessor(points, segments);
	}
	
	@Test
	void test_implicit_points()
	{		
		Preprocessor prePro = makePreProcessor("crossing_symmetric_triangle.json");
		assertEquals(1, prePro._implicitPoints.size());
		assertTrue(prePro._implicitPoints.contains(new Point(3,3)));
		
		//test with new figure
		Preprocessor square = makePreProcessor("box_with_two_lines.json");
		assertEquals(5, square._implicitPoints.size());
		assertTrue(square._implicitPoints.contains(new Point(0,2)));
		assertTrue(square._implicitPoints.contains(new Point(-2,2)));
		assertTrue(square._implicitPoints.contains(new Point(0,0)));
		
		//test with new figure	
		Preprocessor preStar = makePreProcessor("star.json");
		assertEquals(5, preStar._implicitPoints.size());
		
		//test with figure without implicit points				
		Preprocessor preGrid = makePreProcessor("grid.json");
		int sizeGrid = preGrid._implicitPoints.size();
		assertEquals(0, sizeGrid);
		
		//this figure has given points that act as implicit one of the other segments
		Preprocessor preTriangle = makePreProcessor("triangle_with_two_lines.json");
		assertEquals(0, preTriangle._implicitPoints.size());
	}
	
	@Test
	void test_implicit_Segments()
	{
		Preprocessor prePro = makePreProcessor("crossing_symmetric_triangle.json");
		int sizeImplicit = prePro._implicitSegments.size();
		assertEquals(4, sizeImplicit);
		
		//test with new figure
		Preprocessor square = makePreProcessor("box_with_two_lines.json");
		int sizeSquare = square._implicitSegments.size();
		assertEquals(16, sizeSquare);	
		
		//test with new figure
		Preprocessor preStar = makePreProcessor("star.json");
		int sizeStar = preStar._implicitSegments.size();
		assertEquals(15, sizeStar);
		
		//test with figure without implicit points
		Preprocessor preGrid = makePreProcessor("grid.json");
		int sizeGrid = preGrid._implicitSegments.size();
		assertEquals(0, sizeGrid);
		
		//this figure has given points that act as implicit one of the other segments
		Preprocessor preTriangle = makePreProcessor("triangle_with_two_lines.json");
		assertEquals(6, preTriangle._implicitSegments.size());
	}
	
	@Test
	void test_allMinimal_Segments()
	{
		Preprocessor prePro = makePreProcessor("crossing_symmetric_triangle.json");
		int sizeImplicit = prePro._allMinimalSegments.size();
		assertEquals(10, sizeImplicit);
		
		//test with new figure
		Preprocessor square = makePreProcessor("box_with_two_lines.json");
		int sizeSquare = square._allMinimalSegments.size();
		assertEquals(16, sizeSquare);	
		
		//test with new figure
		Preprocessor preStar = makePreProcessor("star.json");
		int sizeStar = preStar._allMinimalSegments.size();
		assertEquals(15, sizeStar);
		
		//test with figure without implicit points
		Preprocessor preGrid = makePreProcessor("grid.json");
		int sizeGrid = preGrid._allMinimalSegments.size();
		assertEquals(12, sizeGrid);
		
		//this figure has given points that act as implicit one of the other segments
		Preprocessor preTriangle = makePreProcessor("triangle_with_two_lines.json");
		assertEquals(9, preTriangle._allMinimalSegments.size());
	}

	@Test
	void test_allNonMinimal_Segments()
	{
		Preprocessor prePro = makePreProcessor("crossing_symmetric_triangle.json");
		int sizeImplicit = prePro._nonMinimalSegments.size();
		assertEquals(4, sizeImplicit);
		
		//test with new figure
		Preprocessor square = makePreProcessor("box_with_two_lines.json");
		int sizeSquare = square._nonMinimalSegments.size();
		assertEquals(16, sizeSquare);	
	
		//test with new figure
		Preprocessor preStar = makePreProcessor("star.json");
		int sizeStar = preStar._nonMinimalSegments.size();
		assertEquals(15, sizeStar);
		
		//test with figure without implicit points
		Preprocessor preGrid = makePreProcessor("grid.json");
		int sizeGrid = preGrid._nonMinimalSegments.size();
		assertEquals(6, sizeGrid);
		
		//this figure has given points that act as implicit one of the other segments
		Preprocessor preTriangle = makePreProcessor("triangle_with_two_lines.json");
		assertEquals(6, preTriangle._nonMinimalSegments.size());
	}
	
	@Test
	void test_implicit_crossings()
	{
		FigureNode fig = InputFacade.extractFigure("fully_connected_irregular_polygon.json");

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		// 5 new implied points inside the pentagon
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));
		assertEquals(5, iPoints.size());

		//System.out.println(iPoints);

		//
		//
		//		               D(3, 7)
		//
		//
		//   E(-2,4)       D*      E*
		//		         C*          A*       C(6, 3)
		//                      B*
		//		       A(2,0)        B(4, 0)
		//
		//		    An irregular pentagon with 5 C 2 = 10 segments

		Point a_star = new Point(56.0 / 15, 28.0 / 15); 	//*_F 
		Point b_star = new Point(16.0 / 7, 8.0 / 7);		//*_G
		Point c_star = new Point(8.0 / 9, 56.0 / 27);		//*_H
		Point d_star = new Point(90.0 / 59, 210.0 / 59);	//*_I
		Point e_star = new Point(194.0 / 55, 182.0 / 55);	//*_J

		assertTrue(iPoints.contains(a_star));
		assertTrue(iPoints.contains(b_star));
		assertTrue(iPoints.contains(c_star));
		assertTrue(iPoints.contains(d_star));
		assertTrue(iPoints.contains(e_star));

		//
		// There are 15 implied segments inside the pentagon; see figure above
		//
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);
		assertEquals(15, iSegments.size());

		List<Segment> expectedISegments = new ArrayList<Segment>();

		expectedISegments.add(new Segment(points.getPoint("A"), c_star));
		expectedISegments.add(new Segment(points.getPoint("A"), b_star));

		expectedISegments.add(new Segment(points.getPoint("B"), b_star));
		expectedISegments.add(new Segment(points.getPoint("B"), a_star));

		expectedISegments.add(new Segment(points.getPoint("C"), a_star));
		expectedISegments.add(new Segment(points.getPoint("C"), e_star));

		expectedISegments.add(new Segment(points.getPoint("D"), d_star));
		expectedISegments.add(new Segment(points.getPoint("D"), e_star));

		expectedISegments.add(new Segment(points.getPoint("E"), c_star));
		expectedISegments.add(new Segment(points.getPoint("E"), d_star));

		expectedISegments.add(new Segment(c_star, b_star));
		expectedISegments.add(new Segment(b_star, a_star));
		expectedISegments.add(new Segment(a_star, e_star));
		expectedISegments.add(new Segment(e_star, d_star));
		expectedISegments.add(new Segment(d_star, c_star));

		for (Segment iSegment : iSegments)
		{
			assertTrue(expectedISegments.contains(iSegment));
		}

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
		expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("B")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("D")));
		expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("E"), points.getPoint("A")));
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);
		assertEquals(expectedMinimalSegments.size(), minimalSegments.size());

		for (Segment minimalSeg : minimalSegments)
		{
			assertTrue(expectedMinimalSegments.contains(minimalSeg));
		}
		
		//
		// Construct ALL figure segments from the base segments
		//
		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//
		// All Segments will consist of the new 15 non-minimal segments.
		//
		assertEquals(15, computedNonMinimalSegments.size());

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("D")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));		

		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("C")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("D")));
		
		//
		// Check size and content equality
		//
		assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());

		for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
		{
			assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
		}
	}
}