package preprocessor.delegates;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.exception.NotInDatabaseException;

/**
 * given the points and segments of a geometry figure, 
 * finds any points created by intersections between segements
 * 
 * @author Ellie Johnson, Della Avent
 * @date 4/10/24
 */

public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 * 
	 */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments) 
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();

		Object[] segments = givenSegments.toArray();

		for(int i = 0; i < segments.length; i++)
		{
			for (int x = i + 1; x < segments.length; x++)
			{
				Point impPoint =
						geometry_objects.delegates.intersections.IntersectionDelegate
						.segmentIntersection((Segment)segments[i], (Segment)segments[x]);
				
				if(impPoint != null) 
				{
					//puts into givenPoints because it is a PointNamingFactory
					//and will therefore give the implied point a name
					givenPoints.put(impPoint);
					
					try {
						//adds the named implicit point to the set of only implicit points
						implicitPoints.add(givenPoints.getPoint(impPoint));
					} catch (NotInDatabaseException e) {

					} 
				}
			}
		}
		return implicitPoints;
	}
}
