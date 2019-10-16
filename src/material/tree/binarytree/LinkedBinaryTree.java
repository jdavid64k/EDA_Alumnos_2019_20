package material.tree.binarytree;

import material.Position;
import material.tree.iterators.InorderBinaryTreeIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @param <E>
 * @author A. Duarte, J. Vélez
 * @see BinaryTree
 */
public class LinkedBinaryTree<E> implements BinaryTree<E> {


    protected class BTNode<T> implements Position<T> {

        private T element;
        private BTNode<T> left, right, parent;

        /**
         * Main constructor.
         *
         * @param element element stored in this node
         * @param parent  parent of this node
         * @param left    left child of this node
         * @param right   right child of this node
         */
        public BTNode(T element, BTNode<T> parent, BTNode<T> left, BTNode<T> right) {
            setElement(element);
            setParent(parent);
            setLeft(left);
            setRight(right);
        }

        /**
         * Returns the element stored at this node.
         *
         * @return the element stored at this node
         */
        @Override
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored at this node.
         *
         * @param o the element to be stored
         */
        public final void setElement(T o) {
            element = o;
        }

        /**
         * Returns the left child of this node.
         *
         * @return left child of this node
         */
        public final BTNode<T> getLeft() {
            return left;
        }

        /**
         * Sets the left child of this node.
         *
         * @param v the new left child of this node
         */
        public final void setLeft(BTNode<T> v) {
            left = v;
        }

        /**
         * Returns the right child of this node.
         *
         * @return the right child of this node
         */
        public final BTNode<T> getRight() {
            return right;
        }

        /**
         * Sets the right child of this node.
         *
         * @param v the new right child of this node
         */
        public final void setRight(BTNode<T> v) {
            right = v;
        }

        /**
         * Returns the parent of this position.
         *
         * @return the parent of this position
         */
        public final BTNode<T> getParent() {
            return parent;
        }

        /**
         * Sets the parent of this position
         *
         * @param v the new parent of this position
         */
        public final void setParent(BTNode<T> v) {
            parent = v;
        }
    }

    private BTNode<E> root;

    /**
     * Creates an empty binary tree.
     */
    public LinkedBinaryTree() {
        root = null;
    }

    @Override
    public int size() {
        int size = 0;
        for (Position<E> p : this) {
            size++;
        }
        return size;
    }


    @Override
    public boolean isEmpty() {
        return (root == null);
    }


    @Override
    public boolean isInternal(Position<E> v) {
        checkPosition(v);
        return (hasLeft(v) || hasRight(v));
    }


    @Override
    public boolean isLeaf(Position<E> p) {
        return !isInternal(p);
    }


    @Override
    public boolean isRoot(Position<E> p) {
        checkPosition(p);
        return (p == root());
    }


    @Override
    public boolean hasLeft(Position<E> p) {
        BTNode<E> node = checkPosition(p);
        return (node.getLeft() != null);
    }


    @Override
    public boolean hasRight(Position<E> p) {
        BTNode<E> node = checkPosition(p);
        return (node.getRight() != null);
    }


    @Override
    public Position<E> root() throws RuntimeException {
        if (root == null) {
            throw new RuntimeException("The tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> left(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        Position<E> leftPos = node.getLeft();
        if (leftPos == null) {
            throw new RuntimeException("No left child");
        }
        return leftPos;
    }


    @Override
    public Position<E> right(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        Position<E> rightPos = node.getRight();
        if (rightPos == null) {
            throw new RuntimeException("No right child");
        }
        return rightPos;
    }


    @Override
    public Position<E> parent(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        Position<E> parentPos = node.getParent();
        if (parentPos == null) {
            throw new RuntimeException("No parent");
        }
        return parentPos;
    }


    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) {
        List<Position<E>> children = new ArrayList<>();
        if (hasLeft(p)) {
            children.add(left(p));
        }
        if (hasRight(p)) {
            children.add(right(p));
        }
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new InorderBinaryTreeIterator<>(this);
    }


    @Override
    public E replace(Position<E> p, E e) {
        BTNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        BTNode<E> parentPos = node.getParent();
        if (parentPos != null) {
            BTNode<E> sibPos;
            BTNode<E> leftPos = parentPos.getLeft();
            if (leftPos == node) {
                sibPos = parentPos.getRight();
            } else {
                sibPos = parentPos.getLeft();
            }
            if (sibPos != null) {
                return sibPos;
            }
        }
        throw new RuntimeException("No sibling");
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if (!isEmpty()) {
            throw new RuntimeException("Tree already has a root");
        }
        root = new BTNode<>(e, null, null, null);
        return root;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        Position<E> leftPos = node.getLeft();
        if (leftPos != null) {
            throw new RuntimeException("Node already has a left child");
        }
        BTNode<E> newNode = new BTNode<>(e, node, null, null);
        node.setLeft(newNode);
        return newNode;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        Position<E> rightPos = node.getRight();
        if (rightPos != null) {
            throw new RuntimeException("Node already has a right child");
        }
        BTNode<E> newNode = new BTNode<>(e, node, null, null);
        node.setRight(newNode);
        return newNode;
    }


    @Override
    public E remove(Position<E> p) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        BTNode<E> leftPos = node.getLeft();
        BTNode<E> rightPos = node.getRight();
        if (leftPos != null && rightPos != null) {
            throw new RuntimeException("Cannot remove node with two children");
        }
        //the only child of v, if any, null otherwise
        BTNode<E> child = leftPos != null ? leftPos : rightPos;

        if (node == root) { // v is the root
            if (child != null) {
                child.setParent(null);
            }
            root = child;
        } else { // v is not the root
            BTNode<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
            if (child != null) {
                child.setParent(parent);
            }
        }
        return p.getElement();
    }


    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BTNode<E> node1 = checkPosition(p1);
        BTNode<E> node2 = checkPosition(p2);

