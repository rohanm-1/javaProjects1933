//Written by Rohan Modalavalasa, modal005 and Aiden Math, math5600
public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private int size;
    private boolean isSorted;
    public LinkedList() {
        // initialize instance variables
        this.head = null;
        this.size = 0;
        this.isSorted = true;
    }
    // *personal helper method* : this method is used after a required method is performed that alters the Linked List
    // to re-check whether the LinkedList is sorted
    public void sortChecker(){
        isSorted = true;
        Node<T> temp = head;
        while(temp != null){
            //if the pointer of the next node is null that means its the last node, meaning that we have traversed
            // through the entire linked list
            if(temp.getNext() == null)
                break;
            // if not, compare the data of the first and next node and check if they are in sorted order
            if (temp.getData().compareTo(temp.getNext().getData()) > 0) {
                isSorted = false;
                break;
            }
            // update pointer
            temp = temp.getNext();
        }
    }
    public boolean add(T element){
        if (element==null){
            return false;
        }
        Node<T> addNode = new Node(element);
        int i=0;
        if (head==null){//Checks if the size is zero, adds to the front if it is.
            head= addNode;
            //System.out.println("Adding to the front of the list:" + data) ;
            this.isSorted=true;
            size++;//increments the size of the list so we can keep better track
        } else{
            Node<T> currentNode= head;
            while(currentNode.getNext()!=null){//loops through the other nodes to find the end.
                currentNode=currentNode.getNext();
                //System.out.println("loops through the the list: "+ currentNode + " " +i);
                i++;
            }
            currentNode.setNext(addNode);
            size++;//increments the size of the list so we can keep better track
            //System.out.println("Added node to the end of the list:" + data);
        }
        // check for sorting
        sortChecker();
        // in the end, return true because we were still able to add the element
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null) {
            return false;
        }
        Node<T> addNode = new Node<>(element);
        if (index< 0 || index >size) {//Ensuring index is in bounds
            return false;
        }
        if (index == 0) {//Adds to the beginning of the list if input index is 0
            addNode.setNext(head);
            head = addNode;
            size++;
            sortChecker();
            return true;
        }
        Node<T> currentNode = head;
        int i =0;
        while (i < index-1 ) {//Loops through the list, finds the matching index-1, breaks once at desired index
            currentNode = currentNode.getNext();
            i++;
        }
        addNode.setNext(currentNode.getNext());//once index has been found with while loop,
        //it points the new node to the data of the next node at i+1,
        //and assigns the previous node(i-1) to point to the added node's data at i
        currentNode.setNext(addNode);
        size++;
        // check for sorting
        sortChecker();
        return true;
    }

    @Override
    public void clear() {
        head=null;
        size=0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if(index<0){
            return null;
        }
        if(index>size-1){
            return null;
        }

        if (head==null){
            return null;
        }
        Node<T> currentNode = head;
        int i = 0;
        while (i < index) {//Loops through the list, finds the matching index, breaks once desried index has been reached
            currentNode = currentNode.getNext();
            i++;
        }
        return currentNode.getData();
    }


    @Override
    public int indexOf(T element) {
        Node<T> currentNode= head;
        if(currentNode==null){
            return -1;
        }
        // we are unable to increase the efficiency as much as a sorted ArrayList because LinkedLists
        // are unable to perform binary searches with a significantly shorter runtime.

        // However, if we know that the LinkedList is sorted we can end the loop early if the element we are looking for
        // is not found and the elements within our list are starting to become greater, we can return -1 immediately to
        // increase efficiency
        if (isSorted) {
            int index = 0;
            while(currentNode != null){ // loops through LinkedList
                if(currentNode.getData().compareTo(element)>0)
                    return -1;
                else if(currentNode.getData().equals(element)){
                    return index;
                }
                currentNode = currentNode.getNext();
                index++; // increment index until desired element found
            }
        }
        else{
            int index=0;
            while(currentNode !=null) {//loops through the other nodes to find the end.
                if (currentNode.getData().equals(element)) {
                    return index;
                }
                currentNode = currentNode.getNext();
                index++;//continues to increment until desired element found
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void sort() {//Used the idea of selection sort, could also be done using merge sort
        if (size <= 1) {//doesnt need to sort
            return;
        }
        if(isSorted){
            return;
        }
        else{
            Node<T> current = head;
            while (current != null) {//first loop that iterates through list
                Node<T> minimum=current;
                Node<T> nextNode =current.getNext();
                while (nextNode != null) {//inner loop that compares min val to every other node value
                    if (nextNode.getData().compareTo(minimum.getData()) <0){//if the node is smaller than the minimum, it gets replaced
                        minimum=nextNode;
                    }
                    nextNode = nextNode.getNext();
                }
                T temp = current.getData();//uses temp pointer to not lose info
                current.setData(minimum.getData());
                minimum.setData(temp);//swaps data between minimum and temp

                current = current.getNext();//move to the next node at the ned of outer loop
            }
            isSorted = true;
        }
    }
    @Override
    public T remove(int index) {
        if (head==null){
            return null;
        }
        if(index>size || index<0){
            return null;
        }
        if (index == 0) {//if index = zero, set the head to null
            T remove = head.getData();
            head = head.getNext();
            size--;
            sortChecker();
            return remove;
        }
        Node<T> previous = null;
        Node<T> currentNode = head;
        int i = 0;
        while (currentNode != null && i < index) {//loops through until node at index is found
            previous = currentNode;//keeps track of the last node used
            currentNode = currentNode.getNext();
            i++;
        }
        if(currentNode!=null){
            T remove= currentNode.getData();
            previous.setNext(currentNode.getNext());//sets i-1 to point next to i+1
            size--;
            sortChecker();
            return remove;
        }
        return null;
    }

    @Override
    public void removeDuplicates() {
        if (head ==null) {
            return;//if the list is empty or contains only one element, there are no duplicates to remove
        }
        if (size <=1) {
            return;
        }
        else {
            Node<T> currentNode = head;
            Node<T> duplicateChecker;
            for (int i= 0; i <size; i++) {
                duplicateChecker = currentNode.getNext();//helper object used to compare against
                for (int j =i + 1; j< size; j++) {
                    if (duplicateChecker != null && currentNode.getData().equals(duplicateChecker.getData())) {//checks if the nodes in the list are duplicates
                        this.remove(j);//calls the remove method
                        j--;//moves linkedlist index back one
                        isSorted = false;
                        duplicateChecker = duplicateChecker.getNext();//make sure we account for everything in the linked list
                    }else {
                        duplicateChecker = duplicateChecker.getNext();//doesnt remove if not a duplicate, contiues loop
                    }
                }
                currentNode = currentNode.getNext();
            }
            // check for sorting
            sortChecker();
        }
    }

    @Override
    public void reverse() {//used help from this youtube tutorial as its a leetcode question ive done before (https://www.youtube.com/watch?v=G0_I-ZF0S38)
        if (head == null) {
            return;//dont need to reverse if list is empty or has only one node
        }
        if(head.getNext() == null){
            return;
        }
        Node<T> current = head;//Using the two pointer approach to keep track of node info
        Node<T> previous=null;
        Node<T> nextNode;
        while (current!= null) {
            nextNode = current.getNext();//keeps track of this node so we dont lose its pointer
            current.setNext(previous);//points the current node next variable towards the "last" node
            previous= current;//Points previous to current in order to swap
            current = nextNode; //move current to next node, incrementing the loop
        }
        head = previous;//last part of the reverse, set the previous node to the head
        // check for sorting
        sortChecker();
    }

    @Override
    public void exclusiveOr(List<T> otherList) {
        if (otherList == null) {//checks if other null
            return;
        }
        if (this==null){//checks if the current object list is null
            return;
        }
        if (size==0||otherList.size()==0){
            return;
        }

        //Sorting and removing duplicates from both lists in order to make it easier to compare
        this.removeDuplicates();
        LinkedList<T> other = (LinkedList<T>) otherList;
        other.sort();
        other.removeDuplicates();
        LinkedList<T> result = new LinkedList<>();
        Node<T> currentNode = this.head;
        Node<T> currentCompare = other.head;//similarly to the remove method, we use a compare node.We compare againt the current node of the class list to a compareNode of the otherList
        while (currentNode != null && currentCompare!=null) {//loops through both lists until one of them is null

            //Need three cases for the loop, one to check if the currentNode is greater otherNode one to check less than, both adding to result, and then an else case to increment the loop
            if (currentNode.getData().compareTo(currentCompare.getData())<0) {//iff the current Node is less than the other, we add to result because they are different
                result.add(currentNode.getData());
                currentNode = currentNode.getNext();//increments to the next node
            }
            else if (currentNode.getData().compareTo(currentCompare.getData())>0) {//if the OtherNode is bigger at the current index, add other node's data to result.
                result.add(currentCompare.getData());
                currentCompare= currentCompare.getNext();
            }
            else{// we iterate through the nodes by one each and then keep checking
                currentNode = currentNode.getNext();
                currentCompare = currentCompare.getNext();
            }
        }

        //If one of the lists are bigger than the other, we need to add all the elements of the different list to the result
        for(int i=0; i<other.size; i++){//loops through and adds all the elements from otger list
            if(currentCompare!=null){
                result.add(currentCompare.getData());
                currentCompare=currentCompare.getNext();
            }
            else{break;}
        }
        for(int i=0; i<this.size; i++){//adds elements from the class list
            if(currentNode!=null){
                result.add(currentNode.getData());
                currentNode= currentNode.getNext();
            }
            else{break;}
        }
        this.size = result.size;//changes to appropriate size
        this.head = result.head;//points the old list to the new updated result list
        result.sort();
        isSorted=true;
    }

    @Override
    public T getMin() {//sorts and returns head
        if (head==null){
            return null;
        }
        if(isSorted){
            // if it is sorted, we are increasing the efficiency by not having to sort the list beforehand and can
            // simply traverse the list to find the maximum value
        }
        else{
            this.sort();
        }
        return this.head.getData();
    }

    @Override
    public T getMax() {//sorts and returns the "tail"
        if (head == null) {
            return null;
        }
        Node<T> currentNode = head;
        if (isSorted) {
            // if it is sorted, we are increasing the efficiency by not having to sort the list beforehand and can
            // simply traverse the list to find the maximum value
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
        } else {
            // if not, we have to sort and then traverse to return the max node
            this.sort();
            while (currentNode.getNext() != null) {
                currentNode = currentNode.getNext();
            }
            return currentNode.getData();
        }
        return currentNode.getData();
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}
