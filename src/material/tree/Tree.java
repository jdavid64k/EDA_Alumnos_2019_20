package material.tree;

import material.Position;

/**
 * An interface for a tree where nodes can have an arbitrary number of children.
 *
 * @param <E> the type of the elements stored in the tree
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public interface Tree<E> extends Iterable<Position<E>> {

    /**
     * Checks the number of nodes in the tree.
     *
     * @return the number of nodes in the tree
     */
    int size();

    /**
     * Checks if the tree is empty.
     *
     * @return TRUE if the tree is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Checks the root of the tree.
     *
     * @return the root of the tree
     * @throws RuntimeException if the tree is empty
     */
    Position<E> root() throws RuntimeException;

    /**
     * Consults the parent of a given node.
     *
     * @param v the position of the node whose parent is being consulted
     * @return the position of the parent of v
     * @throws RuntimeException if the node does not have a parent (i.e., it is the root)
     */
    Position<E> parent(Position<E> v) throws RuntimeException;

    /**
     * Creates an iterable collection of the children of a given node.
     *
     * @param v the position of the node whose children are checked
     * @return an iterable collection with the children of the consulted node
     */
    Iterable<? extends Position<E>> children(Position<E> v);

    /**
     * Checks if a given node is internal.
     *
     * @param v the position of the node to check
     * @return TRUE is the node is internal, FALSE otherwise
     */
    boolean isInternal(Position<E> v);

    /**
     * Checks if a given node is external.
     *
     * @param v the position of the node to check
     * @return TRUE if the node is a leaf, FALSE otherwise
     * @throws RuntimeException if the position is not valid
     */
    boolean isLeaf(Position<E> v) throws RuntimeException;

    /**
     * Checks if a given node is the root of the tree.
     *
     * @param v the position of the node to check
     * @return TRUE if the node is the root, FALSE otherwise
     */
    boolean isRoot(Position<E> v);

    /**
     * Sets the root of the tree
     *
     * @param e the element to be used as a root
     * @return the position of the root added
     * @throws RuntimeException if the tree already has a root
     */
    Position<E> addRoot(E e) throws RuntimeException;
}
