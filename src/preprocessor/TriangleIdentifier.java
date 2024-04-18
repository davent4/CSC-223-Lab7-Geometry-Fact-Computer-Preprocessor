package preprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;

/**
 * class that finds triangles with a given set of segments
 *
 * @author Della Avent, Ellie Johnson, Jack Roberts
 * @date   April 19 2024
 */

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure.

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memorize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	/*
	 * adds all valid triangles to the set
	 */
	private void computeTriangles()
	{
		//makes _segments indexable
		List<Segment> segments = _segments.keySet().stream().toList();
		
		//tries to create a valid triangle from every combination of segments
		//tests every unique combination of segments
		for(int i = 0; i < segments.size(); i++)
		{
			for (int j = i + 1; j < segments.size(); j++)
			{ 
				for (int k = i + 2; k < segments.size(); k++) 
				{ 
					Segment first = segments.get(i);
					Segment second = segments.get(j);
					Segment third = segments.get(k);
					List<Segment> segList = new ArrayList<Segment>(Arrays.asList(first, second, third));
					try {
						Triangle t = new Triangle(segList);
						//if the triangle is valid, add it to the set
						_triangles.add(t);
					} catch (FactException e) {
						//if an exception is caught, then the triangle is not valid
						//simply move onto a new combination of segments
					}
				}
			}
		}
	}
}

