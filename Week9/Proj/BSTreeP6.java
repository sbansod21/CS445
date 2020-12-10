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
    if(this.root == null)
    {
		  return false;
	  }
    if(this.root.key.equals(key2remove))
    {
      if(this.root.left !=null && this.root.right != null)
      {
			  BSTNode<T> node = this.root.left;
        while(node.right != null)
        {
          node = node.right;
        }
        T data = node.key;
        remove(data);

        this.root.key = data;
        return true;
      }else if(this.root.right != null && this.root.left == null)
      {
			  this.root = this.root.right;
			  return true;
      }else if(this.root.left != null && this.root.right == null)
      {
			  this.root = this.root.left;
        return true;
      }else if(this.root.right == null && this.root.left == null)
      {
			  this.root = null;
        return true;
      }
		return false;        
	}

	Comparable cKey = (Comparable) key2remove;
	BSTNode<T> parent = findParent(key2remove, this.root);
	
  if(parent == null) return false;

  if(parent.right == null )
  {
    if(parent.left.left == null && parent.left.right == null)
		{
			return leafRemoval(parent, key2remove);
    }else if(parent.left.left !=null && parent.left.right == null)
    {
			int child = 2; 
			return oneChildRemoval(parent, key2remove, child);
    }else if(parent.left.right !=null && parent.left.left == null)
    {
			int child = 1;
			return oneChildRemoval(parent, key2remove, child);
    }else if(parent.left.left != null && parent.left.right != null)
    {
			return twoChildRemoval(parent, key2remove);
		}
  }else if(parent.left == null)
  {
    if(parent.right.right == null && parent.right.left == null)
    {
			return leafRemoval(parent, key2remove);
    }else if(parent.right.right != null && parent.right.left == null)
    {
			int child = 1;
			return oneChildRemoval(parent, key2remove, child);
    }else if(parent.right.left != null && parent.right.right == null)
    {
			int child = 2;
			return oneChildRemoval(parent, key2remove, child);
    }else if(parent.right.left != null && parent.right.right != null)
    {
			return twoChildRemoval(parent, key2remove);
		}
  }else
  {
    if(cKey.compareTo(parent.right.key) == 0)
    {
      if(parent.right.right == null && parent.right.left == null)
      {
				return leafRemoval(parent, key2remove);
      }else if(parent.right.right != null && parent.right.left == null)
      {
			  int child = 1;
			  return oneChildRemoval(parent, key2remove, child);
      }else if(parent.right.left != null && parent.right.right == null)
      {
			  int child = 2;
			  return oneChildRemoval(parent, key2remove, child);
      }else if(parent.right.left != null && parent.right.right != null)
      {
			  return twoChildRemoval(parent, key2remove);
			}
    }else if(cKey.compareTo(parent.left.key) == 0)
    {
		  if(parent.left.left == null && parent.left.right == null)
		  {
			  return leafRemoval(parent, key2remove);
      }else if(parent.left.left !=null && parent.left.right == null)
      {
			  int child = 2;
			  return oneChildRemoval(parent, key2remove, child);
      }else if(parent.left.right !=null && parent.left.left == null)
      {
			  int child = 1; 
        return oneChildRemoval(parent, key2remove, child);
      }else if(parent.left.left != null && parent.left.right != null)
      {
		  	return twoChildRemoval(parent, key2remove);
		  }
		}
	}
    return false;
}


  @SuppressWarnings("unchecked")
  public boolean leafRemoval(BSTNode<T> parent, T key2remove)
  {
    Comparable cKey = (Comparable) key2remove;
    if(cKey.compareTo(parent.right.key) == 0)
    {
			parent.right = null;
      return true;
    }else if(cKey.compareTo(parent.left.key) == 0)
    {
			parent.left = null;
      return true;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  public boolean oneChildRemoval(BSTNode<T> parent, T key2remove, int child)
  {
    Comparable cKey = (Comparable) key2remove;
    
    if(cKey.compareTo(parent.right.key) == 0)
    {
      if(child == 1)
      {
			  parent.right = parent.right.right;
			  return true;
      }else
      {
			  parent.right = parent.right.left;
        return true;
      }
    }else if(cKey.compareTo(parent.left.key) == 0)
    {
      if(child == 1)
      {
			  parent.left= parent.left.right;
        return true;
      }else
      {
			  parent.left = parent.left.left;
			  return true;
		  }
	  }
	  return false;
  }

  @SuppressWarnings("unchecked")
  public boolean twoChildRemoval(BSTNode<T> parent, T key2remove)
  {
    Comparable cKey = (Comparable) key2remove;
    BSTNode<T> deadNode = parent;
	
    if(parent.left != null && cKey.compareTo(parent.left.key) == 0 )
    {
		  deadNode = parent.left;
    }else if(parent.right != null && cKey.compareTo(parent.right.key) == 0)
    {
		  deadNode = parent.right;
	  }

	  BSTNode<T> node = deadNode.left;

    while(node.right != null)
    {
		  node = node.right; 
    }
    
	  T data = node.key; 

	  remove(data);
	
    if(cKey.compareTo(parent.right.key) == 0)
    {
		  parent.right.key = data;
		  return true;
    }else if(cKey.compareTo(parent.left.key) == 0) 
    {
		  parent.left.key = data;
		  return true;
	  }
    
    return false;
  }
 

  @SuppressWarnings("unchecked")
  public boolean rootCase(T key2remove, BSTNode<T> root)
  {
    if(root.left != null && root.right != null)
    {
		  BSTNode<T> node = root.left;
      while(node.right != null)
      {
			  node = node.right;
		  }

		  T data = node.key;

		  remove(data);

		  root.key = data;
		  return true;
    }else
    {
		  return false;
	  }
	}

  

  @SuppressWarnings("unchecked")
  public BSTNode<T> findParent(T key2remove, BSTNode<T> root)
  {
	  Comparable cKey = (Comparable) key2remove;
	  int cmp = cKey.compareTo(root.key);
	
    while(cmp != 0)
    {
		  cmp = cKey.compareTo(root.key);
      if(cmp > 0)
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
			}

      if(cmp < 0)
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
			}
	}
	    return null;
  }
} // END BSTREEP6 CLASS
