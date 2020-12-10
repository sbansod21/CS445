import java.io.*;
import java.util.*;

public class CDLL_List<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;

	public CDLL_List()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE

	public CDLL_List( String fileName, String insertionMode ) throws Exception
	{
			BufferedReader infile = new BufferedReader( new FileReader( fileName ) );
			while ( infile.ready() )
			{	@SuppressWarnings("unchecked")
				T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
				if ( insertionMode.equals("atFront") )
					insertAtFront( data );
				else if ( insertionMode.equals( "atTail" ) )
					insertAtTail( data );
				else
					die( "FATAL ERROR: Unrecognized insertion mode <" + insertionMode + ">. Aborting program" );
			}
			infile.close();
	}

	private void die( String errMsg )
	{
		System.out.println( errMsg );
		System.exit(0);
	}

	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT DYNAMICALLY WITH A TRAVERSAL LOOP
	@SuppressWarnings("unchecked")
	public int size()
	{
		CDLL_Node<T> curr = head;
		
		while(curr.next != head) 
        {
			System.out.println("sizing");
			count++;
			curr = curr.next;
		}
		return count;
	}

	// TACK A NEW NODE ONTO THE FRONT OF THE LIST
	@SuppressWarnings("unchecked")
	public void insertAtFront(T data)
	{
		// BASE CASE WRITTEN FOR YOU
		CDLL_Node<T> newNode = new CDLL_Node(data,null,null);
		if (head==null) 	//i dont understnad this logic
		{
			newNode.next=newNode;
			newNode.prev=newNode;
			head = newNode;
			return;
		}

		//fix this
		CDLL_Node<T> old1st=head,oldlast=head.prev;
		head=newNode;
		newNode.next=old1st;
		newNode.prev=oldlast;
		old1st.prev=newNode;
		oldlast.next=newNode;
		// FINSIH THIS METHOD.

		// OK LIST IS NOT EMPTY. INSERT NEW NODE BETWEEN HEAD POINTER AND 1ST NODE
		// MAKE HEAD POINT TO NEW NODE AND MAKE TAIL POIT TO IT TOO
	}

	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		// BASE CASE WRITTEN FOR YOU
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.next=newNode;
			newNode.prev=newNode;
			head = newNode;
			return;
		}
		
		CDLL_Node<T> curr = head;
		//CDLL_Node<T> newNode =
		while(curr.next != head)
		{
			curr = curr.next;
		}

		curr.next = newNode;
		newNode.prev = curr;

		newNode.next = head;
		head.prev = newNode;
		// FINISH THIS METHOD. INSERT NEW NODE AFTER THE LAST/TAIL NODE.
		// MAKE THE OLD TAIL POINT TO THIS NEW NODE. MAKE THIS NEW NODE POINT TO SAME AS HEAD.
	}

	// RETURN TRUE/FALSE THIS LIST CONTAINS A NODE WITH DATA EQUALS KEY
	public boolean contains( T key )
	{
		return (search(key) != null);
	}

	// RETURN REF TO THE FIRST NODE (SEARCH CLOCKWISE FOLLOWING next) THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
	public CDLL_Node<T> search( T key )
	{
		CDLL_Node<T> curr = head;
	
		while(curr.next != head)
		{
			System.out.println("getin stuck");
			if(key.equals(curr.data))
			{
				return curr;
			}
			curr = curr.next;
		}

		return null;
	}

	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		return "";
	}

} // END CDLL_LIST CLASS

// PRIVATE TO CODE OUTSIDE FILE. BUT PUBLIC TO CODE INSIDE
class CDLL_Node<T>
{
  T data; // DONT DEFINE MEMBERS AS PUBLIC OR PRIVATE
  CDLL_Node<T> prev, next; //
  CDLL_Node() 		{ this( null, null, null ); }
  CDLL_Node(T data) { this( data, null, null);  }
  CDLL_Node(T data, CDLL_Node<T> prev, CDLL_Node<T> next)
  {	this.data=data; this.prev=prev; this.next=next;
  }
  public String toString() // TOSTRING MUST BE PUBLIC
  {	return ""+data;
  }
} //END NODE CLASS

