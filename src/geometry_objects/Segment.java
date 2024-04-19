package geometry_objects;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import geometry_objects.delegates.LineDelegate;
import geometry_objects.delegates.SegmentDelegate;
import geometry_objects.delegates.intersections.IntersectionDelegate;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

/**
 * an class representing and undirected segment (a finite section of an infinite line)
 * 
 * @author Dr. C Alvin, Della Avent, Ellie Johnson
 * @date 4/10/24
 */

public class Segment extends GeometricObject
{
	protected Point _point1;
	protected Point _point2;

	protected double _length;
	protected double _slope;

	public Point getPoint1() { return _point1; }
	public Point getPoint2() { return _point2; }
	public double length() { return _length; }
	public double slope()
	{
		try { return GeometryUtilities.slope(_point1, _point2); }
		catch(ArithmeticException ae) { return Double.POSITIVE_INFINITY; }
	}

	public Segment(Segment in) { this(in._point1, in._point2); }
	public Segment(Point p1, Point p2)
	{
		_point1 = p1;
		_point2 = p2;
	}

	/*
	 * @param that -- a segment (as a segment: finite)
	 * @return the midpoint of this segment (finite)
	 */
	public Point segmentIntersection(Segment that) {  return IntersectionDelegate.segmentIntersection(this, that); }

	/*
	 * @param pt -- a point
	 * @return true / false if this segment (finite) contains the point
	 */
	public boolean pointLiesOn(Point pt) { return this.pointLiesOnSegment(pt); }

	/*
	 * @param pt -- a point
	 * @return true / false if this segment (finite) contains the point
	 */
	public boolean pointLiesOnSegment(Point pt) { return SegmentDelegate.pointLiesOnSegment(this, pt); }

	/*
	 * @param pt -- a point
	 * @return true if the point is on the segment (EXcluding endpoints); finite examination only
	 */
	public boolean pointLiesBetweenEndpoints(Point pt) { return SegmentDelegate.pointLiesBetweenEndpoints(this, pt); }

	/**
	 * Does this segment contain a subsegment?
	 *   Example:
	 *                A-------B-------C------D
	 *
	 *         Subsegments of AD are: AB, AC, AD, BC, BD, CD
	 * 
	 * @param candidate
	 * @return true if this segment contains candidate as subsegment.
	 */
	public boolean HasSubSegment(Segment candidate)
	{
		//checks whether the endpoints of candidate are on this segment
		return (SegmentDelegate.pointLiesOnSegment(this, candidate.getPoint1())) 
				&& (SegmentDelegate.pointLiesOnSegment(this, candidate.getPoint2()));	
	}

	/**
	 * Determines if this segment and that segment share an endpoint

	 * @param s -- a segment

	 * @return the shared endpoint
	 *         returns null if no endpoints are the same segment
	 */
	public Point sharedVertex(Segment that)
	{
		if (this.equals(that)) return null;

		if (_point1.equals(that._point1)) return _point1;
		if (_point1.equals(that._point2)) return _point1;
		if (_point2.equals(that._point1)) return _point2;
		if (_point2.equals(that._point2)) return _point2;
		return null;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;

		if (!(obj instanceof Segment)) return false;
		Segment that = (Segment)obj;

		return this.has(that.getPoint1()) && this.has(that.getPoint2());
	}

	/*
	 * @param that -- another segment
	 * @return true / false if the two lines (infinite) are collinear
	 */
	public boolean isCollinearWith(Segment that) { return LineDelegate.areCollinear(this, that); }

	/*
	 * @param pt -- a point
	 * @return true if @pt is one of the endpoints of this segment
	 */
	public boolean has(Point pt) { return _point1.equals(pt) || _point2.equals(pt); }

	/*
	 * @return true if this segment is horizontal (by analysis of both endpoints having same y-coordinate)
	 */
	public boolean isHorizontal() { return MathUtilities.doubleEquals(_point1.getY(), _point2.getY()); }

