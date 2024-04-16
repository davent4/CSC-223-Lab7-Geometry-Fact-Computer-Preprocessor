package utilities.eq_classes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A list that store many equivalence classes
 * @author Jackson Tedesco, Tony Song, and Della Avent
 * @data 2/6/2024
 * @param <T>
 */
public class EquivalenceClasses<T> {
	protected Comparator<T> _comparator;
	protected List<LinkedEquivalenceClass<T>> _classes;

	public EquivalenceClasses(Comparator<T> comp) {
		_comparator = comp;
		_classes = new ArrayList<>();
	}

	/**
	 * Adds LinkedEquivalenceClass with the canonical of element.
	 * If it already exist add element to that LinkedEquivalenceClass 
	 * @param element: canonical of added LinkedEquivalenceClass
	 * @return true if LinkedEquivalenceClass did not exist; False otherwise
	 */
	public boolean add(T element) {
		int index = indexOfClass(element);
		if(index > -1) {
			_classes.get(index).add(element);
			return false;
		}

		LinkedEquivalenceClass<T> equivalence = new LinkedEquivalenceClass<T>(_comparator);
		
		if(equivalence.add(element)) {//null test. False if element is null
			_classes.add(equivalence);
			return true;
		}
		
		return false;
	}

	/**
	 * @param target
	 * @return true if target is contained; false otherwise
	 */
	public boolean contains(T target) {
		int index = indexOfClass(target);
		if(index > -1) {
			return _classes.get(index).contains(target);
		}

		return false;
	}

	/**
	 * @return sum of elements in each equivalence class
	 */
	public int size() {
		int sum = 0;
		for(int i= 0; i < numClasses(); i++) {
			sum = sum + _classes.get(i).size();
		}

		return sum;
	}

	/**
	 * @return number of classes
	 */
	public int numClasses() {
		return _classes.size();
	}

	/**
	 * @param element
	 * @return index of class; if class is not return -1 
	 */
	protected int indexOfClass(T element) {	
		if(element == null) return -1;
		
		for(int i = 0; i < _classes.size(); i++) {
			if(_classes.get(i).belongs(element)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * @return string version of EquivilanceClasses
	 */
	public String toString() {
		if(_classes.size() < 1) return "";

		StringBuilder s = new StringBuilder();
		for(int i = 0; i < numClasses(); i++) {
			s.append(_classes.get(i).toString() + "\n");
		}

		return s.toString();
	}
}
