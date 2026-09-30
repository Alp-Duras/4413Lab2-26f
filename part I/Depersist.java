// this class retrieve the results from the disk file
//please contact if u have any compilation issues, 
//this file was confirmed to be working on java 21.0.12.1 2026-08-18 LTS

import java.io.FileInputStream;

import java.io.ObjectInputStream;

class Depersist{  
 
 public static void main(String args[]){  
   
  //  open file "result.txt" and retrieve the SumMaxResult object
  try{
    FileInputStream fileIn = new FileInputStream("result.txt");
    ObjectInputStream in = new ObjectInputStream(fileIn);
    
    SumMaxResult result = (SumMaxResult)in.readObject();
    in.close();
    fileIn.close();

    //do the printing here to avoid null errors if file readingf issues happen
    System.out.print("[");
      

    for (int i = 0; i < result.getArr().length; i++) {
        if (i > 0) {
            System.out.print(", ");
        }

        System.out.print(result.getArr()[i]);
    }

    System.out.println("]");

    System.out.println(String.format("sum: %d\nmax: %d", result.getSum(),result.getMax()));
    System.out.println(result.getDate());




  }catch (Exception e){
    e.printStackTrace();
  }
  
  //  and output the 4 attributes of the object
  
  //...
}  

}