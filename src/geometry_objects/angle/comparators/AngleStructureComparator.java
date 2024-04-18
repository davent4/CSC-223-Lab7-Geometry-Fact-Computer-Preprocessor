/**
 * Write a succinct, meaningful description of the class here. You should avoid wordiness    
 * and redundancy. If necessary, additional paragraphs should be preceded by <p>,
 * the html tag for a new paragraph.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author <your name>
 * @date   <date of completion>
 */

package geometry_objects.angle.comparators;

import java.util.Comparator;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import utilities.math.analytic_geometry.GeometryUtilities;

public class AngleStructureComparator implements Comparator<Angle>
{
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;

	/**
	 * Given the figure below:
	 * 
	 *    A-------B----C-----------D
	 *     \
	 *      \
	 *       \
	 *        E
	 *         \
	 *          \
	 *           F
	 * 
	 * What we care about is the fact that angle BAE is the smallest angle (structurally)
	 * and DAF is the largest angle (structurally). 
	 * 
	 * If one angle X has both rays (segments) that are subsegments of an angle Y, then X < Y.
	 * 
	 * If only one segment of an angle is a subsegment, then no conclusion can be made.
	 * 
	 * So:
	 *     BAE < CAE
	 *     BAE < DAF
	 *     CAF < DAF

	 *     CAE inconclusive BAF
	 * 
	 * @param left -- an angle
	 * @param right -- an angle
	 * @return -- according to the algorithm above:
	 *              Integer.MAX_VALUE will refer to our error result
	 *              0 indicates an inconclusive result
	 *              -1 for less than
	 *              1 for greater than
	 */
	@Override
	public int compare(Angle thisS, Angle that)
	{
		//conditions that mean it is not comparable structurally
		Point vertex = thisS.getVertex();
		if (!vertex.equals(that.getVertex())) 	return STRUCTURALLY_INCOMPARABLE;
		if (!thisS.overlays(that)) 				return STRUCTURALLY_INCOMPARABLE;
		
		Segment thisone = thisS.getRay1();
		Segment thistwo = thisS.getRay2();
		Segment thatone = that.getRay1();
		Segment thattwo = that.getRay2();
		
		if(GeometryUtilities.between(thisone.other(vertex), vertex, thatone.other(vertex)) &&
		   GeometryUtilities.between(thistwo.other(vertex), vertex, thattwo.other(vertex))) 	return -1;
		
		if(GeometryUtilities.between(thisone.other(vertex), vertex, thattwo.other(vertex)) &&
		   GeometryUtilities.between(thistwo.other(vertex), vertex, thatone.other(vertex))) 	return -1;
		
		if(GeometryUtilities.between(thatone.other(vertex), vertex, thisone.other(vertex)) &&
		   GeometryUtilities.between(thattwo.other(vertex), vertex, thistwo.other(vertex))) 	return 1;
		
		if(GeometryUtilities.between(thatone.other(vertex), vertex, thistwo.other(vertex)) &&
		   GeometryUtilities.between(thattwo.other(vertex), vertex, thisone.other(vertex))) 	return 1;
			
		return 0;
	}
}

