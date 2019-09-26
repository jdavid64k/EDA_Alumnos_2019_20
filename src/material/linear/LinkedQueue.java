package material.linear;

public class LinkedQueue<E> implements Queue<E> {
    private int size;
    private Node<E> front;
    private Node<E> tail;

    private class Node<T> {
        private E element;
        private Node<T> next;


        public Node(E element){
            this.setElement(element);
            setNext(null);
        }


        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public int size() {
        return size;

    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public E front() {
        if(isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        return this.front.getElement();
    }

    @Override
    public void enqueue(E element) {
        Node<E> newNode = new Node<>(element);
        if(isEmpty()){
            this.front = newNode;
        } else {
            this.tail.setNext(newNode);
        }
        this.tail = newNode;
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new RuntimeException("Queue is empty");
        }
        E element = this.front();
        this.front = this.front.getNext();
        if(this.front == null) {
            this.tail = null;
        }
        size--;
        return element;
    }
}
