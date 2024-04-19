package geometry_objects.angle;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;

/**
 * class that stores angles as two segments with a shared endpoint without overlap
 *
 * @author Dr C. Alvin, Ellie Johnson, Jack Roberts, Della Avent
 * @date   April 19 2024
 */

public class Angle implements Comparable<Angle>
{
	protected Segment _ray1;
	protected Segment _ray2;
	public Segment getRay1() { return _ray1; }
	public Segment getRay2() { return _ray2; }
	
	protected Point _ray1Endpoint;
	protected Point _vertex;
	protected Point _ray2Endpoint;
	
	//added to get access to endpoints
	public Point getRayEndpoint1() { return _ray1Endpoint; }
	public Point getRayEndpoint2() { return _ray2Endpoint; }
	
	protected double _measure; 
	public double getMeasure() { return _measure; }

	public Point getVertex() { return _vertex; }
	
	public Angle(Segment ray1, Segment ray2)  throws FactException
	{
		_vertex = ray1.sharedVertex(ray2);
		
		if (_vertex == null) throw new FactException("Shared endpoint not found.");
		
		initAngle(_vertex, ray1, ray2);
	}
	
	/**
	 * Common initialization routine for angles
	 * @param a -- A point defining the angle.
	 * @param b --  A point defining the angle. This is the vertex point.
	 * @param c --  A point defining the angle.
	 */
	private void initAngle(Point vertex, Segment r1, Segment r2) throws FactException
	{
		Point other1 = r1.other(vertex);
		Point other2 = r2.other(vertex);
		
		if (vertex.equals(other1) || vertex.equals(other2) || other1.equals(other2))
			throw new FactException("Angle constructed with redundant vertices.");

		_ray1Endpoint = other1;
		_vertex = vertex;
		_ray2Endpoint = other2;

		_ray1 = r1;
		_ray2 = r2;

		_measure = Math.toDegrees(findAngle(_ray1Endpoint, _vertex, _ray2Endpoint));

		if (_measure <= 0) throw new FactException("Measure of " + this.toString() + " is ZERO");
	}
	
	public Segment other(Segment ray)
	{
		if (ray.equals(getRay1())) return getRay2();
		if (ray.equals(getRay2())) return getRay1();
		return null;
	}

	/**
	 * Find the measure of the angle (in radians) specified by the three points.
	 * Uses Law of Cosines to compute angle.
	 * 
	 * @param a -- A point defining the angle.
	 * @param b --  A point defining the angle. This is the vertex of the angle
	 * @param c -- A point defining the angle.
	 * @return The measure of the angle (in radians) specified by the three points.
	 */
	public static double findAngle(Point a, Point b, Point c)
	{
		double v1x = a.getX() - b.getX();
		double v1y = a.getY() - b.getY();
		double v2x = c.getX() - b.getX();
		double v2y = c.getY() - b.getY();
		double dotProd = v1x * v2x + v1y * v2y;
		double cosAngle = dotProd / (Point.distance(a, b) * Point.distance(b, c));

		// Avoid minor calculation issues and retarget the given value to specific angles. 
		// 0 or 180 degrees
		if (MathUtilities.doubleEquals(Math.abs(cosAngle), 1) ||
			MathUtilities.doubleEquals(Math.abs(cosAngle), -1))
		{
			cosAngle = cosAngle < 0 ? -1 : 1;
		}

		// 90 degrees
		if (MathUtilities.doubleEquals(cosAngle, 0)) cosAngle = 0;

		return Math.acos(cosAngle);
	}

	/**
	 * An angle is foremost distinct from an another angle based on their measures.
	 * 
	 * Secondarily, an angle is smaller than another angle if the constituent rays
	 * are shorter than another.
	 * 
	 *          C
	 *         /
	 *        /
	 *       B
	 *      /
	 *     /
	 *    A------X------Y
	 *    
	 *    BAX = XAB
	 *    BAX < CAX
	 *    CAX > BAX
	 *    
	 *    CAX = BAY since this implies that 
	 */
	@Override
	public int compareTo(Angle that)
	{
		return new AngleStructureComparator().compare(this, that);
	}
	
    /**
	 * @param angle -- a angle
	 * @return true / false whether the angle overlays one of the rays/is equivalent
	 */
    public boolean overlays(Angle that)
    {
    	if (that == null) return false;
		
		// Both rays of Angle that must overlay with a ray from this Angle.
		// Cannot overlay with the same ray from this Angle because that would
		// require Angle that to have an angle of 0 degrees, which is not allowed.
    	return overlayingRay(that._ray1) != null && overlayingRay(that._ray2) != null;
    }
	
    /**
	 * @param ray -- a ray
	 * @return the one of this angle's rays that overlaps with given Segment that
	 */
    public Segment overlayingRay(Segment that)
    {
    	// Must share a vertex (checked by Segment.overlaysAsRay())

    	// Individual overlaying
    	if (Segment.overlaysAsRay(_ray1, that)) return _ray1;
    	if (Segment.overlaysAsRay(_ray2, that)) return _ray2;

    	// No overlaying segment
    	return null;
    }
	
	/**
	 * Returns whether this angle and a given angle share the same vertext
	 * @param that
	 * @return		true if this angle's and that angle's vertex are equal; else false
	 */
	public boolean sameVertexAs(Angle that) {
		if (this._vertex == null || that == null) return false;
		return this._vertex.equals(that._vertex);
	}

	@Override
	public String toString()
	{
		return "Angle( m" + _ray1Endpoint.getName() +
				            _vertex.getName() +
				            _ray2Endpoint.getName() +
				            " = " + String.format("%1$.3f", _measure) + ")";
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Angle)) return false;

		if(this == obj) return true;

		Angle angle = (Angle) obj;
		if (!sameVertexAs(angle)) return false;
		
		if(getRayEndpoint1().equals(angle.getRayEndpoint1()) &&
				getRayEndpoint2().equals(angle.getRayEndpoint2())) return true;
		if(getRayEndpoint1().equals(angle.getRayEndpoint2()) &&
				getRayEndpoint2().equals(angle.getRayEndpoint1())) return true;
		return false;
	}
}

