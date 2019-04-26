// Name: Bao-Tran (Monica) Phan
// Class: CS 3345 
// Section: 003
// Semester: Spring 2018
// Project 3: BST Lazy Deletion

public class LazyBinarySearchTree {

	private class TreeNode{
		int key;
		TreeNode leftChild;
		TreeNode rightChild;
		boolean deleted;
	} // end class TreeNode
	
	private TreeNode root; 
	
	public LazyBinarySearchTree() 		// constructor
	{ root = null; }
	
	/*method: insert(int)
	 inserts a new element to a leaf. valid set of keys is  integers [1,99]. If the 
	 new element is a duplicate of a non-deleted element already in the tree, then it does nothing. 
	 If the new element is not a duplicate of a non- deleted element, but is a duplicate of a deleted 
	 element, then insert should “undelete” the deleted element in-place. 
	 The return value of insert indicates whether insert logically inserted a new element.*/
	public boolean insert (int key) throws IllegalArgumentException{
		// throw error if key is not within range 
		if(key < 1 || key > 99)
			   throw new IllegalArgumentException("Error: key must be 1-99");
		
		TreeNode node = new TreeNode(); 
		
		// if the root is null, create a new tree
		if(root==null) {
			node.leftChild = null;
			node.rightChild = null;
			node.deleted = false;    // is deleted = false because it is present
			node.key=key;			// initialize data in node to int that is read in
			root = node;
		} // end if
		else {
			node = root; 
			// tree traversal
			while (node!=null) {
				if (key < node.key) {
					node = node.leftChild;		// key becomes left child if < parent
				} // end if
				else if (key > node.key) {
					node = node.rightChild;		// key becomes right child if > parent
				} // end else if
				else {
					if(node.deleted==true) {		// if the key is 'deleted' it is 'undeleted'
						node.deleted=false;
						return true;				// if element inserted ('undeleted'), return true
					} // end if
					else
						return false; 			// if element is not inserted, return false 
				} // end else
			} // end while
			
		} // end else 
		
		node = new TreeNode();		// basic insert 
		node.leftChild = null;
		node.rightChild = null;
		node.deleted = false;
		node.key=key;
		return true;			

	}  // end method insert(int)
	
	/* method: boolean delete(int)
	 it marks the specified element as logically deleted. If the specified element isn't in 
	 the tree or is already marked as deleted, it does nothing 
	 The return value of delete indicates whether delete logically deleted an element.*/
	public boolean delete (int key) throws IllegalArgumentException{
		// if key input by user is invalid, throw an error
		if(key < 1 || key > 99)
			   throw new IllegalArgumentException("Error: key must be 1-99");
		if(root==null) {
			return false;						// does nothing if tree is empty
		} // end if
		TreeNode current = root;
		while (current!=null) {					// tree traversal
			if(current.key==key) {
				if(current.deleted == false) {
					current.deleted = true;		// if key present and is not deleted, mark as deleted
					return true;
				} // end if
				else {
					return false;				// does nothing if key is present + already 'deleted'
				}//end else
			} // end if 
			else if (current.key < key) 
				current = current.rightChild;
			else if (current.key>key)
				current = current.leftChild;
		} // end while
		return false; 
	} // end method delete(int)
	

	/* method: int findMin()
	 return the value of the minimum non-deleted element, or -1 if none exists*/
	int findMin() {
		if (root == null)
			return -1;		// return -1 if the tree is empty
		TreeNode node = new TreeNode();
		node = root;
		while (node.leftChild != null) {		//traverse BST til hit the leftmost left child
			node = node.leftChild;
		} // end while
		return node.key;    					// return leftmost (minimum) child
	} // end method findMin()
	
	/*method: int findMax()
	return the value of the maximum non-deleted element, or -1 if none exists.*/
	int findMax() {
		if (root == null)		// return -1 if the tree is empty
			return -1;
		TreeNode max = new TreeNode();
		max = root;
		while (max.rightChild!=null) {		//traverse BST til hit the rightmost left child
			max = max.rightChild;
		} //end while
		return max.key;						// return rightmost (maximum) child
	} // end findMax() method
	
	/* method: boolean contains(int)
	 return whether the given element both exists in the tree and is non-deleted.*/
	boolean contains (int key) throws IllegalArgumentException{
		// if key input by user is invalid, throw an error
		if(key < 1 || key > 99)
			throw new IllegalArgumentException("Error: key must be 1-99");
		
		if (root == null)				// if tree is empty, return that element isn't present
			return false;		
		TreeNode current = root;
		while(current!=null) {				// tree traversal
			if(current.key==key)				// true if key is found
				return true;
			else if (key < current.key)		// key less than current, move left
				current=current.leftChild;
			else if (key>current.key)		// key greater than current, move right 
				current=current.rightChild;
		}
		return false;						// not found return false
	} // end contains() method
	
	/* WIP method: String toString()
	 perform a preorder traversal  and print the value of each element, including elements marked 
	 as deleted.*/
	public String toString() {
		if (root == null)
			{return "The list is empty.\n";}
		String output = "";
		preOrder(root,output);
		return output;
	} // end toString() method
	
	/* method: preOrder()
	 * recursive preorder traversal of the binary search tree- used alongside with
	 * the toString() method. Elements that are marked as deleted is preceded by a single asterisk. 
	 Every pair of adjacent elements is separated by whitespace in  printing, but no whitespace 
	 occurs between an asterisk and the element associated with it. 
	 Leading and trailing whitespace is tolerable and is ignored. 
	 */
	private void preOrder(TreeNode current, String s) {
		if(current == null)
			return;							// does nothing if the tree is empty
		if(current.deleted==true)
			s = "*" + current.key + " ";		// if the node is marked to be deleted, indicate with *
		else
			s = current.key + " ";				// not "deleted" print as normal 
		preOrder(current.leftChild,s);			// recursive call to complete left side
		preOrder(current.rightChild,s);			// recursive call to complete right side 
	} // end preOrder(String,int)

	/* method height(): returns int of height of given tree. this 
	 * includes "deleted" elements*/
	int height() {
		int rHeight = 0;
		int lHeight = 0;
		if (root == null)
			return -1;		// returns -1 if tree is empty 
		TreeNode node = root;
		
		while(node.rightChild!=null){				// counts height on right side
	        rHeight++;
	        node = node.rightChild;
	    }
		
		node = root;		// reset
		while (node.leftChild!=null) {				// counts height on left side
			lHeight++;
			node = node.leftChild;
		}
		
		if(lHeight>rHeight)							// returns highest count
			return lHeight;
		else
			return rHeight;
	} // end height() method
	
	/* Recurisve Method: int size()
	 * Recursively calls the method that calculates size of the tree*/
	int size() {
		return size(root);
	} // end size()
		 
	 /* Method int size(TreeNode)
	  *  method counts the elements in tree and returns int of size of given tree
	  *  includes "deleted" elements
	  * computes number of nodes in tree */
	int size(TreeNode node)
	{
		if (node == null)
		    return 0;
		else
			return(size(node.leftChild) + 1 + size(node.rightChild));
	 } // end size(TreeNode)

	
} // end public class LazyBST
