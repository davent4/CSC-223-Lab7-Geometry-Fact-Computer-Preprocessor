import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import components.ComponentNode;
import components.FigureNode;
import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import input.builder.GeometryBuilder;
import input.components.exception.NotInDatabaseException;
import input.components.exception.ParseException;
import input.components.parser.JSONParser;
import input.components.visitor.ToJSONvisitor;
import input.components.visitor.UnparseVisitor;

class TestInputFacade {
	
	public static FigureNode genericTestSet(String filename) throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = InputFacade.extractFigure(filename);
		
		assertTrue(node instanceof FigureNode);
		
		return node;
	}

	@Test
	void empty_json_string_test()
	{
		FigureNode node = InputFacade.extractFigure("{}");
		assertNull(node);
	}

	@Test
	void single_triangle_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("single_triangle.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 3);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 0, 0));
		assertEquals(pd.getPoint("B"), new Point("B", 1, 1));
		assertEquals(pd.getPoint("C"), new Point("C", 1, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 3);
		
		//spot checks (comprehensive)
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("B", 1, 1))));
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("C", 1, 0))));
		assertTrue(segments.contains(new Segment(new Point("C", 1, 0), new Point("B", 1, 1))));
		
		//catches that point name is the same, but the point itself dne in the PointDatabase
		assertFalse(segments.contains(new Segment(new Point("A", 1, 2), new Point("B", 1, 1))));
	}

	@Test
	void collinear_line_segments_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("collinear_line_segments.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 6);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 0, 0));
		assertEquals(pd.getPoint("B"), new Point("B", 4, 0));
		assertEquals(pd.getPoint("C"), new Point("C", 9, 0));
		assertEquals(pd.getPoint("D"), new Point("D", 11, 0));
		assertEquals(pd.getPoint("E"), new Point("E", 16, 0));
		assertEquals(pd.getPoint("F"), new Point("F", 26, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 5);
		
		//can catch segment either direction
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("B", 4, 0))));
		assertTrue(segments.contains(new Segment(new Point("B", 4, 0), new Point("A", 0, 0))));
		
		//spot checks (comprehensive)
		assertTrue(segments.contains(new Segment(new Point("B", 4, 0), new Point("C", 9, 0))));
		assertTrue(segments.contains(new Segment(new Point("C", 9, 0), new Point("D", 11, 0))));
		assertTrue(segments.contains(new Segment(new Point("D", 11, 0), new Point("E", 16, 0))));
		assertTrue(segments.contains(new Segment(new Point("E", 16, 0), new Point("F", 26, 0))));
		
		//points there but no segment
		assertFalse(segments.contains(new Segment(new Point("A", 0, 0), new Point("F", 26, 0))));
	}

	@Test
	void crossing_symmetric_triangle_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("crossing_symmetric_triangle.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 5);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 3, 6));
		assertEquals(pd.getPoint("B"), new Point("B", 2, 4));
		assertEquals(pd.getPoint("C"), new Point("C", 4, 4));
		assertEquals(pd.getPoint("D"), new Point("D", 0, 0));
		assertEquals(pd.getPoint("E"), new Point("E", 6, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 8);
		
		//spot checks 
		assertTrue(segments.contains(new Segment(new Point("A", 3, 6), new Point("B", 2, 4))));
		assertTrue(segments.contains(new Segment(new Point("A", 3, 6), new Point("C", 4, 4))));
		assertTrue(segments.contains(new Segment(new Point("B", 2, 4), new Point("C", 4, 4))));
		assertTrue(segments.contains(new Segment(new Point("C", 4, 4), new Point("E", 6, 0))));
		assertTrue(segments.contains(new Segment(new Point("D", 0, 0), new Point("E", 6, 0))));
		
		//point is there, but a different name
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("E", 6, 0))));
	}
	
	@Test
	void fully_connected_irregular_polygon_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("fully_connected_irregular_polygon.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 6);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 0, 0));
		assertEquals(pd.getPoint("B"), new Point("B", 4, 0));
		assertEquals(pd.getPoint("C"), new Point("C", 6, 3));
		assertEquals(pd.getPoint("D"), new Point("D", 3, 7));
		assertEquals(pd.getPoint("E"), new Point("E", -2, 4));
		assertEquals(pd.getPoint("F"), new Point("F", 26, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 10);
		
		//spot checks 
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("B", 4, 0))));
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("D", 3, 7))));
		assertTrue(segments.contains(new Segment(new Point("B", 4, 0), new Point("C", 6, 3))));
		assertTrue(segments.contains(new Segment(new Point("C", 6, 3), new Point("E", -2, 4))));
		assertTrue(segments.contains(new Segment(new Point("D", 3, 7), new Point("E", -2, 4))));
		
		//point (F) is in database, but no segments
		assertFalse(segments.contains(new Segment(new Point("A", 0, 0), new Point("F", 26, 0))));
	}

	@Test
	void square_four_interior_triangles_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("square_four_interior_triangle.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 5);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 0, 0));
		assertEquals(pd.getPoint("B"), new Point("B", 5, 0));
		assertEquals(pd.getPoint("C"), new Point("C", 5, 5));
		assertEquals(pd.getPoint("D"), new Point("D", 0, 5));
		assertEquals(pd.getPoint("E"), new Point("E", 3, 3));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 8);
		
		//spot checks
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0), new Point("D", 0, 5))));
		assertTrue(segments.contains(new Segment(new Point("B", 5, 0), new Point("A", 0, 0))));
		assertTrue(segments.contains(new Segment(new Point("E", 3, 3), new Point("C", 5, 5))));
		assertTrue(segments.contains(new Segment(new Point("B", 5, 0), new Point("E", 3, 3))));
		assertTrue(segments.contains(new Segment(new Point("D", 0, 5), new Point("E", 3, 3))));
	}
	
	@Test
	void four_point_star_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("four_point_star.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 8);
		
		//spot checks
		assertEquals(pd.getPoint("A"), new Point("A", 5, 10));
		assertEquals(pd.getPoint("C"), new Point("C", 7, 7));
		assertEquals(pd.getPoint("E"), new Point("E", 3, 3));
		assertEquals(pd.getPoint("G"), new Point("G", 10, 5));
		assertEquals(pd.getPoint("H"), new Point("H", 5, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 8);
		
		//spot checks		
		assertTrue(segments.contains(new Segment(new Point("A", 5, 10), new Point("C", 7, 7))));
		assertTrue(segments.contains(new Segment(new Point("C", 7, 7), new Point("G", 10, 5))));
		assertTrue(segments.contains(new Segment(new Point("E", 3, 3), new Point("H", 5, 0))));
		assertTrue(segments.contains(new Segment(new Point("B", 3, 7), new Point("D", 0, 5))));
		assertTrue(segments.contains(new Segment(new Point("F", 7, 3), new Point("H", 5, 0))));
	}

	@Test
	void traingle_with_three_triangles_inside_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("triangle_with_three_triangles_inside.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 10);
		
		//spot checks
		assertEquals(pd.getPoint("I"), new Point("I", 0, 0));
		assertEquals(pd.getPoint("G"), new Point("G", 3, 1));
		assertEquals(pd.getPoint("J"), new Point("J", 8, 0));
		assertEquals(pd.getPoint("F"), new Point("F", 6, 3));
		assertEquals(pd.getPoint("A"), new Point("A", 5, 7));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 18);
		
		//spot checks		
		assertTrue(segments.contains(new Segment(new Point("A", 5, 7) , new Point("C", 6, 5))));
		assertTrue(segments.contains(new Segment(new Point("C", 6, 5) , new Point("F", 6, 3))));
		assertTrue(segments.contains(new Segment(new Point("E", 5, 3) , new Point("G", 3, 1))));
		assertTrue(segments.contains(new Segment(new Point("G", 3, 1) , new Point("I", 0, 0))));
		assertTrue(segments.contains(new Segment(new Point("F", 6, 3) , new Point("H", 6, 1))));
	}
	
	@Test
	void arrow_pointing_right_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("arrow_pointing_right.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 7);
		
		//spot checks
		assertEquals(pd.getPoint("G"), new Point("G", 0, 2));
		assertEquals(pd.getPoint("A"), new Point("A", 0, 6));
		assertEquals(pd.getPoint("B"), new Point("B", 18, 6));
		assertEquals(pd.getPoint("C"), new Point("C", 18, 8));
		assertEquals(pd.getPoint("D"), new Point("D", 22, 4));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 7);
		
		//spot checks		
		assertTrue(segments.contains(new Segment(new Point("A", 0, 6) , new Point("B", 18, 6))));
		assertTrue(segments.contains(new Segment(new Point("C", 18, 8) , new Point("D", 22, 4))));
		assertTrue(segments.contains(new Segment(new Point("D", 22, 4) , new Point("E", 18, 0))));
		assertTrue(segments.contains(new Segment(new Point("E", 18, 0) , new Point("F", 18, 2))));
		assertTrue(segments.contains(new Segment(new Point("F", 18, 2) , new Point("G", 0, 2))));
	}
	
	@Test
	void grid_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("grid.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 9);
		
		//spot checks
		assertEquals(pd.getPoint("A"), new Point("A", 0, 5));
		assertEquals(pd.getPoint("C"), new Point("C", 5, 5));
		assertEquals(pd.getPoint("E"), new Point("E", 3, 3));
		assertEquals(pd.getPoint("G"), new Point("G", 0, 0));
		assertEquals(pd.getPoint("I"), new Point("I", 5, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 12);
		
		//spot checks		
		assertTrue(segments.contains(new Segment(new Point("A", 0, 5) , new Point("D", 0, 3))));
		assertTrue(segments.contains(new Segment(new Point("C", 5, 5) , new Point("F", 5, 3))));
		assertTrue(segments.contains(new Segment(new Point("H", 3, 0) , new Point("E", 3, 3))));
		assertTrue(segments.contains(new Segment(new Point("H", 3, 0) , new Point("I", 5, 0))));
		assertTrue(segments.contains(new Segment(new Point("G", 0, 0) , new Point("D", 0, 3))));
	}
	
	@Test
	void two_separate_triangles_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("two_separate_triangles.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 6);
		
		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 2, 3));
		assertEquals(pd.getPoint("B"), new Point("B", 0, 0));
		assertEquals(pd.getPoint("C"), new Point("C", 3, 0));
		assertEquals(pd.getPoint("D"), new Point("D", 6, 3));
		assertEquals(pd.getPoint("E"), new Point("E", 5, 0));
		assertEquals(pd.getPoint("F"), new Point("F", 7, 0));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 6);
		
		//spot checks (comprehensive)		
		assertTrue(segments.contains(new Segment(new Point("A", 2, 3) , new Point("B", 0, 0))));
		assertTrue(segments.contains(new Segment(new Point("A", 2, 3) , new Point("C", 3, 0))));
		assertTrue(segments.contains(new Segment(new Point("B", 0, 0) , new Point("C", 3, 0))));
		assertTrue(segments.contains(new Segment(new Point("D", 6, 3) , new Point("E", 5, 0))));
		assertTrue(segments.contains(new Segment(new Point("D", 6, 3) , new Point("F", 7, 0))));
		assertTrue(segments.contains(new Segment(new Point("E", 5, 0) , new Point("F", 7, 0))));
	}
	
	@Test
	void divided_square_test() throws ParseException, JSONException, NotInDatabaseException
	{
		FigureNode node = TestInputFacade.genericTestSet("divided_square.json");
		
		PointDatabase pd = InputFacade.toGeometryRepresentation(node).getKey();
		assertEquals(pd.size(), 4);

		//spot checks (comprehensive)
		assertEquals(pd.getPoint("A"), new Point("A", 0, 0));
		assertEquals(pd.getPoint("B"), new Point("B", 5, 0));
		assertEquals(pd.getPoint("C"), new Point("C", 5, 5));
		assertEquals(pd.getPoint("D"), new Point("D", 0, 5));
		
		Set<Segment> segments = InputFacade.toGeometryRepresentation(node).getValue();
		assertEquals(segments.size(), 5);
		
		//spot checks (comprehensive)
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0) , new Point("B", 5, 0))));
		assertTrue(segments.contains(new Segment(new Point("A", 0, 0) , new Point("D", 0, 5))));
		assertTrue(segments.contains(new Segment(new Point("B", 5, 0) , new Point("C", 5, 5))));
		assertTrue(segments.contains(new Segment(new Point("B", 5, 0) , new Point("D", 0, 5))));
		assertTrue(segments.contains(new Segment(new Point("C", 5, 5) , new Point("D", 0, 5))));
	}
	
}

