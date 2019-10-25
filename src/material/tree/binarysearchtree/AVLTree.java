package material.tree.binarysearchtree;

import material.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * AVLTree class - implements an AVL Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
public class AVLTree<E> implements BinarySearchTree<E> {

    //We need this class to store the height of each BTNode
    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T> {

        private int height;
        private T element;
        private Position<AVLInfo<T>> pos;

        AVLInfo(T element) {
            this.element = element;
            this.pos = null;
            this.height = 1;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<AVLInfo<T>> getTreePosition() {
            return this.pos;
        }


        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable<T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }

        @Override
        public String toString() {
            return this.getElement().toString();
        }
    }


    private class AVLTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<AVLInfo<T>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<AVLInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<AVLInfo<E>> bst;
    private ReestructurableBinaryTree<AVLInfo<E>> resBT;


    public AVLTree() {
        this(new DefaultComparator<>());
    }

    /**
     * Creates a BinarySearchTree with the given comparator.
     *
     * @param c the comparator used to sort the nodes in the tree
     */
    public AVLTree(Comparator<E> c) {
        Comparator<AVLInfo<E>> avlComparator = (o1, o2) -> c.compare(o1.getElement(), o2.getElement());
        bst = new LinkedBinarySearchTree<>(avlComparator);
        resBT = new ReestructurableBinaryTree<>();
        bst.binTree = resBT;
    }


    @Override
    public Position<E> find(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
        Position<AVLInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
        List<Position<E>> aux = new ArrayList<>();
        for (Position<AVLInfo<E>> n : bst.findAll(searchedValue)) {
            aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }


    @Override
    public int size() {
        return bst.size();
    }


    /**
     * Returns whether a node has balance factor between -1 and 1.
     */
    private boolean isBalanced(Position<AVLInfo<E>> p) {

        AVLInfo<E> right = bst.binTree.right(p).getElement();
        AVLInfo<E> left = bst.binTree.left(p).getElement();

        final int leftHeight = (left == null) ? 0 : left.getHeight();
        final int rightHeight = (right == null) ? 0 : right.getHeight();

        final int bf = leftHeight - rightHeight;
        return ((-1 <= bf) && (bf <= 1));
    }

    /**
     * Return a child of p with height no smaller than that of the other child.
     */
    private Position<AVLInfo<E>> tallerChild(Position<AVLInfo<E>> p) {

        Position<AVLInfo<E>> right = bst.binTree.right(p);
        Position<AVLInfo<E>> left = bst.binTree.left(p);

        final int leftHeight = (left.getElement() == null) ? 0 : left.getElement().getHeight();
        final int rightHeight = (right.getElement() == null) ? 0 : right.getElement().getHeight();

        if (leftHeight > rightHeight) {
            return left;
        } else if (leftHeight < rightHeight) {
            return right;
        }

        // equal height children - break tie using parent's type
        if (bst.binTree.isRoot(p)) {
            return left;
        }

        if (p == bst.binTree.left(bst.binTree.parent(p))) {
            return left;
        } else {
            return right;
        }
    }

    private void calculateHeight(Position<AVLInfo<E>> p) {
        Position<AVLInfo<E>> left = bst.binTree.left(p);
        Position<AVLInfo<E>> right = bst.binTree.right(p);

        final int leftHeight = (left.getElement() == null) ? 0 : left.getElement().getHeight();
        final int rightHeight = (right.getElement() == null) ? 0 : right.getElement().getHeight();

        p.getElement().setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    /**
     * Rebalance method called by insert and remove. Traverses the path from p
     * to the root. For each node encountered, we recompute its height and
     * perform a trinode restructuring if it's unbalanced.
     */
    private void rebalance(Position<AVLInfo<E>> zPos) {
        if (bst.binTree.isInternal(zPos)) {
            calculateHeight(zPos);
        }
        while (!bst.binTree.isRoot(zPos)) { // traverse up the tree towards the
            // root
            zPos = bst.binTree.parent(zPos);
            calculateHeight(zPos);
            if (!isBalanced(zPos)) {
                // perform a trinode restructuring at zPos's tallest grandchild
                Position<AVLInfo<E>> xPos = tallerChild(tallerChild(zPos));
                zPos = this.resBT.restructure(xPos, bst);
                calculateHeight(bst.binTree.left(zPos));
                calculateHeight(bst.binTree.right(zPos));
                calculateHeight(zPos);
            }
        }
    }

    /**
     * Inserts an item into the dictionary and returns the newly created entry.
     */
    @Override
    public Position<E> insert(E e) {
        AVLInfo<E> aux = new AVLInfo<>(e);
        Position<AVLInfo<E>> internalNode = bst.insert(aux);
        aux.setTreePosition(internalNode);
        rebalance(internalNode);
        return aux;
    }

    /**
     * Removes and returns an entry from the dictionary.
     */
    @Override
    public E remove(Position<E> pos) throws RuntimeException {
        AVLInfo<E> aux = checkPosition(pos);

        E toReturn = pos.getElement(); // entry to be returned
        Position<AVLInfo<E>> remPos = bst.getLeafToRemove(aux.getTreePosition());

        Position<AVLInfo<E>> actionPos = bst.binTree.sibling(remPos);
        bst.removeLeaf(remPos);
        rebalance(actionPos); // rebalance the tree
        return toReturn;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<AVLInfo<E>>> bstIt = bst.iterator();
        AVLTreeIterator<E> it = new AVLTreeIterator<E>(bstIt);
        return it;
    }

    /**
     * If v is a good tree node, cast to TreePosition, else throw exception
     */
    private AVLInfo<E> checkPosition(Position<E> p) throws RuntimeException {
        if (p == null) {
            throw new RuntimeException("The position of the AVL node is null");
        } else if (!(p instanceof AVLInfo)) {
            throw new RuntimeException("The position of the AVL node is not AVL");
        } else {
            AVLInfo<E> aux = (AVLInfo<E>) p;
            return aux;
        }
    }


    public Iterable<Position<E>> findRange(E minValue, E maxValue) throws RuntimeException {
        //TODO: Practica 5 Ejercicio 1
        throw new RuntimeException("Not yet implemented.");
    }

    public Position<E> first() throws RuntimeException {
        //TODO: Practica 5 Ejercicio 2
        throw new RuntimeException("Not yet implemented.");
    }

    public Position<E> last() throws RuntimeException {
        //TODO: Practica 5 Ejercicio 2
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Position<E>> successors(Position<E> pos) {
        //TODO: Practica 5 Ejercicio 2
        throw new RuntimeException("Not yet implemented.");

    }

    public Iterable<Position<E>> predecessors(Position<E> pos) {
        //TODO: Practica 5 Ejercicio 2
        throw new RuntimeException("Not yet implemented.");
    }
}

