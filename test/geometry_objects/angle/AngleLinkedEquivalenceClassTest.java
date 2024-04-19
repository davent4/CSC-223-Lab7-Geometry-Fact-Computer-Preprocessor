package geometry_objects.angle;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;

public class AngleLinkedEquivalenceClassTest {
    public Angle makeAngle(Point p0, Point p1, Point p2, Point p3) {
        try {
            return new Angle(new Segment(p0, p1), new Segment(p2, p3));
        } catch (Exception e) {}

        return null;
    }
    
    @Test
    void belongsTest() {
        AngleLinkedEquivalenceClass alec = new AngleLinkedEquivalenceClass();

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

        // test null
        assertFalse(alec.belongs(a0));

        // test while alec empty
        assertFalse(alec.belongs(BAD));

        // test while alec is not empty
        alec.add(BAD);

        assertTrue(alec.belongs(BAD));

        // same angle; one ray equal; other ray different
        assertTrue(alec.belongs(CAD));

        // same angle; both rays longer
        assertTrue(alec.belongs(CAE));

        // same angle; both rays shorter
        assertTrue(alec.belongs(PAQ));

        // same angle; one ray longer; one ray shorter
        assertTrue(alec.belongs(BAE));

        // same angle; vertex not shared
        assertFalse(alec.belongs(HGI));

        // different angle; vertex shared
        assertFalse(alec.belongs(BAF));

        // different angle; vertex not shared
        assertFalse(alec.belongs(HGZ));
    }

    @Test
    void addTest() {
        AngleLinkedEquivalenceClass alec = new AngleLinkedEquivalenceClass();
        
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
            alec.add(angle);
        }

        assertEquals(PAQ, alec.canonical());

        assertTrue(alec.contains(BAD));
        assertTrue(alec.contains(CAD));
        assertTrue(alec.contains(CAE));
        assertTrue(alec.contains(BAE));

        assertFalse(alec.contains(a0));
        assertFalse(alec.contains(BAF));
        assertFalse(alec.contains(HGI));
        assertFalse(alec.contains(HGZ));
    }
}
