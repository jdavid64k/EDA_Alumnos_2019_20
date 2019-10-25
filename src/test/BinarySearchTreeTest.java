package test;

import material.Position;
import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    BinarySearchTree<Integer> b;


    public BinarySearchTree<Integer> newBST(){
        //TODO: Aqui se define la implementacion a comprobar
        return new LinkedBinarySearchTree<>();
//        return new AVLTree<>();
//        return new RBTree<>();
    }


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        b = newBST();
    }


    @Test
    void random_test(){
        final int N=1000;
        Random rnd = new Random();
        ArrayList<Integer> list = new ArrayList<>();
        for(int i=0; i<N;i++){
            int r = rnd.nextInt(N);
            list.add(r);
            b.insert(r);
        }
        StringBuilder sb = new StringBuilder();
        list.sort(Comparator.naturalOrder());
        list.forEach(e -> sb.append(' ').append(e));
        String expected = sb.toString();
        sb.setLength(0);
        b.forEach(pos -> sb.append(' ').append(pos.getElement()));
        String output = sb.toString();
        assertEquals(expected,output);

        rnd = new Random();
        for(int i=0; i<=N/2; i++){
            Integer integer = list.get(rnd.nextInt(list.size()));
            list.remove(integer);
            Position<Integer> pos = b.find(integer);
            assertNotNull(pos);
            b.remove(pos);
        }

        sb.setLength(0);
        list.sort(Comparator.naturalOrder());
        list.forEach(e -> sb.append(' ').append(e));
        expected = sb.toString();
        sb.setLength(0);
        b.forEach(pos -> sb.append(' ').append(pos.getElement()));
        output = sb.toString();
        assertEquals(expected,output);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        b = null;
    }

    @Test
    void find() {
        final int N = 25;
        for (int i = 0; i < N; i+=2)
            b.insert(i);

        b.insert(17);
        Position<Integer> p = b.find(17);
        assertEquals(17, p.getElement().intValue());
        p = b.find(2);
        assertEquals(2, p.getElement().intValue());
        p = b.find(13);
        assertNull(p);
    }

    @Test
    void findAll() {
        b.insert(1);
        b.insert( 7);
        b.insert( 7);
        b.insert(7);
        final int N = 25;
        for(int i=0; i<N; i++) {
            b.insert(i);
        }

        Iterable<? extends Position<Integer>> all;
        int found, num, expected;

        num = -5;
        expected=0;
        all = b.findAll(num);
        found = 0;
        for(Position<Integer> p: all)
            found++;
        assertEquals(expected, found);

        num = 1;
        expected = 2;
        all = b.findAll(num);
        found=0;
        for(Position<Integer> p: all){
            found++;
            assertEquals(Integer.valueOf(num), p.getElement());
        }
        assertEquals(expected, found);

        num = 7;
        expected = 4;
        all = b.findAll(num);
        found=0;
        for(Position<Integer> p: all){
            found++;
            assertEquals(Integer.valueOf(num), p.getElement());
        }
        assertEquals(expected, found);

        num = 10;
        expected = 1;
        all = b.findAll(num);
        found=0;
        for(Position<Integer> p: all){
            found++;
            assertEquals(Integer.valueOf(num), p.getElement());
        }
        assertEquals(expected, found);

    }

    @Test
    void insert() {
        Position<Integer> insertPosition = b.insert(1);
        assertNotNull(insertPosition);
        assertEquals(Integer.valueOf(1),insertPosition.getElement());

        Position<Integer> foundPosition = b.find(1);
        assertNotNull(foundPosition);
        assertEquals(Integer.valueOf(1),foundPosition.getElement());
    }

    @Test
    void insert_many() {
        b.insert(1);
        final int N = 25;
        for(int i=0; i<N; i++) {
            b.insert(i);
        }

        StringBuilder sb = new StringBuilder(); //efficient string concatenation
        for (Position<Integer> pos: b) {
            sb.append(' ').append(pos.getElement());
        }
        assertEquals(" 0 1 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24",sb.toString());
    }

    @Test
    void isEmpty() {
        assertTrue(b.isEmpty());
        b.insert(1);
        assertFalse(b.isEmpty());
    }

    @Test
    void remove() {
        Position<Integer> p1,p2,p3;
        Integer r1,r2,r3;
        p1 = b.insert(1);
        p2 = b.insert(2);
        p3 = b.insert(3);
        assertEquals(3,b.size()); //sanity check

        r1 = b.remove(p1);
        assertEquals(2,b.size());
        assertEquals(p1.getElement(),r1);
        r2 = b.remove(p2);
        assertEquals(1,b.size());
        assertEquals(p2.getElement(),r2);
        r3 = b.remove(p3);
        assertEquals(0,b.size());
        assertEquals(p3.getElement(),r3);
        assertTrue(b.isEmpty());

    }

    @Test
    void remove_many() {
        final int N = 25;
        Position<Integer>[] positions = new Position[N];

        for(int i=0; i<N; i++){
            positions[i]= b.insert(i);
        }
        assertEquals(b.size(), N);

        //remove
        int remaining = b.size();
        for(int i=0; i<N; i++){
            Integer removed = b.remove(positions[i]);
            remaining--;
            assertEquals(b.size(),remaining);
            assertEquals(positions[i].getElement(),removed);

        }
        assertTrue(b.isEmpty());


    }



    @Test
    void size() {
        assertEquals(0, b.size());
        b.insert(5);
        assertEquals(1,b.size());
        b.insert(10);
        assertEquals(2,b.size());
        b.insert(5);
        assertEquals(3,b.size());

        int prev = b.size();
        final int N = 25;
        for (int i = 0; i < N; i++) {
            b.insert(i);
            assertEquals(prev+i+1,b.size());
        }
    }

    @Test
    void findRange() {//inclusivo
        b.insert(-5);
        b.insert(2);
        b.insert(7);
        b.insert(7);
        final int N=25;
        for(int i=0; i<N; i++){
            b.insert(i);
        }

        RuntimeException exception = assertThrows(RuntimeException.class, () -> b.findRange(2, 1));
        assertEquals("Invalid range. (min>max)", exception.getMessage());

        Iterable<Position<Integer>> range;
        String output, expected;
        final StringBuilder sb = new StringBuilder();

        sb.setLength(0);//clear
        range = b.findRange(-50, -10);
        assertNotNull(range);
        expected = "";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = b.findRange(N+10, N+20);
        assertNotNull(range);
        expected = "";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = b.findRange(1, 1);
        assertNotNull(range);
        expected = " 1";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = b.findRange(-10, -4);
        assertNotNull(range);
        expected = " -5";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = b.findRange(6, 7);
        assertNotNull(range);
        expected = " 6 7 7 7";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        range = b.findRange(6, 10);
        assertNotNull(range);
        expected = " 6 7 7 7 8 9 10";
        range.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

    }

    @Test
    void first() {
        Integer expected;
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> b.first());
        assertEquals("No first element.",runtimeException.getMessage());

        expected = 1;
        b.insert(expected);
        assertEquals(expected, b.first().getElement());
        final int N=25;
        for(int i=0; i<N;i++){
            b.insert(i);
        }

        expected = 0;
        assertEquals(expected,b.first().getElement());

        expected=-1;
        b.insert(expected);
        assertEquals(expected,b.first().getElement());

    }

    @Test
    void last() {

        Integer expected;
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> b.last());
        assertEquals("No last element.",runtimeException.getMessage());

        expected = 1;
        b.insert(expected);
        assertEquals(expected, b.last().getElement());
        final int N=25;
        for(int i=0; i<N;i++){
            b.insert(i);
        }
        expected = N-1;
        assertEquals(expected,b.last().getElement());


    }

    @Test
    void successors() {//mayores o iguales
        Position<Integer> first = b.insert(-5);
        b.insert(2);
        b.insert(7);
        b.insert(7);
        final int N=25;
        Position<Integer> positions[] = new Position[N];
        for(int i=0; i<N; i++){
            positions[i] = b.insert(i);
        }
        Iterable<Position<Integer>> successors;
        String output, expected;
        final StringBuilder sb = new StringBuilder();

        sb.setLength(0);//clear
        successors = b.successors(first);
        assertNotNull(successors);
        expected = " -5 0 1 2 2 3 4 5 6 7 7 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24";
        successors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);
        successors = b.successors(positions[7]);
        assertNotNull(successors);
        expected = " 7 7 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24";
        successors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        successors = b.successors(positions[17]);
        assertNotNull(successors);
        expected = " 17 18 19 20 21 22 23 24";
        successors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);//clear
        successors = b.successors(positions[24]);
        assertNotNull(successors);
        expected = " 24";
        successors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

    }

    @Test
    void predecessors() {//menores o iguales
        Position<Integer> first = b.insert(-5);
        b.insert(2);
        b.insert(7);
        b.insert(7);
        final int N=25;
        Position<Integer> positions[] = new Position[N];
        for(int i=0; i<N; i++){
            positions[i] = b.insert(i);
        }
        Iterable<Position<Integer>> predecessors;
        String output, expected;
        final StringBuilder sb = new StringBuilder();

        sb.setLength(0);//clear
        predecessors = b.predecessors(first);
        assertNotNull(predecessors);
        expected = " -5";
        predecessors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);
        predecessors = b.predecessors(positions[7]);
        assertNotNull(predecessors);
        expected = " 7 7 7 6 5 4 3 2 2 1 0 -5";
        predecessors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);
        predecessors = b.predecessors(positions[11]);
        assertNotNull(predecessors);
        expected = " 11 10 9 8 7 7 7 6 5 4 3 2 2 1 0 -5";
        predecessors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);

        sb.setLength(0);
        predecessors = b.predecessors(positions[24]);
        assertNotNull(predecessors);
        expected = " 24 23 22 21 20 19 18 17 16 15 14 13 12 11 10 9 8 7 7 7 6 5 4 3 2 2 1 0 -5";
        predecessors.forEach(p -> sb.append(' ').append(p.getElement()));
        output = sb.toString();
        assertEquals(expected, output);
    }
}