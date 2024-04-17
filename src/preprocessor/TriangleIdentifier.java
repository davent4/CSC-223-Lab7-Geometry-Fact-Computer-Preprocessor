package preprocessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

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

	private void computeTriangles()
	{
		// make equivalenceClass of angles
		//		each equivalenceClass has multiple LinkedEquivalenceClass
		//		each LinkedEquivalenceClass = one equivalent angle
		AngleIdentifier angleIden = new AngleIdentifier(_segments);		
		AngleEquivalenceClasses aeq = angleIden.getAngles();
	
		// for each angle, try to mash it with two other angles
		//		idk how the fuck to do this part
		//		try each angle per equivalenceClass against all the other angles in all the other equivalenceClasses????
		//		still keeps from trying same angle against equivalent angles
		//    ok so like:
		//		grab canonical from one linkedClass
		//		grab the canonical from another
		//		try it with every angle in every other linkedEquivalenceClass
		//		then grab the second element from "another" (the second linkedList)
		//		try IT with every angle in every other linkedEquivalenceClass
		//		once you've made it thru every angle in "another", don't try it until you've moved onto second element in first linked class
		//
		Angle first = null;
		Angle second = null;
		Angle third = null;
		if( 
		// "trying it":
		// (1) if they share the same 3 points
				//or if they share same three rays???
		// (2) if they DONT share a vertex
	}
}
