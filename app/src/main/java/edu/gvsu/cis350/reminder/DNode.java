package edu.gvsu.cis350.reminder;


import java.io.Serializable;

/**

 * Nodes used to create the Doubly Linked List. It implements Serializable.

 *

 * @author alexvansteel, kevinharrison

 *

 * @param <E>

 */

public class DNode<E> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The information in the node. */

    public E data;

    /** The pointer to the next node. */

    public DNode<E> next;

    /** The pointer to the previous node. */

    public DNode<E> previous;

    /**

     * Constructor of the node.

     *

     * @param data

     * points to the data

     * @param previous

     * points to the previous node

     * @param next

     * points to the next node

     */

    public DNode(E data, DNode<E> previous, DNode<E> next) {

        super();

        this.data = data;

        this.next = next;

        this.previous = previous;

    }

    public DNode() {

        super();

    }

    public E getData() {

        return data;

    }

    public void setData(E data) {

        this.data = data;

    }

    public DNode<E> getNext() {

        return next;

    }

    public void setNext(DNode<E> next) {

        this.next = next;

    }

    public DNode<E> getPrevious() {

        return previous;

    }

    public void setPrevious(DNode<E> previous) {

        this.previous = previous;

    }

}