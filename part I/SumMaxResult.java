
// class to store reusults
//please contact if u have any compilation issues, 
//this file was confirmed to be working on java 21.0.12.1 2026-08-18 LTS
import java.io.Serializable;
import java.util.Date;

public class SumMaxResult implements Serializable  {



	private int arr[]; // the array operated on
    private int max;   // max value
	private int sum;   // sum value
	private Date date;  // result created date/time

    public SumMaxResult(int[]arr,int sum,int max,Date date){
		this.arr=arr;
		this.max =max;
		this.sum=sum;
		this.date = date;
	}

	public int[] getArr() {
		return arr;
	}

	public int getMax() {
		return max;
	}

	public int getSum() {
		return sum;
	}

	public Date getDate() {
		return date;
	}
	
	
}
		