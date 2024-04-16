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
import utilities.math.MathUtilities;
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
	public int compare(Angle left, Angle right)
	{
		Point vertex = left.getVertex();
		if (vertex != right.getVertex()) return STRUCTURALLY_INCOMPARABLE;
		
		//can't be on same lines if angle is not equal
		if (!MathUtilities.doubleEquals(left.getMeasure(), right.getMeasure())) return STRUCTURALLY_INCOMPARABLE;

		//compares the segments of each angle
		//TODO probably could be more efficient
		Segment leftOpposite = null;
		Segment rightOpposite = null;

		//finds if there is a same segment and sets the opposite to compare
		if(left.getRay1().equals(right.getRay1()) || left.getRay1().equals(right.getRay2()))  
		{
			leftOpposite = left.getRay2();
			//finds the right segment that is the same
			if(left.getRay1().equals(right.getRay1())) rightOpposite = right.getRay2();
			else rightOpposite = right.getRay1();
		}
		//compares the second segment of the left given angle to the right rays
		if(left.getRay2().equals(right.getRay1()) || left.getRay2().equals(right.getRay2()))  
		{
			leftOpposite = left.getRay2();
			//finds the right segment that is the same
			if(left.getRay2().equals(right.getRay2())) rightOpposite = right.getRay1();
			else rightOpposite = right.getRay2();
		}

		//returns zero since there is no similarity
		if (leftOpposite == null) return 0;
		
		//compares the distance of the opposite lines
		double rayLeft = GeometryUtilities.distance(vertex, leftOpposite.other(vertex));
		double rayRight = GeometryUtilities.distance(vertex, rightOpposite.other(vertex));

		//returns if the first angle parameter is less than the second
		//TODO is this how you compare doubles
		if(rayLeft > rayRight) return 1;
		
		return -1;
	}
}

