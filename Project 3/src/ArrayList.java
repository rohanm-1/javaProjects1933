//Written by Rohan Modalavalasa, modal005@umn.edu and Aiden Math, math5600
public class ArrayList<T extends Comparable<T>> implements List<T> {
    private boolean isSorted;
    private T[] arr;
    private int size;
    public ArrayList(){
        //initialize
        this.size=0;
        this.arr = (T[]) new Comparable[2];
        this.isSorted=true;
    }
    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (size == arr.length) {
            //creates a new array with double the size and copy elements
            T[] copyList = (T[]) new Comparable[size * 2];
            for (int j = 0; j < arr.length; j++) {
                copyList[j] = arr[j];
            }
            arr = copyList;// Point the original array to the copy
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = element;// Add the element to an empty spot
                size++;
                // check for sorting
                isSorted = true;
                for(int j = 0; j < size-1; j++){
                    if(arr[j].compareTo(arr[j+1]) > 0){
                        isSorted = false;
                        break;
                    }
                }
                return true;
            }
        }
        return false; // Return false if no empty spot is found
    }


    @Override
    public boolean add(int index, T element) {
        if(element == null){
            return false;
        }
        if (size == arr.length) {//if the array is full, create a new one and copy it over
            T[] copyList = (T[]) new Comparable[size *2];
            for (int j = 0; j < arr.length; j++) {
                copyList[j] = arr[j];
            }
            arr = copyList;
        }
        if (index < 0 || index >= arr.length) {//bounds checking
            return false;
        }

        if (index< size) {
            for (int i = size -1; i >= index; i--) {//shifts all stuff to the right of the index
                arr[i + 1] = arr[i];
            }
        }
        arr[index] = element;
        size++;
        // assume isSorted is true, and if we find an element that breaks the sort, set it to false
        isSorted = true;
        for(int j = 0; j < size-1; j++){
            if(arr[j].compareTo(arr[j+1]) > 0){
                isSorted = false;
                break;
            }
        }
        return true;
    }


    @Override
    public void clear() {//creates new array with default values and points to it
        T[] copyList = (T[]) new Comparable[2];
        arr=copyList;
        this.size=0;
        isSorted = true;
    }
    @Override
    public T get(int index) {
        if (index<0||index>=arr.length){//checks if index in bounds
            return null;
        }
        return arr[index];
    }

    @Override
    public int indexOf(T element) {
        if (element == null) // checks if element is null
            return -1;
        if (isSorted) {
            // If the list is sorted, use binary search for efficiency
            int low = 0;
            int high = size - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int compare = arr[mid].compareTo(element);
                if (compare == 0) {
                    // we found the element, but now we need to find the first occurrence of that element
                    // this is because mid can land on the element but there could be elements before which
                    // are the same as arr[mid]
                    while (mid > 0 && arr[mid - 1].compareTo(element) == 0) {
                        mid--;
                    }
                    return mid;
                } else if (compare < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        } else {
            // if the list is not sorted, we are forced to use a linear search
            for (int i = 0; i < size; i++) {
                if (arr[i].compareTo(element) == 0) {
                    return i;
                }
            }
        }
        // element was not found
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (size==0){
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {
        // account for if the method is sorted to begin with
        if(isSorted)
            return;
        else{
            // we can implement a simple sorting algorithm (bubble sort)
            for(int i=0; i<size;i++){
                for(int j=0; j< size - i-1 ;j++){
                    T temp= arr[j];
                    if((arr[j]).compareTo(arr[j + 1])>0){
                        arr[j]=arr[j+1];
                        arr[j+1]=temp;

                    }
                }
            }
            isSorted=true;
        }
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= arr.length)
            return null;
        else{
            //temp variable to return after the value is removed
            T temp = arr[index];
            // shifts everything to the right of index one step to the left
            for (int i = index; i < size-1; i++) {
                arr[i] = arr[i+1];
            }
            //reset the last element to null
            arr[size-1] = null;
            // decrement size before iterating through the ArrayList
            size--;
            // iterate through the ArrayList to check if sorted
            isSorted = true;
            for(int i = 0; i < size-1; i++){
                if(arr[i].compareTo(arr[i+1]) > 0)
                    isSorted = false;
            }
            return temp;
        }
    }

    @Override
    public void removeDuplicates() {
        if (size <= 1)
            return; // no duplicates if size is <= 1

        for (int i = 0; i < size-1; i++) {
            //check if the given element is a dupe
            for (int j=i+1; j < size; j++) {
                if (arr[i].compareTo(arr[j]) == 0) {
                    this.remove(j);
                    j--;
                }
            }
        }
        // iterate through the ArrayList to check if sorted
        isSorted = true;
        for(int i = 0; i < size-1; i++){
            if(arr[i].compareTo(arr[i+1]) > 0)
                isSorted = false;
        }
    }

    @Override
    public void reverse() {
        int start = 0;
        int end = size-1;
        for(int i = 0; i < size/2; i++){
            T temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
        // iterate through the ArrayList to check if sorted
        isSorted = true;
        for(int i = 0; i < size-1; i++){
            if(arr[i].compareTo(arr[i+1]) > 0)
                isSorted = false;
        }
    }

    @Override
    public void exclusiveOr(List<T> otherList) {
        if(otherList == null) //account for null type
            return;
        //cast other to type ArrayList and sort & removeDuplicates of both ArrayLists
        ArrayList<T> other = (ArrayList<T>) otherList;
        this.sort();
        this.removeDuplicates();
        other.sort();
        other.removeDuplicates();
        //iterate through the Arraylists. If we find the same element in both, remove it from both
        int i = 0;
        while (i < this.size) {
            boolean removed = false;
            for (int j = 0; j < other.size(); j++) {
                if (this.arr[i].compareTo(other.arr[j]) == 0) {
                    this.remove(i);
                    other.remove(j);
                    removed = true;
                    break; // break out of the inner loop after removing the element
                }
            }
            // If an element was removed, don't increment i
            if (!removed) {
                i++;
            }
        }
        // let the loop iterate through both sizes. We will break the loop
        // if the index exceeds the size of the other Arraylist
        for (T element : other.arr) {
            this.add(element);
        }
        this.sort();
        this.removeDuplicates();
    }

    @Override
    public T getMin() {
        if(size == 0)
            return null;
        // if we know the ArrayList is sorted, we can simply return the first element
        if(isSorted)
            return arr[0];
        // if not we must traverse the array to find the minimum element
        else {
            T min = arr[0];
            for (int i = 0; i < size; i++) {
                if ((arr[i].compareTo(min)) < 0)
                    min = arr[i];
            }
            return min;
        }
    }

    @Override
    public T getMax() {
        if(size == 0)
            return null;
        // if we know the ArrayList is sorted, we can simply return the last element
        if(isSorted)
            return arr[size-1];
        // if not we must traverse the array to find the minimum element
        else {
            T max = arr[0];
            for (int i = 0; i < size; i++) {
                if (arr[i].compareTo(max) > 0)
                    max = arr[i];
            }
            return max;
        }
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}