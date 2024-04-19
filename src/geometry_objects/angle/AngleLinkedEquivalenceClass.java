package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.eq_classes.LinkedEquivalenceClass;

/**
 * This implementation requires greater knowledge of the implementing Comparator.
 * 
 * According to our specifications for the AngleStructureComparator, we have
 * the following cases:
 *
 *    Consider Angles A and B
 *    * Integer.MAX_VALUE -- indicates that A and B are completely incomparable
 *                           STRUCTURALLY (have different measure, don't share sides, etc. )
 *    * 0 -- The result is indeterminate:
 *           A and B are structurally the same, but it is not clear one is structurally
 *           smaller (or larger) than another
 *    * 1 -- A > B structurally
 *    * -1 -- A < B structurally
 *    
 *    We want the 'smallest' angle structurally to be the canonical element of an
 *    equivalence class.
 * 
 * @author Dr C. Alvin, Ellie Johnson, Jack Roberts, Della Avent
 * @date   April 19 2024
 */
public class AngleLinkedEquivalenceClass extends LinkedEquivalenceClass<Angle>
{
	public AngleLinkedEquivalenceClass() 
	{
		super(new AngleStructureComparator());
	}
	
	/**
	 * checks whether an element can be added, or "belongs"
	 * @param element
	 * @return True if element belongs; False otherwise 
	 */
	@Override
	public boolean belongs(Angle element)
	{	
		if(isEmpty()) return false;
		
		if(element == null) return false;
		
		if(_comparator.compare(_canonical, element) == AngleStructureComparator.STRUCTURALLY_INCOMPARABLE) 
			return false;
		
		//if(_comparator.compare(_canonical, element) == 0) return false;
		
		return true;
	}
	
	/**
	 * Adds element to list
	 * @param element
	 * @return True if given element is added to list; False otherwise
	 */
	@Override
	public boolean add(Angle element)
	{	
		//sets a canonical number
		if(isEmpty()) 
		{
			if(element == null) return false;	
			_canonical = element;
			return true;
		}
		
		//if the element is not added as the canonical
		if(!belongs(element)) return false;
		if(contains(element)) return false;
		
		//if the new angle is smaller than the canonical
		if(_comparator.compare(_canonical, element) > 0)
		{
			demoteAndSetCanonical(element);
			return true;
		}
		
		_rest.addToBack(element);
		return true;
	}
}
