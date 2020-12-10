import java.io.*;
import java.util.*;

public class L1_SetOps
{
	public static void main( String args[] ) throws Exception
	{
		BufferedReader infile1 = new BufferedReader( new FileReader( args[0] ) );
		BufferedReader infile2 = new BufferedReader( new FileReader( args[1] ) );

		String[] set1 = loadSet( infile1 );
		Arrays.sort( set1 );
		String[] set2 = loadSet( infile2 );
		Arrays.sort( set2 );
		printSet( "set1: ",set1 );
		printSet( "set2: ",set2 );

		String[] union = union( set1, set2 );
		Arrays.sort( union );
		printSet( "\nunion: ", union );


		String[] intersection = intersection( set1, set2 );
		Arrays.sort( intersection );
		printSet( "\nintersection: ",intersection );

		String[] difference = difference( set1, set2 );
		Arrays.sort( difference );
		printSet( "\ndifference: ",difference );

		String[] xor = xor( set1, set2 );
		Arrays.sort( xor );
		printSet("\nxor: ", xor );

		System.out.println( "\nSets Echoed after operations.");

		printSet( "set1: ", set1 );
		printSet( "set2: ", set2 );

	}// END MAIN

	// USE AS GIVEN - DO NOT MODIFY
	// CAVEAT: This method will not work *correctly* until you write a working doubleLength() method.

	static String[] loadSet( BufferedReader infile ) throws Exception
	{
		final int INITIAL_LENGTH = 5;
		int count=0;
		String[] set = new String[INITIAL_LENGTH];
		while( infile.ready() )
		{
				if (count >= set.length)
					set = doubleLength( set );
				set[ count++ ] = infile.readLine();
		}
		infile.close();
		return trimArray( set, count );
	}

	// USE AS GIVEN - DO NOT MODIFY
	static void printSet( String caption, String [] set )
	{
		System.out.print( caption );
		for ( String s : set )
			System.out.print( s + " " );
		System.out.println();
	}


	/* ###############################################################
		For each of the following set operations you must execute the following steps:
		1) dimension an array that is just big enough to handle the largest possible set for that operation.
		2) add the appropriate elements to the array as the operation prescribes.
		3) before returning the array, resize it to the exact size as the number of elements in it.
	*/


	
	static String[] union( String[] set1, String[] set2 )
	{

		int length = set1.length + set2.length;	

		String[] unionSet = Arrays.copyOf(set1,length);

		int place = set1.length;
        
        for(int i = 0; i < set2.length; i++)
        {
        	if(!contains(set1,set2[i])) //if the union set does NOT contian elem from set 2
        	{
        		unionSet[place++] = set2[i];
        	}
        }

		return trimArray(unionSet, place);
	}


	static String[] intersection( String[] set1, String[] set2)
	{
		 String intSet[] = new String[Math.min(set1.length, set2.length)]; 
               
        int i = 0;
        
        for(String elem : set1) 
        { 
            if(contains(set2, elem)) 
            { 
                intSet[i++] = elem; 
            }
        }
        
        return trimArray(intSet, i);
    }

	static String[] difference( String[] set1, String[] set2 )
	{

		//all the things in A minus the things is B
		int length = set1.length;

		String[] diffSet = new String[length];

		int i = 0;
		for(String elem : set1)
		{
			 if(!contains(set2,elem)) 
			 {
                diffSet[i++] = elem; 
            }
        }
		return trimArray(diffSet,i); // change this to return a trimmed full array
	}

	static String[] xor( String[] set1, String[] set2 )
	{
		//union minus the intersection
		//for size i think i will do the size of the set thats the smallest
	
		return difference(union(set1, set2), intersection(set1,set2)); // change this to return a trimmed full array
	}

	// return an array of length 2x with all data from the old array stored in the new array
	static String[] doubleLength( String[] old )
	{
		int length = old.length;

		String[] newSet = Arrays.copyOf(old, length*2);

	
		return newSet; // you change accordingly
	}

	// return an array of length==count with all data from the old array stored in the new array
	static String[] trimArray( String[] old, int count )
	{
		String[] newArray = Arrays.copyOf(old, count);
		

		return newArray; // you change accordingly
	}

	static boolean contains(String[] array, String search)
	{
		for(int i = 0; i < array.length; i++)
		{
			if(array[i].equals(search))
				{
					return true;
				}
		}

		return false;
	}

} // END CLASS
