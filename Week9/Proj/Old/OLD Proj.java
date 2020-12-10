import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}
///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}
////////////////////////////////////////////////////////////////////////////////
class BSTreeP6<T>
{
	private BSTNode<T> root;
	private int nodeCount;
  private boolean addAttemptWasDupe=false;

	public BSTreeP6()
	{
		nodeCount = 0;
		root=null;
	}

	@SuppressWarnings("unchecked")
	public BSTreeP6( String infileName ) throws Exception
	{
		nodeCount = 0;
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
  } // END addHelper

	public int size()
	{
		return nodeCount; // LOCAL VAR KEEPING COUNT
	}

	public int countNodes() // DYNAMIC COUNT ON THE FLY TRAVERSES TREE
	{
		return countNodes( this.root );
	}
	private int countNodes( BSTNode<T> root )
	{
		if (root==null) return 0;
		return 1 + countNodes( root.left ) + countNodes( root.right );
	}

	// INORDER TRAVERSAL REQUIRES RECURSION
	public void printInOrder()
	{
		printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{
		if (root == null) return;
		printInOrder( root.left );
		System.out.print( root.key + " " );
		printInOrder( root.right );
	}

	public void printLevelOrder()
	{
		if (this.root == null) return;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarity
		while ( !q.empty() )
		{	BSTNode<T> n = q.dequeue();
			System.out.print( n.key + " " );
			if ( n.left  != null ) q.enqueue( n.left );
			if ( n.right != null ) q.enqueue( n.right );
		}
	}

  public int countLevels()
  {
    return countLevels( root );
  }
  private int countLevels( BSTNode root)
  {
    if (root==null) return 0;
    return 1 + Math.max( countLevels(root.left), countLevels(root.right) );
  }

  public int[] calcLevelCounts()
  {
    int levelCounts[] = new int[countLevels()];
    calcLevelCounts( root, levelCounts, 0 );
    return levelCounts;
  }
  private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
  {
    if (root==null)return;
    ++levelCounts[level];
    calcLevelCounts( root.left, levelCounts, level+1 );
    calcLevelCounts( root.right, levelCounts, level+1 );
  }

  /////////////////////////////////////////////////
  // WRITE THE REMOVE METHOD 
  // return true only if it finds/removes the node
  @SuppressWarnings("unchecked")
  public boolean remove( T key2remove )
  {
    T key = key2remove;
    Comparable cKey = (Comparable) key;
    BSTNode<T> parent = findParent(key, this.root);

    if(parent == null)
    {
      return false;
    }   

    if(parent.right == null )
    { 
      if(parent.left.left == null && parent.left.right == null)//this is the leaf case
      {
        return leafCase(parent, cKey);
        
      }else if(parent.left.left != null && parent.left.right == null)//1child left
      {
        int child = 2; //to indicate the deadNode has a left child
        return oneChild(parent, cKey, child);
      }else if(parent.left.right !=null && parent.left.left == null)//1child right
      {
        int child = 1; //to indicate the deadNode has a right child
        return oneChild(parent, cKey, child);
      }
  
      else if(parent.left.left != null && parent.left.right != null){
  
        return twoChild(parent, cKey);
  
      }

      
    }//end of first outer if
    
    if(parent.left == null ){
		
      if(parent.right.left == null && parent.right.right == null)
      {//this is the leaf case
  
        return leafCase(parent, cKey);
        
      }
  
      else if(parent.right.left !=null && parent.right.right == null){
        //this would be the 1Child case
        int child = 2; //to indicate the deadNode has a left child
        return oneChild(parent, cKey, child);
  
      }
  
      else if(parent.right.right !=null && parent.right.left == null){
        //this is also the 1ChildCase
        int child = 1; //to indicate the deadNode has a right child
        return oneChild(parent, cKey, child);
      }
  
      else if(parent.right.left != null && parent.right.right != null){
  
        return twoChild(parent, cKey);
  
      }
    }
    return false;
    
  }
  
  @SuppressWarnings("unchecked")
  private boolean leafCase(BSTNode<T> root, Comparable cKey)
  {
    if(cKey.compareTo(root.left.key) == 0)
    {
        root.left = null;
        return true;

    }else if(cKey.compareTo(root.right.key) == 0)
    {
        root.right = null;
        return true;
    }
      return false;
  }
  @SuppressWarnings("unchecked")
  private boolean oneChild(BSTNode<T> root, Comparable cKey, int side)
  {
    if(side == 0)
    {
      if(cKey.compareTo(root.left.key) == 0)
      {
        root = root.right;
        return true;
      }
    }else if(side == 1)
    {
      if(cKey.compareTo(root.right.key) == 0)
      { 
          root = root.left;
          return true;
      }
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  private boolean twoChild(BSTNode<T> root, Comparable cKey)
  {
      return false;
  }
  
  @SuppressWarnings("unchecked")
  private BSTNode<T> findParent(T key, BSTNode<T> root)
  {
    Comparable cKey = (Comparable) key;
    int k = cKey.compareTo(root.key);
    
    while(k > 0) 
    {   
        k = cKey.compareTo(root.key); //only for the loop to work

        if(k > 0)
        {
          if(root.right != null)
          {
            if(cKey.compareTo(root.right.key) == 0)
            {
              return root;

            }else
            {
              root = root.right;
            }
          }else
          {
            return null;
          }
        }//end of IF(right)

        if(k < 0)
        {
          if(root.left != null)
          {
            if(cKey.compareTo(root.left.key) == 0)
            {
              return root;
            }else
            {
              root = root.left;
            }
          }else
          {
            return null;
          }
        }//return of IF (left)
        
    }//end of while
    return null;
  }//end of find parent
} // END BSTREEP6 CLASS

