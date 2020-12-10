import java.io.*;
import java.util.*;

class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}

class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}

public class PrettyPrint
{
    private BSTNode<T> root;
	private int nodeCount;
    private boolean addAttemptWasDupe=false;
    
    public PrettyPrint(String filename) throws Exception
    {
        nodeCount = 0;
        root=null;
        Scanner infile = new Scanner( new File( infileName ) );
        while ( infile.hasNext() )
        {
            add((T) infile.next() ); // THIS CAST RPODUCES THE WARNING
        }
        
        infile.close();
    }

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
        ArrayList<T> levOrder = new ArrayList<T>();
		if (this.root == null) return ;
		Queue<T> q = new Queue<T>();
		q.enqueue( this.root ); // this. just for emphasis/clarity
		while ( !q.empty() )
        {	BSTNode<T> n = q.dequeue();
            levOrder.add(n.key);
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
        if(root==null) 
        {
            return 0;
        }
        
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
        if (root==null) return;
        ++levelCounts[level];
        calcLevelCounts( root.left, levelCounts, level+1 );
        calcLevelCounts( root.right, levelCounts, level+1 );
    }

    public BSTreeL6<T> makeBalancedCopyOf( )
	{
        ArrayList<T> keys = new ArrayList<T>();
		
		insertInOrder(keys, this.root);
		
		BSTreeL6<T> balancedBST = new BSTreeL6<T>();
		
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

	private void bsVisit(ArrayList<T> list, int low, int high, BSTreeL6<T> BST)
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

    public void prettyPrint()
    {
        if(root == null)
        {
            return;
        }
        
        int[] levCount = calcLevelCounts();      // 0 1   2       3               4
                                                 // 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
        ArrayList<T> levOrder = makeLevelOrder();// M F T C I P W A D G K N R U Y B E H J L O Q S V X Z
        //use the ArrayList to print this to the screen
        //Grid[][]

        int index= 0;
        
        for(row = 0 ;  row < levCount.length ; ++row)
        {
            for(col = 0 ; col < levCounts[row] ; ++col)
            {
                System.out.print(levOrder[index++] + " ");
                System.out.println();
            }
        }

/*
//Grid[row][col] = levOrder[index++]
}
M
F T C I P W
A D G K N R U Y
B E H J L O Q S V X Z

        int[] levCount = calcLevelCounts();
        ArrayList<T> levOrder = makeLevelOrder();

        int [][] x = new int[levOrder.size][levOrder.size];
        for(int i = 0; i <= levCount.length; i++)
        {
            for(int j = 0; j <= levCount[i]; j++)
            {
                x[i][j] = levOrder.get(i+j);
            }
        }

        for(int i = 0; i <= levCount.length; i++)
        {
            for(int j = 0; j <= levCount[i]; j++)
            {
                if(x[i][j] == null)
                {

                }

            }
        }

    */
    }
}
