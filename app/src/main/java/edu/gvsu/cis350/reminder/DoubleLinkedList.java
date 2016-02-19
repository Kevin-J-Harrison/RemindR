package edu.gvsu.cis350.reminder;

/**
 * Created by Andrew on 2/19/2016.
 */


import java.io.Serializable;

/**
 * Creates the Doubly Linked List for the Rental Store GUI, and implements serializable.
 *
 * @author alexvansteel, kevinharrison
 *
 * @param <E>
 */
public class DoubleLinkedList<E> implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Pointer to the first Node of the list. */
    private DNode<E> top;
    /** Pointer to the last Node of the list. */
    private DNode<E> tail;

    /** Int for the size of the list. */
    private int size;
    /** Boolean to check if an Node was removed. */
    private boolean removed = false;

    public DoubleLinkedList() {
        super();
        top = null;
        tail = null;
        size = 0;
    }

    /**
     * Counts the number of nodes in the list.
     *
     * @return the number of nodes in the list.
     */
    public int size() {
        int count = 0;
        DNode<E> temp = top;
        while (temp != null) {

            count++;
            temp = temp.getNext();

        }
        return count;
    }

    /**
     * Clears the list of all nodes.
     */
    public void clear() {
        if (top == null)
            return;

        while (top != tail) {
            top.setPrevious(null);
            top = top.getNext();
        }
        if (top == tail)
            top = tail = null;

        size = 0;
    }

    /**
     * Adds a node to the end of the list.
     *
     * @param data
     *            data to be entered into the node.
     */
    public void add(E data) {
        if (top == null) {
            top = tail = new DNode<E>(data, null, null);
            size++;
            return;
        }

        tail = new DNode<E>(data, tail, null);
        tail.getPrevious().setNext(tail);
        size++;
    }

    /**
     * Adds a node to the top of the list.
     *
     * @param data
     *            data to be entered into the node.
     */
    public void addFirst(E data) {
        if (top == null) {
            top = tail = new DNode<E>(data, null, null);
            size++;
            return;
        }

        if (top == tail) {
            top = new DNode<E>(data, null, tail);
            size++;
        }

        top = new DNode<E>(data, null, top);
        size++;

    }

    /**
     * Removes node based on the parameter.
     *
     * @param index
     *            the node to be removed.
     */
    public E remove(int index) {
        if (size == 0)
            return null;

        if (size == 1) {
            DNode<E> temp = top;
            top = tail = null;
            size = 0;
            removed = true;
            return temp.getData();
        }

        if (index == size) {
            DNode<E> temp = tail;
            tail = tail.getPrevious();
            size--;
            removed = true;
            return temp.getData();
        }

        if (index == 0) {
            DNode<E> temp = top;
            top = top.getNext();
            top.setPrevious(null);
            size--;
            return temp.getData();
        }

        DNode<E> temp = top.getNext();
        for (int i = 1; i <= size; i++) {
            if (i == index) {
                temp.getPrevious().setNext(temp.getNext());
                temp.getNext().setPrevious(temp.getPrevious());
                size--;
                removed = true;
                return temp.getData();
            } else {
                temp = temp.getNext();
            }
        }
        return null;
    }

    /**
     * Locates node based on entered index.
     *
     * @param index
     *            node to be found.
     * @return the data of the found node.
     */
    public E get(int index) {
        if (size == 0)
            return null;

        if (index == 0)
            return top.getData();

        if (index == size)
            return tail.getData();

        DNode<E> temp = top.getNext();
        for (int i = 1; i <= size; i++) {
            if (i == index) {
                return temp.getData();
            } else {
                temp = temp.getNext();
            }
        }
        return null;
    }

    /**
     * Checks if a node has been removed or not.
     *
     * @return status of removal of a node.
     */
    public boolean returnAll() {
        return removed;
    }

    /**
     * Checks if the data is found in the nodes
     *
     * @param data
     *            data to be found
     * @return int showing if the data was found or not found.
     */
    public int find(E data) {
        DNode<E> temp = top;
        for (int i = 0; i <= size; i++) {
            if (temp.getData().equals(data))
                return i;
            temp = temp.getNext();
        }
        return -1;
    }
}



