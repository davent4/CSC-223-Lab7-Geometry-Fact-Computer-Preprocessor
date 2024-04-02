package preprocessor.delegates;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;

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

		for(int i = 0; i < segments.length - 1; i++)
		{
			for (int x = i + 1; x < segments.length -1; x++)
			{
				Point impPoint =
						geometry_objects.delegates.intersections.IntersectionDelegate
						.segmentIntersection((Segment)segments[i], (Segment)segments[x]);
				
				//checks that impPoint is implicit or an end point
				if(impPoint != null) implicitPoints.add(impPoint);
			}
		}
		return implicitPoints;
	}
}
