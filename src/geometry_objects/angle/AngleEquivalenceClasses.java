package geometry_objects.angle;


import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.EquivalenceClasses;

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
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{
	public AngleEquivalenceClasses() 
	{
		super(new AngleStructureComparator());
	}	
	
	@Override
	public boolean add(Angle element) {
		int index = indexOfClass(element);
		if(index > -1) {
			_classes.get(index).add(element);
			return false;
		}

		AngleLinkedEquivalenceClass equivalence = new AngleLinkedEquivalenceClass();
		
		if(equivalence.add(element)) {
			_classes.add(equivalence);
			return true;
		}
		
		return false;
	}
}
