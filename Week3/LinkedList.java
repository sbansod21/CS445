import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	@SuppressWarnings("unchecked")	
	public LinkedList( String fileName, boolean orderedFlag )
	{
		head=null;
		try
		{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{
				if (orderedFlag)
					insertInOrder( (T)infile.readLine() );  // WILL INSERT EACH ELEM INTO IT'S SORTED POSITION
				else
					insertAtTail( (T)infile.readLine() );  // TACK EVERY NEWELEM ONTO END OF LIST. ORIGINAL ORDER PRESERVED
			}
			infile.close();
		}
		catch( Exception e )
		{
			System.out.println( "FATAL ERROR CAUGHT IN C'TOR: " + e );
			System.exit(0);
		}
	}

	//-------------------------------------------------------------

	// inserts new elem at front of list - pushing old elements back one place
	public void insertAtFront(T data)
	{
		head = new Node<T>(data,head);
	}

	// we use toString as our print


	public String toString()
	{
		String toString = "";

		for (Node<T> curr = head; curr != null; curr = curr.getNext())
		{
			toString += curr.getData();		// WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " ";
		}

		return toString;
	}

	// ########################## Y O U   W R I T E    T H E S E    M E T H O D S ########################

	
	public int size() // OF COURSE MORE EFFICIENT TO MAINTAIN COUNTER. BUT YOU WRITE LOOP!
	{
	    int size = 0;
        for (Node<T> curr = head; curr != null; curr = curr.getNext()) 
        {
			size++;
		}

		return size;
	}


	public boolean empty()
	{
		return this.size() == 0;
	}

	
	public boolean contains( T key )
	{
		return (search(key) != null);
	}

	
	public Node<T> search( T key )
	{
		for(Node<T> curr = head; curr != null; curr = curr.getNext())
		{
			if(key.equals(curr.getData()))
			{
				return curr;
			}
			
		}
			return null;
	}

	
	public void insertAtTail(T data) // TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	{
        Node<T> curr = head;

		if (head == null) // if theres no element after head then..
		{
			head = (new Node<T>(data));

		} else {
			while (curr.getNext() != null) {
				
				curr = curr.getNext();
			}

			curr.setNext(new Node<T>(data)); // set the next one to a new node
		}
	}

	@SuppressWarnings("unchecked")
	public void insertInOrder(T  data) // PLACE NODE IN LIST AT ITS SORTED ORDER POSTIOPN
	{
		Comparable cdata = (Comparable) data;
		
		if ( (head==null) || cdata.compareTo( head.getData() ) < 0 )  //if the list is empty OR the element is LESS THAN the first one
        {
		   insertAtFront( data );  //THEN insert at FRONT
		   return;
		}
		
		Node<T> curr = head;
		while(curr.getNext() != null && (cdata.compareTo(curr.getNext().getData()) > 0))
		{
			curr = curr.getNext();
		}
		curr.setNext(new Node<T>(data, curr.getNext()));

	}
	
	
	public boolean remove(T key) // FIND/REMOVE 1st OCCURANCE OF A NODE CONTAINING KEY
	{
		if(this.empty())
		{
			return false;
		
		}else if (head.getData().equals(key))
		{
			head = head.getNext();
			return true;

		}else
		{
			Node<T> curr = head;

			while(curr.getNext() != null)
			{
				if(curr.getNext().getData().equals(key))
				{
					curr.setNext(curr.getNext().getNext());
					return true;
				}
				
				curr = curr.getNext();
			}

			return false;
		}

	}	

	
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(this.empty()) //if the list is empty then return false
		{
			return false;
		}else if(size() == 1)
		{
			head = null;
			return true;
		}else{

			Node<T> curr = head;

			while(curr.getNext().getNext() != null)		//keep doing next until the NEXT one is null so stops at second to last node
			{	
				curr = curr.getNext();
			}

			curr.setNext(null); //clears the second one
			return true;
		}
	}

	
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(this.empty())
		{
			return false;
		}else{
			//only need curr when iterating the list
			head = head.getNext();
			return true;
		}
	}


	public LinkedList<T> union( LinkedList<T> other )
	{
		//copy both lists in order
		//check for repeats
		//traverse other the same way OTHER.HEAD

		//create a new list and copy THIS into it

		LinkedList<T> UnionList = new LinkedList<>();
		Node<T> thisCurr = head;
		
		while(thisCurr != null)
		{
			UnionList.insertInOrder(thisCurr.getData());
			thisCurr = thisCurr.getNext();
		}
		
		Node<T> otherCurr = other.head;
		while(otherCurr != null)
		{
			if(!UnionList.contains(otherCurr.getData()))
			{
				UnionList.insertInOrder(otherCurr.getData());
			}
			
			otherCurr = otherCurr.getNext();
		}

		return  UnionList; // CHANGE TO YOUR CODE
	}
	
	
	public LinkedList<T> inter( LinkedList<T> other )
	{
		LinkedList<T> InterList = new LinkedList<>();
		Node<T> thisCurr = head;

		while(thisCurr != null)
		{
			if(other.contains(thisCurr.getData()))
			{
				InterList.insertInOrder(thisCurr.getData());
			}
			thisCurr = thisCurr.getNext();
		}
		return  InterList; // CHANGE TO YOUR CODE
	}


	public LinkedList<T> diff( LinkedList<T> other )
	{
		LinkedList<T> DiffList = new LinkedList<>();
		Node<T> thisCurr = this.head;

		while(thisCurr != null)
		{
			if(this.contains(thisCurr.getData()))
			{
				if(!other.contains(thisCurr.getData()))
				{
					DiffList.insertInOrder(thisCurr.getData());
				}
			}
			thisCurr = thisCurr.getNext();
		}

		return DiffList; // CHANGE TO YOUR CODE
	}

	public LinkedList<T> xor( LinkedList<T> other )
	{
		return union(other).diff(inter(other)); // CHANGE TO YOUR CODE
	}

} //END LINKEDLIST CLASS
