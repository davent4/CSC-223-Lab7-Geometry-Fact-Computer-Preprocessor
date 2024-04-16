package utilities.eq_classes;
import java.util.Comparator;
import utilities.LinkedList;

/**
 * A class that stores equivalent vales
 * @author Jackson Tedesco, Tony Song, and Della Avent
 * @data 2/8/2024
 * @param <T>
 */

public class LinkedEquivalenceClass<T> 
{
	protected T _canonical;
	protected Comparator<T> _comparator;
	protected LinkedList<T> _rest;
	
	public LinkedEquivalenceClass(Comparator<T> comp) 
	{
		_canonical = null;
		_comparator = comp;
		_rest = new LinkedList<T>();
	}
	
	/**
	 * @return canonical
	 */
	public T canonical() 
	{
		return _canonical;
	}
	
	/**
	 * @return True if list is empty; False otherwise
	 */
	public boolean isEmpty() 
	{
		return _canonical == null && _rest.isEmpty(); 
	}
	
	/**
	 * removes everything from the LinkedList and empties _canonical
	 */
	public void clear()
	{
		_canonical = null;
		_rest = new LinkedList<T>();
	}
	
	/**
	 * removes everything that isn't the canonical 
	 */
	public void clearNonCanonical()
	{
		_rest = new LinkedList<T>();
	}
	
	/**
	 * @return size of the equivalence class
	 */
	public int size() 
	{
		if(isEmpty()) return _rest.size();
		//this won't add the extra one if canonical number is null
		
		return _rest.size() + 1;
		//the 1 accounts for the canonical number
	}
	
	/**
	 * Adds element to list
	 * @param element
	 * @return True if given element is added to list; False otherwise
	 */
	public boolean add(T element)
	{	
		//sets a canonical number
		if(isEmpty()) 
		{
			if(element == null) return false;	
			_canonical = element;
			return true;
		}
		
		//if the element is not the canonical
		if(!belongs(element)) return false;
		if(contains(element)) return false;
		_rest.addToBack(element);
		return true;
	}
	
	/**
	 * checks if the given element is in the equivalence class
	 * @param element
	 * @return True if equivalence class contains element; False otherwise
	 */
	public boolean contains(T element)
	{
		if(!belongs(element)) return false;
		if(_canonical.equals(element)) return true;
		
		return _rest.contains(element);
	}
	
	/**
	 * checks whether an element can be added, or "belongs"
	 * @param element
	 * @return True if element belongs; False otherwise 
	 */
	public boolean belongs(T element)
	{	
		if(isEmpty()) return false;
		
		if(element == null) return false;
		
		if(_comparator.compare(_canonical, element) == 0) return true;
		return false;
	}
	
	/**
	 * removes a specified element from the equivalence class
	 * @param element
	 * @return true if changed
	 */
	public boolean remove(T element)
	{
		if(element == null) return false;
		
		if(isEmpty()) return false;
		
		if(_canonical.equals(element)) return removeCanonical();
		
		return _rest.remove(element);
	}
	
	/**
	 * replaces the canonical with the first element in class
	 * @return true if _canonical != null; False otherwise
	 */
	public boolean removeCanonical()
	{
		if(isEmpty()) return false;
		
		//replaces the canonical with the first element in the LinkedList
		if(size() > 1) {
			_canonical = _rest.get(0);
			_rest.remove(_canonical);
			return true;
		}
		
		//makes the class empty
		_canonical = null;
		return true;
	}
	
	/**
	 * replaces the canonical with the specified element 
	 * @param element
	 * @return true if changed
	 */
	public boolean demoteAndSetCanonical(T element)
	{
		if(element == null) return false;
		
		if(_canonical == null) 
		{
			_canonical = element;
			return true;
		}
		
		//cannot add something if it does not belong
		if(!(belongs(element))) return false;
		
		//if the canonical already equals the element
		if(_canonical.equals(element)) return false; 
		
		//no duplicates
		if(contains(element)) _rest.remove(element);
		
		if(_canonical != null) _rest.addToBack(_canonical);
		_canonical = element;
		
		return true;
	}
	
	/**
	 * @return the list as a String 
	 */
	public String toString() {
		if(isEmpty()) return "";
		
		StringBuilder s = new StringBuilder(_canonical.toString() + ":");
		s.append(_rest.toString());
		return s.toString();
	}
}

