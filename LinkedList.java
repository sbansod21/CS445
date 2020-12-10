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

		for (Node curr = head; curr != null; curr = curr.getNext())
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
	
		while(cdata.compareTo(curr.getNext().getData()) > 0 )
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
			head = null;
			return true;

		}else
		{
			Node<T> curr = head;

			while(curr.getNext().getData() != key)
			{
				curr = curr.getNext();
			}
			curr.setNext(curr.getNext().getNext());
			
		}
	
	}

	
	public boolean removeAtTail()	// RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		if(this.empty())
		{
			return false;
		}

		Node<T> curr = head;
		while((curr.getNext()!= null))
		{
			curr = curr.getNext();
		}

		curr.setNext(null);
		return true; // CHANGE TO YOUR CODE
	}

	
	public boolean removeAtFront() // RETURNS TRUE IF THERE WAS NODE TO REMOVE
	{
		return false; // CHANGE TO YOUR CODE
	}


	public LinkedList<T> union( LinkedList<T> other )
	{
		return  null; // CHANGE TO YOUR CODE
	}
	
	
	public LinkedList<T> inter( LinkedList<T> other )
	{
		return  null; // CHANGE TO YOUR CODE
	}


	public LinkedList<T> diff( LinkedList<T> other )
	{
		return  null; // CHANGE TO YOUR CODE
	}

	
	public LinkedList<T> xor( LinkedList<T> other )
	{
		return  null; // CHANGE TO YOUR CODE
	}

} //END LINKEDLIST CLASS
