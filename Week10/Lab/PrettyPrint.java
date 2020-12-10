import java.io.*;
import java.util.*;

//////////////////////////////////////////////////////////////////////////////////////
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

///////////////////////////////////////////////////////////////////////////////////////
class PrettyPrint<T>
{
	private BSTNode<T> root;
	private int nodeCount;
	private boolean addAttemptWasDupe=false;

	// DEFAULT C'TOR
	public PrettyPrint()
	{
		nodeCount=0;
		root=null;
	}

	// INPUT FILE C'TOR
	@SuppressWarnings("unchecked")
	public PrettyPrint( String infileName ) throws Exception
	{
		nodeCount=0;
		root=null;
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );
		while ( infile.ready() )
			add( (T) infile.readLine() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
    }
    
	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		if (!addAttemptWasDupe) ++nodeCount;
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
    
	public ArrayList<T> makeLevelOrder()
	{	
        ArrayList<T> x = new ArrayList<T>();
        if (this.root == null) return x;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarit
        while ( !q.empty() )
        {	BSTNode<T> n = q.dequeue();
            x.add(n.key);
			if ( n.left  != null ) q.enqueue( n.left );
			if ( n.right != null ) q.enqueue( n.right );
		}

        return x;
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

  public PrettyPrint<T> makeBalancedCopyOf( )
	{
        ArrayList<T> keys = new ArrayList<T>();
		
		insertInOrder(keys, this.root);
		
		PrettyPrint<T> balancedBST = new PrettyPrint<T>();
		
		bsVisit( keys, 0, keys.size()-1, balancedBST);
		
		return balancedBST;   // return that balancedBST;
	}
    private void insertInOrder(ArrayList<T> list, BSTNode<T> root )
    {
		if(root == null) 
		{
			return;
		}
		
		insertInOrder(list, root.left);
        list.add(root.key);
        insertInOrder(list, root.right);
	}

	private void bsVisit(ArrayList<T> list, int low, int high, PrettyPrint<T> BST)
	{
		if(low>high) 
		{
			return;
		}
		int mid = (high+low)/2;
		
		BST.add(list.get(mid));				// V  add this data value to BST
		
		bsVisit(list, low, mid-1, BST);		// L  go L
		
		bsVisit(list, mid+1, high, BST);	// R  go R
    }

    @SuppressWarnings("unchecked")
    public void prettyPrint()
    {
       if(root == null) return;

       int[] levCount = calcLevelCounts();
       ArrayList<T> levOrder = makeLevelOrder();
       T[][] grid = (T[][])new Object [levOrder.size()][levOrder.size()];
       
       ArrayList<T> newList = new ArrayList<T>();
       insertInOrder(newList, this.root);

       int index= 0;
        for(int row = 0 ;  row < levCount.length ; ++row)
        {
            for(int col = 0 ; col < levCount[row] ; ++col)
            {
               grid[row][col] = levOrder.get(index);
               index++;
            }
        }
        
        T[][] mat = (T[][])new Object [levOrder.size()][levOrder.size()];
        for(int row = 0 ;  row < levOrder.size() ; ++row)
        {
            for(int col = 0 ; col < levOrder.size() ; ++col)
            {
               if(grid[row][col] != null)
               {
                   int x = newList.indexOf(grid[row][col]);
                   mat[row][x] = grid[row][col];
               }
            }
        }
       

        for(int row = 0 ;  row < mat.length ; ++row)
        {
            for(int col = 0 ; col < mat[row].length ; ++col)
            {
               if(mat[row][col] != null)
               {
                   System.out.print(mat[row][col] + "  ");
                   
               }else
               {
                   System.out.print("  ");
               }
            }
            System.out.println();
        }
        

    }
}
