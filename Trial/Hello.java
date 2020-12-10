import java.io.*;
import java.util.*;

public class Hello
{
	public static void main( String[] args)
	{
		String[] contents = new String[2];

		contents[0] = "Hello";
		contents[1] = "world";

		String[] suss = new String[3];

		suss[0] = "Hello";
		suss[1] = "Sush";
		suss[2] = "you bitch";


		System.out.println(Arrays.toString(contents));
		System.out.println(Arrays.toString(suss));

		int length = contents.length + suss.length;

		String[] unionSet;

		unionSet = Arrays.copyOf(contents, length);

		int place = contents.length;

		for(int i = 0; i < length; i ++)
		{
			if(contains(unionSet, suss[i]))
			{
				System.out.println("contians Hello");
				//unionSet[place] = suss[i];
				//place++;
			}
		}

		System.out.println(Arrays.toString(unionSet));
	}

public static boolean contains(String[] Set, String element)
{
	for(int i = 0; i < Set.length; i++)
	{
		if(Set[i] == element)
		{
			return true;
		}
	}

	return false;
}

}