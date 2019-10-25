package material.ordereddictionary;

import material.tree.binarysearchtree.LinkedBinarySearchTree;

import java.util.Comparator;


/**
 * Realization of a dictionary by means of a binary search tree.
 */
public class BSTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {
	
	public BSTOrderedDict() {
		super();
	}
	
	public BSTOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	protected LinkedBinarySearchTree<Entry<K,V>> createTree (){
		return new LinkedBinarySearchTree<Entry<K,V>>();
	}	
}

