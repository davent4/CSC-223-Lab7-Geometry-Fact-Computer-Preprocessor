package preprocessor;

import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

/**
 * class that finds angles with a given set of segments
 *
 * @author Ellie Johnson, Della Avent, Jack Roberts
 * @date   April 19 2024
 */

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; 
	 * memorize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles()
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}

	
	private void computeAngles()
	{
		Set<Segment> givenSegments = _segments.keySet();
		for (Segment ray1 : givenSegments)
		{
			for (Segment ray2 : _segments.keySet())
			{
				try {
					Angle angle = new Angle(ray1, ray2);
					_angles.add(angle);
				} catch (FactException e) {	}
			}
		}
	}
}
