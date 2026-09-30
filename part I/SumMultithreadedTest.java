//please contact if u have any compilation issues, 
//this file was confirmed to be working on java 21.0.12.1 2026-08-18 LTS

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class SumMultithreadedTest {
    /**
     * Get sum and max of an array.
     *
     *  
     */
   
   public static void main(String[] args) {
        int[] mynums = new int[]{7,3,5,6,8,15, 9,3,10};
		//middle of the array
		int mid = mynums.length / 2;

		// create two instance of SumThread class, give them name "sum thread A", "sum thread B", 
		// give them the aray, and the range.  One will work on the first half of the array, another will work on the second half of the array
		SumThread a = new SumThread("sum thread A",0,mid,mynums);
		SumThread b = new SumThread("sum thread B",mid,mynums.length,mynums);
		// create two instance of MaxThread class, give them name "max thread X", "max thread Y", 
		// give them the aray, and the range.  One will work on the first half of the array, another will work on the second half of the array
		MaxThread  x = new MaxThread("max thread X",0,mid,mynums);
		MaxThread  y = new MaxThread("max thread Y",mid,mynums.length,mynums);


		// starts/fire the 4 threads so they work in parallel
		a.start();
		b.start();

		x.start();
		y.start();
		//....


	    // wait for the 4 threads to finish, and retrieve the results.
		try {
		a.join();
		b.join();

		x.join();
		y.join();
		//...
		}
		catch (Exception e){
			e.printStackTrace();
		}

		int sum = a.getAns()+ b.getAns();
		int max = (x.getAns()>y.getAns()? x.getAns(): y.getAns());

	
		
		System.out.println(String.format("sum:%d max:%d", sum,max));
		Date d = new Date();
		System.out.println(d); 

		// store the result data to the instancre of SumMaxResult class
		SumMaxResult result = new SumMaxResult(mynums, sum, max, d);
		// write the object to a disk file "result.txt"
		try{
		FileOutputStream out = new FileOutputStream("result.txt");
		ObjectOutputStream objectout = new ObjectOutputStream(out);

		objectout.writeObject(result);
		objectout.close();
		out.close();
		System.out.println("Writing success");
		}catch( Exception e){
			e.printStackTrace();
		}
		 
		//...


		

		
   }	

}


 