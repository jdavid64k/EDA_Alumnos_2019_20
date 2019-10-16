package material.tree.binarytree;

import material.Position;

import java.util.Iterator;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
    //TODO: Practica 3 Ejercicio 2


    @Override
    public Position<E> left(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        return false;
    }

    @Override
    public boolean hasRight(Position<E> v) {
        return false;
    }

    @Override
    public E replace(Position<E> p, E e) {
        return null;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        return null;
    }

    @Override
    public E remove(Position<E> p) throws RuntimeException {
        return null;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {

    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        return null;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        return null;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        return null;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return false;
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        return false;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return false;
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }
}
