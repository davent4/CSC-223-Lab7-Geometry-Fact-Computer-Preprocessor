package geometry_objects.points;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import input.components.exception.NotInDatabaseException;
 
public class TestPointDatabase {

    @Test
    public void testPutAndGetPoint() {
        PointDatabase pointDatabase = new PointDatabase();
        pointDatabase.put("A", 1.0, 2.0);
        Point pointA = pointDatabase.getPoint("A");
        assertEquals(1.0, pointA.getX(), 0.001);
        assertEquals(2.0, pointA.getY(), 0.001);
    }

    @Test
    public void testGetName() throws NotInDatabaseException {
        PointDatabase pointDatabase = new PointDatabase();
        pointDatabase.put("B", 3.0, 4.0);
        String name = pointDatabase.getName(3.0, 4.0);
        assertEquals("B", name);
    }
    
    @Test
    public void testGetPointWithName() {
        PointDatabase pointDatabase = new PointDatabase();
        pointDatabase.put("C", 7.0, 8.0);
        Point pointC = pointDatabase.getPoint("C");
        assertEquals(7.0, pointC.getX(), 0.001);
        assertEquals(8.0, pointC.getY(), 0.001);
    }

    @Test
    public void testGetPointWithCoordinates() throws NotInDatabaseException {
        PointDatabase pointDatabase = new PointDatabase();
        pointDatabase.put("D", 9.0, 10.0);
        Point pointD = pointDatabase.getPoint(9.0, 10.0);
        assertEquals("D", pointD.getName());
    }

    @Test
    public void testSize() {
        PointDatabase pointDatabase = new PointDatabase();
        assertEquals(0, pointDatabase.size());
        pointDatabase.put("E", 11.0, 12.0);
        assertEquals(1, pointDatabase.size());
    }

    @Test
    public void testPointDatabaseWithInitialPoints() throws NotInDatabaseException {
        List<Point> initialPoints = Arrays.asList(new Point("F", 13.0, 14.0), new Point("G", 15.0, 16.0));
        PointDatabase pointDatabase = new PointDatabase(initialPoints);
        assertEquals(2, pointDatabase.size());
        
        assertEquals("F", pointDatabase.getPoint("F").getName());
        assertEquals("G", pointDatabase.getPoint("G").getName());
        
        assertEquals("F", pointDatabase.getName(13.0, 14.0));
        assertEquals("G", pointDatabase.getName(15.0, 16.0));
    }
}