        BTNode<E> copyNode1 = new BTNode<>(node1.element, node1.parent, node1.left, node1.right);

        node1.parent = node2.parent == node1 ? node2 : node2.parent;
        node1.left = node2.left == node1 ? node2 : node2.left;
        node1.right = node2.right == node1 ? node2 : node2.right;

        node2.parent = copyNode1.parent == node2 ? node1 : copyNode1.parent;
        node2.left = copyNode1.left == node2 ? node1 : copyNode1.left;
        node2.right = copyNode1.right == node2 ? node1 : copyNode1.right;

        if (node1.parent != null) {
            if (node1.parent.left == node2) {
                node1.parent.left = node1;
            } else {
                node1.parent.right = node1;
            }
        } else {
            this.root = node1;
        }

        if (node2.parent != null) {
            if (node2.parent.left == node1) {
                node2.parent.left = node2;
            } else {
                node2.parent.right = node2;
            }
        } else {
            root = node2;
        }

        if (this.hasLeft(node1)) {
            node1.left.parent = node1;
        }
        if (this.hasRight(node1)) {
            node1.right.parent = node1;
        }
        if (this.hasLeft(node2)) {
            node2.left.parent = node2;
        }
        if (this.hasRight(node2)) {
            node2.right.parent = node2;
        }
    }


    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        BTNode<E> newRoot = checkPosition(v);

        if (newRoot == root) {
            root = null;
        } else {
            if (newRoot.parent.left == newRoot)
                newRoot.parent.left = null;
            else
                newRoot.parent.right = null;
        }

        newRoot.parent = null;

        LinkedBinaryTree<E> tree = new LinkedBinaryTree<>();
        tree.root = newRoot;
        return tree;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasLeft(p)) {
            throw new RuntimeException("Node already has a left child");
        }


        if (tree != null && !tree.isEmpty()) {
            //Check position will fail if tree is not an instance of LinkedBinaryTree
            BTNode<E> r = checkPosition(tree.root());
            node.setLeft(r);
            r.setParent(node);

            //The source tree will be left empty
            LinkedBinaryTree<E> lbt = (LinkedBinaryTree<E>) tree; //safe cast, checkPosition would fail first
            lbt.root = null;
        }
    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
        BTNode<E> node = checkPosition(p);
        if (tree == this) {
            throw new RuntimeException("Cannot attach a tree over himself");
        }
        if (this.hasRight(p)) {
            throw new RuntimeException("Node already has a right child");
        }

        if (tree != null && !tree.isEmpty()) {
            //Check position will fail if tree is not an instance of LinkedBinaryTree
            BTNode<E> r = checkPosition(tree.root());
            node.setRight(r);
            r.setParent(node);

            //The source tree will be left empty
            LinkedBinaryTree<E> lbt = (LinkedBinaryTree<E>) tree; //safe cast, checkPosition would fail first
            lbt.root = null;
        }
    }

    @Override
    public boolean isComplete() {
        //TODO: Practica 3 Ejercicio 1
        throw new RuntimeException("Not yet implemented");
    }

    // Auxiliary methods

    /**
     * If v is a good binary tree node, cast to BTPosition, else throw exception
     */
    private BTNode<E> checkPosition(Position<E> p) {
        if (p == null || !(p instanceof BTNode)) {
            throw new RuntimeException("The position is invalid");
        }
        return (BTNode<E>) p;
    }

}
