package material.tree.binarytree;

import material.Position;
import material.tree.Tree;

/**
 * An interface for a binary tree, where each node can have zero, one, or two
 * children.
 *
 * @param <E>
 * @author A. Duarte, J. Vélez
 */
public interface BinaryTree<E> extends Tree<E>, Iterable<Position<E>> {

    /**
     * Returns the left child of a node.
     *
     * @param p the position of the parent
     * @return the position of the left child of p
     * @throws RuntimeException if p doesn't have a left child
     */
    Position<E> left(Position<E> p) throws RuntimeException;

    /**
     * Returns the right child of a node.
     *
     * @param p the position of the parent
     * @return the position of the right child of p
     * @throws RuntimeException if p doesn't have a right child
     */
    Position<E> right(Position<E> p) throws RuntimeException;

    /**
     * Returns whether a node has a left child.
     *
     * @param p the position of the parent
     * @return true if p has a left child
     */
    boolean hasLeft(Position<E> p);

    /**
     * Returns whether a node has a right child.
     *
     * @param p the position of the parent
     * @return true if p has a right child
     */
    boolean hasRight(Position<E> p);

    /**
     * Replaces the content of position p.
     *
     * @param p the position whose content will be replaced
     * @param e the new element of p
     * @return the element previously contained in position p
     */
    E replace(Position<E> p, E e);

    /**
     * Returns the sibling of a node.
     *
     * @param p the position whose sibling we need
     * @return the position of the sibling of p
     * @throws RuntimeException if p doesn't have a sibling
     */
    Position<E> sibling(Position<E> p) throws RuntimeException;


    /**
     * Inserts a left child at a given node.
     *
     * @param p the position of the parent
     * @param e the element of the new child
     * @return the position of the new left child node of p
     * @throws RuntimeException if p already has a left child
     */
    Position<E> insertLeft(Position<E> p, E e) throws RuntimeException;

    /**
     * Inserts a right child at a given node.
     *
     * @param p the position of the parent
     * @param e the element of the new child
     * @return the position of the new right child
     * @throws RuntimeException if p already has a right child
     */
    Position<E> insertRight(Position<E> p, E e) throws RuntimeException;

    /**
     * Removes a node with zero or one child.
     *
     * @param p the position of the node to be removed
     * @return the element removed
     * @throws RuntimeException if the position has two children
     */
    E remove(Position<E> p) throws RuntimeException;

    /**
     * Swap the locations of two positions in the tree.
     *
     * @param p1 the position that will take the relative location of p2
     * @param p2 the position that will take the relative location of p1
     */
    void swap(Position<E> p1, Position<E> p2);

    /**
     * Removes the subtree of v from this tree a creates a new tree with it.
     *
     * @param v new root node
     * @return The new tree.
     */
    BinaryTree<E> subTree(Position<E> v);

    /**
     * Attach tree t as left children of node p. t will become empty since all
     * its positions are taken to the tree of p.
     *
     * @param p    the parent position of subtree t
     * @param tree the source tree. All its positions will be attached as a left subtree of p
     * @throws RuntimeException if p already has a left children, if p belongs to source tree,
     *                          or if both trees are from different implementations
     */
    void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException;

    /**
     * Attach tree t as right children of node p. t will become an empty tree since all
     * its positions are taken to the tree of p.
     *
     * @param p    the parent position of subtree t
     * @param tree the source tree t. All its positions will be attached as a right subtree of p
     * @throws RuntimeException if p already has a right children, if p belongs to source tree,
     *                          or if both trees are from different implementations
     */
    void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException;

    /**
     * Checks whether the binary tree is complete or not (every node has two children except leaves)
     *
     * @return true if the tree is complete, false otherwise
     */
    boolean isComplete();


}
