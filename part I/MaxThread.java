
//please contact if u have any compilation issues, 
//this file was confirmed to be working on java 21.0.12.1 2026-08-18 LTS
public class MaxThread extends Thread{ //.. implements or extends someting
    private String name;  // name of the thread 
	private int low, hi;   // range of array  from lo to hi, [lo, hi) include low but does not includ hi
    private int[] arr;   // array to be searched
    private int ans = Integer.MIN_VALUE;  // store results, MIN VALUE ALLOWS NEGATIVE ARRAYS TO WORK PROPERLY

    public MaxThread(String name,int low,int hi,int[]arr){
        this.name=name;
        this.low=low; this.hi=hi;
        this.arr=arr;
    }

    
    public void run() {
        //....
		for(int i=this.low;i<this.hi;i++){
            if(arr[i]>ans){
                ans= arr[i];
            }
        }	 
		System.out.println(this.name + " finish");
    }

 public int getAns() {
    return ans;
}
 }