package material.tree.iterators;

import material.Position;
import material.tree.binarytree.BinaryTree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Inorder iteartor for binary trees.
 *
 * @param <T>
 * @author jvelez, JD. Quintana
 */
public class InorderBinaryTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeStack = new LinkedList<>();
    private final BinaryTree<T> tree;

    public InorderBinaryTreeIterator(BinaryTree<T> tree) {
        this.tree = tree;
        if (!tree.isEmpty())
            goToLastInLeft(tree.root());
    }


    public InorderBinaryTreeIterator(BinaryTree<T> tree, Position<T> node) {
        this.tree = tree;
        goToLastInLeft(node);
    }

    private void goToLastInLeft(Position<T> node) {
        nodeStack.addFirst(node);
        while (tree.hasLeft(node)) {
            node = tree.left(node);
            nodeStack.addFirst(node);
        }
    }

    @Override
    public boolean hasNext() {
        return (!nodeStack.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a breath-first search
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeStack.removeFirst();
        if (tree.hasRight(aux)) {
            goToLastInLeft(tree.right(aux));
        }

        return aux;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
