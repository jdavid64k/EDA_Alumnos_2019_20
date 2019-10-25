package material.ordereddictionary;

import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;

import java.util.Comparator;


public class AVLOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {

	public AVLOrderedDict() {
		super();
	}

	public AVLOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	
	@Override
	protected BinarySearchTree<Entry<K,V>> createTree() {
		return new AVLTree<>();
	}
	
}
