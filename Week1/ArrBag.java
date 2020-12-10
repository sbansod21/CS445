// this file formatted by JFormat - jlbalder@netscape.net
//  STARTER FILE FOR ARRBAG PROJECT #2

import java.io.*;
import java.util.*;

public class ArrBag<T>
{
    final int NOT_FOUND = -1;
    final int INITIAL_CAPACITY = 1;
    private int count; // LOGICAL SIZE
    
    // DEFINE & INITIALIZE REF TO OUR ARRAY OF T OBJECTS
    @SuppressWarnings("unchecked") // SUPRESSION APPLIES TO THE NEXT LINE OF CODE
    T[] theArray = (T[]) new Object[INITIAL_CAPACITY]; // CASTING TO T[] (creates warning we have to suppress)
    
    // DEFAULT C'TOR
    public ArrBag()
    {
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    
    // SPECIFIC INITIAL LENGTH VERSION OF CONSTRUCTOR
    // only the union,intersect,diff call this version of constructor
    public ArrBag( int optionalCapacity )
    {
        @SuppressWarnings("unchecked") // SUPRESSION APPLIES TO THE NEXT LINE OF CODE
        T[] theArray = (T[]) new Object[optionalCapacity ]; // CASTING TO T[] (creates warning we have to suppress
        count = 0; // i.e. logical size, actual number of elems in the array
    }
    // C'TOR LOADS FROM FILE Of STRINGS
    @SuppressWarnings("unchecked")
    public ArrBag( String infileName ) throws Exception
    {
        count = 0; // i.e. logical size, actual number of elems in the array
        BufferedReader infile = new BufferedReader( new FileReader(  infileName ) );
        while ( infile.ready() )
        {
            String line = infile.readLine();
            this.add( (T) line );
            infile.close();
        }
        
    }
    
    // APPENDS NEW ELEM AT END OF LOGICAL ARRAY. UPSIZES FIRST IF NEEDED
    public boolean add( T element )
    {
        // THIS IS AN APPEND TO THE LOGICAL END OF THE ARRAY AT INDEX count
        if (size() == theArray.length) upSize(); // DOUBLES PHYSICAL CAPACITY
        theArray[ count++] = element; // ADD IS THE "setter"
        return true; // success. it was added
    }
    
    // INCR EVERY SUCESSFULL ADD, DECR EVERY SUCESSFUL REMOVE
    public int size()
    {
        return count;
    }
    
    // RETURNS TRUE IF THERE ARE NO ELEMENTS IN THE ARRAY, OTHERWISE FALSE
    public boolean isEmpty()
    {
        return count==0;
    }
    
    public T get( int index )
    {
        if ( index < 0 || index >=size() )
        die("FATAL ERROR IN .get() method. Index to uninitialized element or out of array bounds. Bogus index was: " + index + "\n" );
        return theArray[index];
    }
    
    // SEARCHES FOR THE KEY. TRUE IF FOUND, OTHERWISE false
    public boolean contains( T key )
    {
        if (key == null) return false;
        for ( int i=0 ; i < size() ; ++i )
        if ( get(i).equals( key ) ) // WE ARE MAKING AN ASSUMPTION ABOUT TYPE T... WHAT IS IT?
        return true;
        return false;
    }
    
    void die( String errMsg )
    {
        System.out.println( errMsg );
        System.exit(0);
    }
    
    // --------------------------------------------------------------------------------------------
    // # # # # # # # # # # #   Y O U   W R I T E   T H E S E   M E T H O D S  # # # # # # # # # # #
    // --------------------------------------------------------------------------------------------
    
    // PERFORMS LOGICAL (SHALLOW) REMOVE OF ALL THE ELEMENTS IN THE ARRAY (SIMPLE 1 LINER!)
    public void clear()
    {
        //just clear the cunt and everytinng will be ERASED
        count=0;
    }
    
    // DOUBLE THE SIZE OF OUR ARRAY AND COPY ALL THE ELEMS INTO THE NEW ARRAY
    @SuppressWarnings("unchecked")
    private void upSize()
    {
        //like doubles the size of the array
        //Remember this is o(n)
        T[] newArray = (T[]) new Object[theArray.length*2]; //make temp array twice the length  

        for(int i = 0; i < theArray.length; i++)
        {
           newArray[i] = theArray[i]; //copy everything out
        }

        theArray = newArray;
    }
    
    public String toString()
    {
        String str = "";

        for(int i = 0;  i < this.size(); i++)
        {
            str = str + this.get(i) + " ";
        }
        return str; // REPLACE WITH YOUR CODE. THIs JUSt LETS IT COMPILE
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS FROM BOTH BAGS
    // DOES -NOT- MODIFY THIS OR OTHER BAG
    public ArrBag<T> union( ArrBag<T> other )
    {
        ArrBag<T> unionResult = new ArrBag<T>( this.size() + other.size() ); //create a new bag the size of both of the bags combined
        
        for(int i = 0; i < this.size(); i++)  //adds the things from this list to the result 
        {
            unionResult.add(this.get(i));

            //so now the bag has all the elements of set1
        }

        for(int i = 0; i < other.size(); i++)
        {
            if(!unionResult.contains(other.get(i))) //check if a thing is in the list
            {
                unionResult.add(other.get(i)); //if it isnt then add it
            }
            
        }
        // you must declare/define an ArrBag right here just like you did with the linked List Union.
        // However when you define it for the ArrBag version of Union you must use use the alternate
        // constructor which I have added nesr the top of this file
        // You must send in the initial capacity (.length) for this array. The value you send in must be the
        // maximum possible .length that the union of the two sets could possibly be.
        // Ill give you the answer for union so you understand how you would figure it out for the other two.
        // The union of two sets could at MOST have the combined number of elements of both sets.
        // This is the value you will put into the constuctor when you declare your local result set that
        // that you will eventually return.
        
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE UNION
        
        // now do the same traversals and tests you did in the linked list union but use array indices of course.
        
        return unionResult;
    }//~public ArrBag<T> union( ...
    
    // RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS IN COMMON
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> intersection( ArrBag<T> other )
    {
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG 
        //ENOUGH FOR THE LARGEST POSSIBLE INTERSECT
        ArrBag<T> interectResult = new ArrBag<T>(Math.min(this.size(), other.size())); 
        // change the 0 to the right value for intersect

        for(int i = 0; i < other.size(); i++)
        {
            if(this.contains(this.get(i)) && other.contains(this.get(i))) 
            {//this is gonna be 1 swift motion so we check if something is in BOTH bags and then add it
                interectResult.add(this.get(i));
            }//else increment
            
        }
        return interectResult;
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // REMAINING AFTER THIS BAG - OTHER BAG
    // DOES -NOT- MODIFY THIS OR OTHER
    public ArrBag<T> difference( ArrBag<T> other )
    {
        // THIS ARRBAG WILL NEVER TRIGGER AN UPSIZE BECUASE YOU MADE IT JUST BIG ENOUGH FOR THE LARGEST POSSIBLE DIFF
        ArrBag<T> diffResult = new ArrBag<T>(this.size()); // change the 0 to the right value for diff
        
        for(int i = 0; i <this.size(); i++)
        {
            if(this.contains(this.get(i))) //checks if this has a thing
            {
                if(!other.contains(this.get(i))) //then makes sure that other doesnt have it before adding it
                {
                    diffResult.add(this.get(i));
                }
            }

        }
        return diffResult;
    }
    
    // RETURNS A THIRD ARRBAG CONTAIING ONLY ONE COPY (NO DUPES) OF ALL THE ElEMENTS
    // CONTAINED IN THE UNION OF THIS AND OTHER - INTERSECTION OF THIS AND OTHER
    // DOES -NOT- MODIFY THE THIS OR OTHER
    public ArrBag<T> xor( ArrBag<T> other )
    {
        return union(other).difference(intersection(other)); // REPLACE WITH YOUR CODE. THIs JUSt LETS IT COMPILE
    }
}// END ARRBAG CLASS
