package geometry_objects.angle;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;

@SuppressWarnings("null")
public class AngleTest {
    public Angle makeAngle(Point p0, Point p1, Point p2, Point p3) {
        try {
            return new Angle(new Segment(p0, p1), new Segment(p2, p3));
        } catch (Exception e) {}

        return null;
    }

    @Test
    void compareToTest() {
        /*
         *
         *     (0,2)
         *      E     F
         *      |    /
         *      |   /
         *      D  /
         *      | /
         *      |/
         *      A-----B-----C   (2,0)
         *      
         *     (0,-1)
         *      I
         *      |
         *      |
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
        
        Angle a0 = null;
        Angle BAD = makeAngle(A, B, A, D);
        Angle CAD = makeAngle(A, C, A, D);
        Angle CAE = makeAngle(A, C, A, E);
        Angle BAE = makeAngle(A, B, A, E);
        Angle BAF = makeAngle(A, B, A, F);
        Angle HGI = makeAngle(G, H, G, I);

        // at least one of the angles is null
        assertThrows(NullPointerException.class, () -> {
            a0.compareTo(a0);
        });

        assertThrows(NullPointerException.class, () -> {
            a0.compareTo(BAD);
        });

        assertEquals(Integer.MAX_VALUE, BAD.compareTo(a0));

        // identical angles
        assertEquals(-1, BAD.compareTo(BAD));

        // same angle; one ray equal; other ray different
        assertEquals(-1, BAD.compareTo(CAD));
        assertEquals(1, CAD.compareTo(BAD));

        // same angle; one angle has both greater rays
        assertEquals(1, CAE.compareTo(BAE));
        assertEquals(-1, BAE.compareTo(CAE));

        // same angle; each angle has a greater ray
        assertEquals(0, CAD.compareTo(BAE));
        assertEquals(0, BAE.compareTo(CAD));

        // same angle; vertex not shared
        assertEquals(Integer.MAX_VALUE, BAD.compareTo(HGI));
        assertEquals(Integer.MAX_VALUE, HGI.compareTo(BAD));

        // different angle; vertex shared
        assertEquals(Integer.MAX_VALUE, BAD.compareTo(BAF));
        assertEquals(Integer.MAX_VALUE, BAF.compareTo(BAD));

        // different angle; vertex not shared
        assertEquals(Integer.MAX_VALUE, BAF.compareTo(HGI));
        assertEquals(Integer.MAX_VALUE, HGI.compareTo(BAF));
    }

    @Test 
    void overlaysTest() {
        /*
         *
         *     (0,2)
         *      E     F
         *      |    /
         *      |   /
         *      D  /
         *      | /
         *      |/
         *      A-----B-----C   (2,0)
         *      
         *     (0,-1)
         *      I
         *      |
         *      |
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
        
        Angle a0 = null;
        Angle BAD = makeAngle(A, B, A, D);
        Angle CAD = makeAngle(A, C, A, D);
        Angle CAE = makeAngle(A, C, A, E);
        Angle BAE = makeAngle(A, B, A, E);
        Angle BAF = makeAngle(A, B, A, F);
        Angle HGI = makeAngle(G, H, G, I);

        // at least one of the angles is null
        assertThrows(NullPointerException.class, () -> {
            a0.overlays(a0);
        });

        assertThrows(NullPointerException.class, () -> {
            a0.overlays(BAD);
        });

        assertFalse(BAD.overlays(a0));

        // identical angles
        assertTrue(BAD.overlays(BAD));

        // same angle; one ray equal; other ray different
        assertTrue(BAD.overlays(CAD));
        assertTrue(CAD.overlays(BAD));

        // same angle; one angle has both greater rays
        assertTrue(CAE.overlays(BAE));
        assertTrue(BAE.overlays(CAE));

        // same angle; each angle has a greater ray
        assertTrue(CAD.overlays(BAE));
        assertTrue(BAE.overlays(CAD));

        // same angle; vertex not shared
        assertFalse(BAD.overlays(HGI));
        assertFalse(HGI.overlays(BAD));

        // different angle; vertex shared
        assertFalse(BAD.overlays(BAF));
        assertFalse(BAF.overlays(BAD));

        // different angle; vertex not shared
        assertFalse(BAF.overlays(HGI));
        assertFalse(HGI.overlays(BAF));
    }

    @Test
    void overlayingRayTest() {
        /*
         *
         *     (0,2)
         *      E     F
         *      |    /
         *      |   /
         *      D  /
         *      | /
         *      |/
         *      A-----B-----C   (2,0)
         *      
         *     (0,-1)
         *      I
         *      |
         *      |
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
        
        Segment s0 = null;
        Segment AB = new Segment(A, B);
        Segment AD = new Segment(A, D);
        Segment AF = new Segment(A, F);
        Segment GH = new Segment(G, H);
        Segment AC = new Segment(A, C);
        Segment AE = new Segment(A, E);

        Angle a0 = null;
        Angle BAD = makeAngle(A, B, A, D);

        // at least one of the angles is null
        assertThrows(NullPointerException.class, () -> {
            a0.overlayingRay(s0);
        });

        assertThrows(NullPointerException.class, () -> {
            a0.overlayingRay(AB);
        });

        assertNull(BAD.overlayingRay(s0));

        // identical angles
        assertEquals(AB, BAD.overlayingRay(AB));
        assertEquals(AD, BAD.overlayingRay(AD));

        // different vertex
        assertNull(BAD.overlayingRay(GH));

        // same vertex; different angle
        assertNull(BAD.overlayingRay(AF));

        // same vertex; same angle; different rays
        assertEquals(AB, BAD.overlayingRay(AC));
        assertEquals(AD, BAD.overlayingRay(AE));
    }

    @Test
    void sameVertexAsTest() {
        /*
         *
         *     (0,2)
         *      E     F
         *      |    /
         *      |   /
         *      D  /
         *      | /
         *      |/
         *      A-----B-----C   (2,0)
         *      
         *     (0,-1)
         *      I
         *      |
         *      |
         *      G-----H (1,-2)
         */

        Point A = new Point(0, 0);
        Point B = new Point(1, 0);
        Point C = new Point(2, 0);
        Point D = new Point(0, 1);
        Point F = new Point(1, 2);
        Point G = new Point(0, -2);
        Point H = new Point(1, -2);
        Point I = new Point(0, -1);
        
        Angle a0 = null;
        Angle BAD = makeAngle(A, B, A, D);
        Angle CAD = makeAngle(A, C, A, D);
        Angle BAF = makeAngle(A, B, A, F);
        Angle HGI = makeAngle(G, H, G, I);

        // at least one of the angles is null
        assertThrows(NullPointerException.class, () -> {
            a0.sameVertexAs(a0);
        });

        assertThrows(NullPointerException.class, () -> {
            a0.sameVertexAs(BAD);
        });

        assertFalse(BAD.sameVertexAs(a0));

        // identical angles
        assertTrue(BAD.sameVertexAs(BAD));

        // same angle; same vertex; different rays
        assertTrue(BAD.sameVertexAs(CAD));
        assertTrue(CAD.sameVertexAs(BAD));

        // same angle; different vertex
        assertFalse(BAD.sameVertexAs(HGI));
        assertFalse(HGI.sameVertexAs(BAD));

        // different angle; same vertex;
        assertTrue(BAD.sameVertexAs(BAF));
        assertTrue(BAF.sameVertexAs(BAD));
    }
}
