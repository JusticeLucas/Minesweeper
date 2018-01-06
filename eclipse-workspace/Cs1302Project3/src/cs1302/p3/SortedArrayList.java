package cs1302.p3;

import java.util.Iterator;
import java.lang.Comparable;
import cs1302.p3.*;

public class SortedArrayList<T extends Comparable<T>> implements List<T>{
	private Box<T>[] box;	
	
	public SortedArrayList(){
		box = Box.array(0);
	}
		
	@Override
	public void add(T elem) throws NullPointerException {
		
		Box<T>[] boxCopy = (Box<T>[]) new Box[box.length];
		int oldSize = box.length;

		for (int x = 0; x < box.length; x++) {
			boxCopy[x] = box[x];
		}

		box = Box.array(oldSize + 1);

		for (int i = 0; i < boxCopy.length; i++) {
			box[i] = boxCopy[i];
		}

		box[box.length - 1] = (Box<T>) elem;
		
		sort(box);
		
	}
	/**
	 * Swaps current value with the smaller value within the box object
	 * @param box the box object that is being sorted
	 * @param x	  larger value being swapped
	 * @param y   smaller value being swapped
	 */
	private void swap(Box<T>[] box, int x, int y) {
        if (x != y) {
            Box<T> temp = box[x];
            box[x] = box[y];
            box[y] = temp;
        }
    }
	
	/**
	 * Sorts the box object by smallest value to largest value when called
	 * 
	 * @param x the box object
	 */
    private void sort(Box<T>[] x) {
        for (int i = 0; i < x.length - 1; i++) {
           
            int smallValue = i;
            for (int j = i + 1; j < x.length; j++) {
                if (x[j].get().compareTo(x[smallValue].get())<=0) {
                	smallValue = j;
                }
            }

            swap(x, i, smallValue); 
        }
    }

    @Override
	public void add(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {
		add(elem);
	}
    
    @Override
	public void clear() {
		for (int x = 0; x < box.length; x++) {
			box[x] = null;
		}

	}
    
    @Override
	public T get(int index) throws IndexOutOfBoundsException {
		T value = (T) box[index];
		return value;
	}
    
    @Override
	public T set(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {
		Box<T> oldElement = box[index];
		box[index] = (Box<T>) elem;
		return (T) oldElement;
	}
    
    @Override
	public int size() {
		int size = 0;
		for (int x = 0; x < box.length; x++) {
			if (box[x] != null) {
				size++;
			}
		}

		return size;
	}
    
    @Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		} else {
			return false;
		}
	}
    
    @Override
	public boolean contains(T elem) throws NullPointerException {
		for (int x = 0; x < box.length; x++) {
			if (box[x].equals(elem)) {
				return true;
			}
		}
		return false;
	}
    
    @Override
	public boolean equals(Object List) {
		if (List instanceof List && ((List) List).size() == this.size()) {
			for (int x = 0; x < box.length; x++) {

				if (((List) List).get(x) != box[x]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
    
    @Override
	public Iterator iterator() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
	public boolean remove(T elem) throws NullPointerException {
		if(contains(elem)) {
			int index = indexOf(elem);		
			Box<T>[] boxCopy = (Box<T>[]) new Box[box.length];
			int oldSize = box.length;

			for (int x = 0; x < box.length; x++) {
				boxCopy[x] = box[x];
			}//for loop

			box = Box.array(oldSize - 1);
			int j = 0;
			for (int i = 0; i < box.length; i++) {
			
				if(i == index) {
					j++;
				}
				box[i] = boxCopy[j];
				j++;
			}//for loop
			return true;
		}//if statement
		return false;
	}
    
    @Override
	public int indexOf(T elem) throws NullPointerException {
		for (int x = 0; x < box.length; x++) {
			if (box[x].equals(elem)) {
				return x;
			}
		}
		return -1;
	}
	
}
