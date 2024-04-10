package preprocessor.delegates;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.components.exception.NotInDatabaseException;

public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 * @throws NotInDatabaseException 
	 * 
	 */
	//TODO must we make implicitPoints a PointDatabase (to name the points) or leave as a Set<Point>?
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments) 
	//throw implicit point into givenPoints, then put the SAME OBJECT into implicit
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
					givenPoints.put(impPoint);
					try {
						implicitPoints.add(givenPoints.getPoint(impPoint));
					} catch (NotInDatabaseException e) {

					} 
				}
				
			}
		}
		return implicitPoints;
	}
}
