import java.io.*;
import java.util.*;

class BSTree<T>
{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addAttemptWasDupe=false;
	@SuppressWarnings("unchecked")
	public BSTree( String infileName ) throws Exception
	{
		nodeCount=0;
		root=null;
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );
		
		while ( infile.ready() )
			add( (T) infile.readLine() ); // THIS CAST CAUSES THE WARNING
		infile.close();
	}
	
	public int size()
	{
		return nodeCount;
	}
	
	// DUPES BOUNCE OFF RETURN FALSE ELSE INCR COUNT RETURH TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	
        addAttemptWasDupe=false;
		if (null==this.root) //if the root is null then just add one to the left
		{
			root = new BSTNode<T>(key,null,null);
			++nodeCount;
			return true;
		}
		addHelper( this.root, key );
		if (addAttemptWasDupe) return false; // IT WAS REJECTED (DUPE)
		++nodeCount;
		return true;
	}
	@SuppressWarnings("unchecked")
	private void addHelper( BSTNode<T> root, T key )
	{	
		int comp = ((Comparable)key).compareTo(root.key);
		
		// RECALL HOW WE HAD TO STOP ONE NODE SHORT TO 
		// INSERT OR REMOVE? SAME IDEA HERE
		
		if (comp < 0) // go left
		{
			if (root.left == null)
				root.left = new BSTNode<T>( key,null,null );
			else
				addHelper( root.left, key );
		}
		if (comp > 0) // go right
		{
			if (root.right == null)
				root.right = new BSTNode<T>( key,null,null );
			else
				addHelper( root.right, key );
		}
		if ( comp == 0 ) addAttemptWasDupe=true;
    } // END insertHelper


	// GOTTA DO INORDER TRAVERSAL REQUIRING RECURSION
	public void printInOrder()
	{
		printInOrderHelper( this.root );
		System.out.println();
	}	
	// LEFT ROOT RIGHT a.k.a INORDER TAVERSAL
	// PRINTS NODEs IN SORTED ORDER
	private void printInOrderHelper( BSTNode<T> root )
	{
		if (root == null) return;
		printInOrderHelper( root.left );
		System.out.print( root.key + " " );
		printInOrderHelper( root.right );
	}
	
	// - - - - - - -  MODIFY NOTHING ABOVE THIS LINE - - - - - - - 
	// ITS A SEARCH - ONE OF FEW OPS YOU CAN DO ITERATIVELY
	public boolean contains( T key ) 
	{
		return helperContains(key, root);
    }
    
    @SuppressWarnings("unchecked")
    private boolean helperContains(T key, BSTNode<T> root)
    { 
        if (root == null)// the root has no children.
        {
            return false; 
        }
       
        Comparable cKey = (Comparable) key;
       
        if (cKey.compareTo(root.key) < 0)
        {
            return helperContains(key, root.left); // Search left subtree
        
        }else if (cKey.compareTo(root.key) > 0)
        {
            return helperContains(key, root.right); // Search right subtree
        }else //or its found so return true
        {
            return true;
        }
    }
	
}

// -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

class BSTNode<T>
{
	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{
		this.key = key;
		this.left = left;
		this.right = right; 
	}
}