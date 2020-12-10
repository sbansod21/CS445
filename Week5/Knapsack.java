import java.io.*;

public class Knapsack
{
    public static void main(String[] args) throws Exception
    {
		if(args.length < 1)
		{
            System.out.println("Enter file name.");
            System.exit(0);
		}

		BufferedReader infile = new BufferedReader(new FileReader(args[0]));
		int[] set = new int[16];
		String count = infile.readLine(); //only reads 1 line
		
		//knapsack
		 
        int b = 0;
        for(String word : count.split(" "))
        {
            set[b] = Integer.parseInt(word);
            b++;
        }

		int target = Integer.parseInt(infile.readLine());//reading the target
		infile.close();//closing file

		System.out.println(count);
		System.out.println(target);

		for(int i = 1; i <= -1 >>> 16; i++)
		{
			int sum = 0;
			String setString = "";
			for(int j = 0; j <= 16; j++)
			{
				if( ((i>>>j) % 2) == 1)
				{
					sum+=set[j];
					setString += (set[j] + " ");
				}
			}//end of inner for

			if(sum == target)
			{
				System.out.println(setString);
			}
		}//end of outer for
    }//end of the main


}//end of class