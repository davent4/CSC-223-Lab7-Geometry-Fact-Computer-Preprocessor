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
		//check slope equality
		if (!(MathUtilities.doubleEquals(_slope, that.slope()))) return false;
		
		//check for shared endpoint
		if (_point1.equals(that.getPoint2()) ||	
			_point2.equals(that.getPoint1()))	return true;
		
		//check for no overlap
		if (_point1.getX() > that.getPoint2().getX() ||	
			_point2.getX() < that.getPoint1().getX() )	return true; 
		
		if (MathUtilities.doubleEquals(_slope, Double.POSITIVE_INFINITY))
		{
			//check for no overlap when line is vertical
			if (_point1.getY() > that.getPoint2().getY() ||	
				_point2.getY() < that.getPoint1().getY() )	return true;
		}
		
        return false;
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

		//if it's not vertical segment
		if(!(MathUtilities.doubleEquals(_slope, Double.POSITIVE_INFINITY)))
		{
			for (Point p : points) 
			{
				Segment s = new Segment(_point1, p);
				
				/*
				 * if the x value of the point is between the endpoints AND if 
				 * the slope of a segment created from the given point and one 
				 * endpoint matches the slope of this segment, then the point belongs
				 */
				if (Point.LexicographicOrdering(p, _point1) >= 0 &&
					Point.LexicographicOrdering(p, _point2) <= 0 &&
					MathUtilities.doubleEquals(_slope, s.slope())) 
				{
					pointsOn.add(p);
				}
				
//				if (p.getX() >= _point1.getX() Point.LexicographicOrdering(p, _point1) >= 0 &&&&
//					p.getX() <= _point2.getX() &&
//					MathUtilities.doubleEquals(_slope, s.slope())) 
//				{
//					pointsOn.add(p);
//				}

			}
		} 
		//if it's a vertical segment
		else 
		{
			for (Point p : points) 
			{
				if (p.getY() > _point1.getY() &&
					p.getY() < _point2.getY() ) pointsOn.add(p);
			}
		}

		//are they currently ordered? no
		return pointsOn;
	}
}