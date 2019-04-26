/* Author: Bao-Tran Phan
* Class: CS 3345
* Section: 003
* Semester: Spring 2018
* Project 4: Simplified Red-Black Trees
* The task of this project is to implement in Java a red-black tree 
* data structure. However, the tree will be simplified – you only need 
* to support insertion, not deletion.
*/

// import necessary libraries
import java.util.NoSuchElementException;

public class RedBlackTree<Element extends Comparable<Element>, Value> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	private Node root = null; 

	// Tree Node class
	private class Node {
		private Element e; // key
		private Node leftChild, rightChild, parent;
		private boolean color; // color of parent link

		public Node(Element e, boolean color/* , int size */) {
			this.e = e;
			this.color = color;
			// this.size = size;
		} // end node construc
	} // end node class

	/* method: insert (<AnyType> e) inserts the given element into the tree at the
	 * correct position, and then re-balance if necessary. Regarding input
	 * validation, have a descriptive message if the given element is null.
	 * Alternatively, The return value should indicate whether the given element was
	 * inserted or not. Likewise, the ordering of any 2 elements for the purposes of
	 * insertion and re-balancing is given by compareTo().
	 */
	public boolean insert (Element key) {
		// basic insert
		Node n = new Node(key, RED);
		n.rightChild = null;
		n.leftChild = null;
		Node current = root;
		
		if (current == null)
		{
			root = n;
			n.color = BLACK;
		} else {
			while (current != null) {
				// if there is an existing node with the same key do nothing
				if (current.e.compareTo(key)== 0)
				{
					return false; 		
				}
				else if (current.e.compareTo(key) == -1) {
					current = current.leftChild;
				}
				else if (current.e.compareTo(key)==1) {
					current = current.rightChild;	
				}
			} // end while
			if (current == null) {
				current = n; 
			} // end if 
		} // end else
		return true;
		
		//  rebalance if necessary...
		// parent node is also red (not black)...
		// case 1: parent's sibling is BLACK or null [RESTRUCTURING]
		// case 2: parent's sibling is RED [RECOLOR]
		
	} // end insert 
	
	
/* 	rebalancing helper methods:  */
	
	/* private void recolor()
	 * if (current.color == BLACK && current.rightChild.color == RED && current.rightChild.leftChild.color == RED){
	 * 	if (current!=root)
	 * 		current.color = !current.color;
	 * 	current.rightChild.color = !current.rightChild.color; 
	 * 	current.rightChild.leftChild.color = !current.rightChild.leftChild.color
	 * }
	 */

	/*private void rotateright()
	 * if (current.color==BLACK && current.rightChild.rightChild.color == RED && current.rightChild.color == RED){
	 * 	parent = current.rightChild;
	 *  parent.color = BLACK;
	 *  parent.rightChild = current;
	 *  parent.rightChild.color = RED;
	 *  parent.leftChild = current.rightChild.rightChild;
	 *  parent.leftChild.color = RED; 
	 * } // end if 
	 */
	
	/* private void rotateleft ()
	 if (current.color == BLACK && (current.leftChild.leftChild.color == RED) && current.leftChild.color == RED){
		parent = current.leftChild
		parent.color = BLACK;
		parent.leftChild = current;
		parent.leftChild.color = RED; 
		parent.rightChild = current.leftChild.leftChild;
		parent.rightChild.color = RED; 
	} // end if 
	*/
	
	
	/*method: contains(<AnyType e) returns whether the tree contains any element
	 * that compares equal to the given object using the compareTo method of the
	 * object. This means that you should always do object.compareTo(element).
	 */
	public boolean contains(Element key) {
		// if the given object is null, then contains returns false.
		if (key == null) {
			return false; 
		} // end if 
		Node current = root;
		
		// traverse through tree to find 
		while (current != null) {
			if (current.e.compareTo(key) == 0) {
				return true;
			} // end if 
			else if (current.e.compareTo(key)==-1) {
				current = current.leftChild;
			} // end else if 
			else{
				current = current.rightChild;
			} // end else 
		} // end while
		return false; // returns false if element is not found within tree 
	} // end contains()
	
	
	/*method: preOrder() recursive preorder traversal of the binary search tree-
	 * used alongside with the toString() method. Elements that are RED are preceded
	 * by '*'. Every pair of adjacent elements is separated by whitespace in
	 * printing, but no whitespace occurs between an asterisk and the element
	 * associated with it. Leading and trailing whitespace is tolerable and is
	 * ignored.
	 */
	private void preOrder(Node current, String s) {
		if (current == null)
			return; // does nothing if the tree is empty
		if (current.color == RED)
			s = "*" + current.e + " "; // if the node is RED, indicate with *
		else
			s = current.e + " "; // not red, print as normal
		preOrder(current.leftChild, s); // recursive call to complete left side
		preOrder(current.rightChild, s); // recursive call to complete right side
	} // end preOrder(String,int)

	/*
	 * method: toString() overrides the eponymous method of Object and return a
	 * string representing the pre-order traversal of this tree. The returned string
	 * is the ordered concatenation of invoking the toString method of each element
	 * in the traversal, where every 2 adjacent elements should be separated by (“
	 * ”). If an element is a red node, then it's preceded by an (“*”). An element
	 * located in a black node should not be preceded by an asterisk.
	 * 
	 * OPTIONAL: use a StringJoiner and/or to implement Node#toString() as well. The
	 * color field of the node class should be assigned and evaluated using the RED
	 * and BLACK constants of the enclosing tree class. This means that you should
	 * do color = BLACK or if(color == RED)
	 */
	public String toString() {
		if (root == null) {
			return "The list is empty.\n";
		}
		String output = "";
		preOrder(root, output); // preorder traversal of tree
		return output;
	} // end toString method
	
	
	
} // end red black tree 

	
		
		
	
