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

	/**
	 * private method to create all the possible angles in a figure
	 * adds all angles into an equivalence class
	 */
	private void computeAngles()
	{
		Set<Segment> givenSegments = _segments.keySet();
		
		//compares every segment and creates an angle if there is a shared
		//end point. Angles are added to the Equivalence class
		for (Segment ray1 : givenSegments)
		{
			for (Segment ray2 : _segments.keySet())
			{
				//makes an angle but can catch the potential exception
				try {
					Angle angle = new Angle(ray1, ray2);
					_angles.add(angle);
				} catch (FactException e) {	}
			}
		}
	}
}
