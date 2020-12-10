import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method 
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE
	
	public CDLL_JosephusList( String infileName ) throws Exception
	{
		Scanner kbd = new Scanner(System.in);
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );	
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked") 
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data ); 
		}
		infile.close();
	}
	

	
	// ########################## Y O U   W R I T E / F I L L   I N   T H E S E   M E T H O D S ########################
	
	// TACK ON NEW NODE AT END OF LIST
	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);
		if (head==null)
		{
			newNode.next=newNode;
			newNode.prev=newNode;
			head = newNode;
			return;
		}
		
		CDLL_Node<T> curr;
		curr = head.prev;
		
		curr.next = newNode;
		head.prev = newNode;
		
		newNode.next = head;
		newNode.prev = curr;
	}

	
	public int size()
	{	
		count = 1; //since it wont count the last node
		CDLL_Node<T> curr = head;
		
		if(head == null)
		{
			return 0;
		}
		
		while(curr != head.prev)
        {
			count++;
			curr = curr.next;
		}
		
		return count;
	}
	
	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{	
		CDLL_Node<T> curr = head;
		CDLL_Node<T> result = null;

		while(curr != head.prev)
		{
			if(key.equals(curr.data))
			{
				result = curr;
				break;
			}
			curr = curr.next;
		}

		if(key.equals(curr.data))
		{
			result = curr;
		}

		return result;
	}
	
	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		String str = "";
		CDLL_Node<T> curr = head;
		
		while(curr != head.prev)
		{
			str = str + curr.data;
			curr = curr.next;
			
			str = str + "<=>";
		}
		
		str = str + curr.data;
		
		return str;
		
	}
	
	void removeNode(CDLL_Node<T> deadNode)
	{
		deadNode.prev.next = deadNode.next;
		deadNode.next.prev = deadNode.prev;
	}
	
	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		System.out.println("executing ritual");
		if (size() < 1 )
		{
			return;
		}
		
		CDLL_Node<T> curr = search(first2Bdeleted);
		if (curr==null)
		{
			 return;
		}
		
		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			Scanner kbd = new Scanner(System.in);
			CDLL_Node<T> deadNode = curr;
			T deadName = deadNode.data;
			
			System.out.println("stopping on " + deadName + " to delete " + deadName);
			
			
			if(curr == head)
			{
				if(skipCount > 0) //if positive then going clockwise which means we do .next
				{
					head = curr.next;			
				}else
				{
					head = curr.prev;
				}	
			}
			
			removeNode(deadNode);
			// remove the deadNode
			
			
			// now you gotta worry about what if head was pointinng to the same node as DeadNode pointed to?
			// if it was them make head point to whicher node you are about to make curr point to
		
			// adjust head to point to either deadNodes prev or deadNodes next ( hint are you CLOCKWISE or COUNTER )
			// you HAVE/MUST do this now cuase you are about to print the list. If you don't - list is FUBAR!!
			System.out.println("deleted. list now: " + this.toString());
			
			// if the list size has reached 1 break out of this loop YOU ARE DONE 
			
			if(this.size() == 1)
			{
				break;
			}
			
			String direction = "";
			// othewise make curr point to same thing you just made head point to so you can resume the ritual
			if(skipCount > 0)
			{
				direction = "CLOCKWISE";
			}else{
				direction = "COUNTER-CLOCKWISE";
			}

			// write loop that advance curr skipCount times (be sure of CLOCKWISE or COUNTER)

			if(skipCount > 0)
			{
				System.out.println("resuming at " + curr.next.data + " skipping " + curr.next.data   + skipCount + " nodes " + direction + " after");
			}else{
				System.out.println("resuming at " + curr.prev.data + " skipping " + curr.prev.data   + skipCount + " nodes " + direction + " after");
			}
		

			if(skipCount > 0) //if positive then going clockwise which means we do .next
			{
				for(int i = skipCount; i >= 0; i--)
				{
					curr =  curr.next;
				}

			}else
			{	
				for(int i = skipCount; i <= 0; i++)
				{
					curr = curr.prev;
				}			
			}	

			//String junk = kbd.nextLine();  //<= MIGHT FIND THis HELPFUL. FOR DEBUGGING. WAITS FOR YOU TO HIT RETUN KEY
			
		}
		while (size() > 1 );

	}
	
} // END CDLL_LIST CLASS
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