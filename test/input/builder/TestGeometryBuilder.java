package input.builder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import components.ComponentNode;
import components.FigureNode;
import input.components.exception.NotInDatabaseException;
import input.components.exception.ParseException;
import input.components.parser.JSONParser;
import input.components.visitor.UnparseVisitor;



class TestGeometryBuilder {

	public static ComponentNode runFigureParseTest(String filename) throws ParseException, JSONException, NotInDatabaseException
	{
		JSONParser parser = new JSONParser(new GeometryBuilder());

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		return parser.parse(figureStr);
	}

	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser(new GeometryBuilder());

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test() throws ParseException, JSONException, NotInDatabaseException
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("single_triangle.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}

	@Test
	void collinear_line_segments_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}

	@Test
	void crossing_symmetric_triangle_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void fully_connected_irregular_polygon_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}

	@Test
	void square_four_interior_triangles_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("square_four_interior_triangle.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void four_point_star_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("four_point_star.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}

	@Test
	void traingle_with_three_triangles_inside_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("triangle_with_three_triangles_inside.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void arrow_pointing_right_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("arrow_pointing_right.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void grid_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("grid.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void two_separate_triangles_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("two_separate_triangles.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
	
	@Test
	void divided_square_test() throws ParseException, JSONException, NotInDatabaseException
	{
		ComponentNode node = TestGeometryBuilder.runFigureParseTest("divided_square.json");

		assertTrue(node instanceof FigureNode);

		StringBuilder sb = new StringBuilder();
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = new AbstractMap.SimpleEntry<StringBuilder, Integer>(sb, 0);
		
		node.accept(new UnparseVisitor(), pair);
		System.out.println(sb.toString());
	}
}