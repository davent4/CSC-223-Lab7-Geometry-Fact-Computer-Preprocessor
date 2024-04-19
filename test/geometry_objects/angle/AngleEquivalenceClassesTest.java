package geometry_objects.angle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;

public class AngleEquivalenceClassesTest {
    public Angle makeAngle(Point p0, Point p1, Point p2, Point p3) {
        try {
            return new Angle(new Segment(p0, p1), new Segment(p2, p3));
        } catch (Exception e) {}

        return null;
    }
    
    @Test
    void addTest() {
        AngleEquivalenceClasses aec = new AngleEquivalenceClasses();

        /*
         *
         *     (0,2)
         *      E     F
         *      |    /
         *      D   /
         *      |  /
         *      P /
         *      |/
         *      A--Q---B-----C   (2,0)
         *      
         *     (0,-1)
         *      I  Z
         *      | /
         *      |/
         *      G-----H (1,-2)
         */

        Point A = new Point(0, 0);
        Point B = new Point(1, 0);
        Point C = new Point(2, 0);
        Point D = new Point(0, 1);
        Point E = new Point(0, 2);
        Point F = new Point(1, 2);
        Point G = new Point(0, -2);
        Point H = new Point(1, -2);
        Point I = new Point(0, -1);

        Point P = new Point(0.5, 0);
        Point Q = new Point(0, 0.5);
        Point Z = new Point(0.5, -1);

        Angle a0 = null;
        Angle BAD = makeAngle(A, B, A, D);
        Angle CAD = makeAngle(A, C, A, D);
        Angle CAE = makeAngle(A, C, A, E);
        Angle BAE = makeAngle(A, B, A, E);
        Angle BAF = makeAngle(A, B, A, F);
        Angle HGI = makeAngle(G, H, G, I);

        Angle PAQ = makeAngle(A, P, A, Q);
        Angle HGZ = makeAngle(G, H, G, Z);
 
        ArrayList<Angle> angles = new ArrayList<>();
 
        angles.add(a0);
        angles.add(BAD);
        angles.add(CAD);
        angles.add(CAE);
        angles.add(BAE);
        angles.add(BAF);
        angles.add(HGI);
        angles.add(PAQ);
        angles.add(HGZ);
 
        for (Angle angle : angles) {
            aec.add(angle);
        }

        assertEquals(4, aec.numClasses());
        assertEquals(8, aec.size());

        for (Angle angle : angles) {
            if (angle != null) {
                assertTrue(aec.contains(angle));
            }
        }
    }
}
