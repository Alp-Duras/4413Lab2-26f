
//please contact if u have any compilation issues, 
//this file was confirmed to be working on java 21.0.12.1 2026-08-18 LTS
import java.io.*; 
import java.net.*;
import java.util.Arrays;
import java.util.Date; 


public class Server { 

  public static void main(String argv[]) throws Exception     { 

   //if no arguments port deafults to 4413
   int port;
   if (argv.length == 0) {
      port=4413;
   }
   else{
      port = Integer.parseInt(argv[0]);
   }
     ServerSocket welcomeSocket = new ServerSocket(port); 
   while(true){
      System.out.println("listening ... at port " + port);
      Socket socket = welcomeSocket.accept();
      //create client connection using the handler for multi client support
      ClientHandler handler = new ClientHandler(socket);
      handler.start();

   }

}
}

class ClientHandler extends Thread {

    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {

        try {

            BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()) );

            
            ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream() );

            objectOut.flush();

            String line;

            
            
            //READ INPUT
            while ((line = in.readLine()) != null) {
               try{
                  //check if the input is the array input
               if(line.startsWith("array ")){
                  //send mesasge to client
                  objectOut.writeObject("calculating ......");
                  objectOut.flush();
                  //split the string by the spaces
                  String[] arrString= line.trim().split("\\s+");
                  //map each string in the split array to ints
                  int[] intArray = Arrays.stream(arrString).skip(1).mapToInt(Integer::parseInt).toArray();
                  if (intArray.length == 0) {
                     objectOut.writeObject("invalid array input. Enter at least one integer." );
                     objectOut.flush();
                     continue;
               }
                 //find the middle
                  int mid = intArray.length/2;
                  //create the threads
                  SumThread sumT1 = new SumThread("sumT1",0 ,mid ,intArray);
                  SumThread sumT2 = new SumThread("sumT2",mid ,intArray.length ,intArray);
                  
                  MaxThread maxT1 = new MaxThread("maxT1",0,mid, intArray);
                  MaxThread maxT2 = new MaxThread("maxT2",mid,intArray.length, intArray);
                  sumT1.start();sumT2.start();
                  maxT1.start();maxT2.start();
                  //wait for threads
                  try{
                  sumT1.join();sumT2.join();
                  maxT1.join();maxT2.join();
                  
                  }catch(InterruptedException e){
                     e.printStackTrace();
                  }
                  Date d = new Date();
                  //create  result object
                  SumMaxResult result = new SumMaxResult(intArray,sumT1.getAns()+sumT2.getAns(),
                  (maxT1.getAns()>maxT2.getAns())? maxT1.getAns():maxT2.getAns(),d);
             
                  objectOut.writeObject(result);
                  objectOut.flush();
                  break; //CLOSE THE CLIENT CONNECTION AFTER SENDING THE ARRAY LIKE PDF SAID
               }
               //normal string input from client return uppercase
               else{
                  objectOut.writeObject(line.toUpperCase());
                  objectOut.flush();
               }
               
            }catch(NumberFormatException e){
              objectOut.writeObject("invalid array input. Usage: array num num ...");
               objectOut.flush();
               
            }
               
            }

            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



 

 