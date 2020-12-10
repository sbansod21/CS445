import java.io.*;
import java.util.*;

public class LinkedList<T>
{
	private Node<T> head;  // pointer to the front (first) element of the list

	public LinkedList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// COPY ALL NODES FROM OTHER LIST INTO THIS LIST. WHEN COMPLETED THIS LIST IDENTICAL TO OTHER
	public LinkedList( final LinkedList<T> other) {
		this.head = other.head; // YOU ABSOLUTLEY MUST CHANGE THIS. THIS IS A SHALLOW COPY :(
	}

	// LOAD LINKED LIST FROM INCOMING FILE
	@SuppressWarnings("unchecked")
	public LinkedList(final String fileName) {
		try {
			final BufferedReader infile = new BufferedReader(new FileReader(fileName));
			while (infile.ready()) {
				 //System.out.println((T)infile.readLine());
				 //System.out.println(size());
				insertAtTail((T) infile.readLine());
			}
			infile.close();
		} catch (final Exception e) {
			System.out.println("FATAL ERROR CAUGHT IN C'TOR: " + e);
			System.exit(0);
		}
	}

	// -------------------------------------------------------------
	// inserts new elem at front of list - pushing old elements back one place

	public void insertAtFront(final T data) {
		head = new Node<T>(data, head);
	}

	// we use toString as our print

	public String toString() {
		String toString = "";

		for (Node<T> curr = head; curr != null; curr = curr.getNext()) {
			toString += curr.getData(); // WE ASSUME OUR T TYPE HAS toString() DEFINED
			if (curr.getNext() != null)
				toString += " -> ";
		}

		return toString + "\n";
	}

	// ########################## Y O U W R I T E T H E S E M E T H O D S
	// ########################

	// TACK A NEW NODE (CABOOSE) ONTO THE END OF THE LIST
	public void insertAtTail(final T data) {
		// IF THERE ARE NO NODES IN LIST TACK THIS ONE RIGHT ONTO THE HEAD

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

		// ELSE GET A REF TO THE VERY LAST NODE AND HANG IT OFF THE LAST NODE'S NEXT REF
	}

	// OF COURSE MORE EFFICIENT TO KEEP INTERNAL COUNTER BUT YOU COMPUTE IT
	// DYNAMICALLY WITH A TRAVERSAL LOOP
	public int size() {
		if (head == null) {
			return 0;
		}

		int size = 0;
		for (Node<T> curr = head; curr != null; curr = curr.getNext()) {
			size++;
		}

		return size;
		// WALK THE LIST AND COUNT THE NODES
	}

	// MUST CALL SEARCH AND IF SEARCH RETURNS NULL, THIS METHOD RETURNS FALSE,
	// OTHERWIASE RETURN TRUE
	public boolean contains(final T key) {

		return (search(key) != null);
	}

	// TRAVERSE LIST FRONT TO BACK LOOKING FOR THIS DATA VALUE.
	// RETURN REF TO THE FIRST NODE THAT CONTAINS THIS KEY. DO -NOT- RETURN REF TO
	// KEY ISIDE NODE
	// RETURN NULL IF NOT FOUND
	public Node<T> search(final T key)
	{	
		for(Node<T> curr = head; curr != null; curr = curr.getNext())
		{
			if(key.equals(curr.getData()))
			{
				return curr;
			}
			
		}
			return null;
		
		//WALK THE LIST LOOKING FOR 1ST OCCURANCE OF NODE WITH DATA VALUE EQUAL TO KEY
	}

} //EOF