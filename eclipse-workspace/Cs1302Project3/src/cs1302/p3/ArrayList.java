package cs1302.p3;

import java.util.Iterator;

import cs1302.p3.List;
import cs1302.p3.Box;

public class ArrayList<T> implements List<T> {
	private Box<T>[] box;

	public ArrayList() {
		box = Box.array(0);

	}

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

	}

	public void add(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {
		Box<T>[] boxCopy = (Box<T>[]) new Box[box.length];
		int oldSize = box.length;

		for (int x = 0; x < box.length; x++) {
			boxCopy[x] = box[x];
		}

		box = Box.array(oldSize + 1);

		for (int i = 0; i < boxCopy.length; i++) {
			if (i == index) {
				box[i] = (Box<T>) elem;
				i++;
			}
			box[i] = boxCopy[i];

		}

	}

	public void clear() {
		for (int x = 0; x < box.length; x++) {
			box[x] = null;
		}

	}

	public T get(int index) throws IndexOutOfBoundsException {
		T value = (T) box[index];
		return value;
	}

	public T set(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {
		Box<T> oldElement = box[index];
		box[index] = (Box<T>) elem;
		return (T) oldElement;
	}

	public int size() {
		int size = 0;
		for (int x = 0; x < box.length; x++) {
			if (box[x] != null) {
				size++;
			}
		}

		return size;
	}

	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(T elem) throws NullPointerException {
		for (int x = 0; x < box.length; x++) {
			if (box[x].equals(elem)) {
				return true;
			}
		}
		return false;
	}

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

	public Iterator iterator() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

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

	public int indexOf(T elem) throws NullPointerException {
		for (int x = 0; x < box.length; x++) {
			if (box[x].equals(elem)) {
				return x;
			}
		}
		return -1;
	}

}