	/*
	 * @return true if this segment is vertical (by analysis of both endpoints having same x-coordinate)
	 */
	public boolean isVertical() { return MathUtilities.doubleEquals(_point1.getX(), _point2.getX()); }

	/*
	 * @param pt -- one of the endpoints of this segment
	 * @return the 'other' endpoint of the segment (null if neither endpoint is given)
	 */
	public Point other(Point p)
	{
		if (p.equals(_point1)) return _point2;
		if (p.equals(_point2)) return _point1;

		return null;
	}

	@Override
	public int hashCode()
	{
		return _point1.hashCode() +_point2.hashCode();
	}

	/*
	 * @param that -- a segment
	 * @return true if the segments coincide, but do not overlap:
	 *
	 * True case:
	 *                    this                  that
	 *             ----------------           ===========
	 *
	 * True case:
	 *                    this    that
	 *             |----------|==========|     
	 * 
	 * Note: the segment MAY share an endpoint
	 * coincide means to be on the same infinite line
	 */

	public boolean coincideWithoutOverlap(Segment that)
	{
		//check collinearity
		if (!(isCollinearWith(that))) return false;

		//check for parallel
		if(!(isCollinearWith(new Segment(getPoint1(), that.getPoint1())))) return false;

		Point sharedVertex = sharedVertex(that);

		//if there is no shared vertex the endpoints shouldn't be on the segment
		if (sharedVertex == null) 
		{
			return !SegmentDelegate.pointLiesBetweenEndpoints(this, that.getPoint1()) &&
				   !SegmentDelegate.pointLiesBetweenEndpoints(this, that.getPoint2()) &&
				   !SegmentDelegate.pointLiesBetweenEndpoints(that, this.getPoint1()) &&
				   !SegmentDelegate.pointLiesBetweenEndpoints(that, this.getPoint2());
		}
		
		//checks if nonendpoint is on the line
		if (SegmentDelegate.pointLiesBetweenEndpoints(this, that.other(sharedVertex)) || 
			SegmentDelegate.pointLiesBetweenEndpoints(that, other(sharedVertex))) return false;
		
		return true;
	}

	/**
	 *   Example:
	 *                             Q *
	 *
	 *                A-------B-------C------D     E
	 *
	 *      * Z
	 *
	 *  Given:
	 *	    Segment(A, D) and points {A, B, C, D, E, Q, Z},
	 *      this method will return the set {A, B, C, D} in this order
	 *      since it is lexicographically sorted.
	 *
	 *      Points Q, Z, and E are NOT on the segment.
	 *
	 * @return the sorted subset of Points that lie on this segment (ordered lexicographically)
	 */
	public SortedSet<Point> collectOrderedPointsOnSegment(Set<Point> points)
	{
		SortedSet<Point> pointsOn = new TreeSet<Point>();

		for(Point p : points)
		{
			if(SegmentDelegate.pointLiesOnSegment(this, p))	pointsOn.add(p);
		}

		return pointsOn;
	} 

	/**
	 * finds if the other end point of segment two is on the line of segment one
	 * we know they share an end point based on the method call
	 * @param one segment
	 * @param second segment
	 * @return true if segment two is on segment one
	 */
	public static boolean overlaysAsRay(Segment one, Segment two)
	{
		if(one.equals(two)) return true;

		// defines the vertex where the points intersect
		Point vertex = one.sharedVertex(two);
		if(vertex == null) return false;
		
		if(!LineDelegate.areCollinear(one, two)) return false;
		
		// returns whether or not the other end point is on one of the lines
		return GeometryUtilities.between(two.other(vertex), vertex, one.other(vertex)) ||
				GeometryUtilities.between(one.other(vertex), vertex, two.other(vertex));
	}
	
	/**
	 * returns object as a string
	 */
	public String toString() {
		return "[" + _point1.getName() + ", " + _point2.getName() + "]";
	}
}