import java.io.*;
import java.net.*;
import java.util.Arrays;

// argv[0] = host address
// argv[1] = port
// defaults to localhost:4413
//this client was built by using the example from class, shown from ORACLe (ECHOCLIENTOBJECT)
//no compilation erros on java 21.0.12.1 2026-08-18 LTS

public class Client {

    public static void main(String argv[]) throws Exception {

        int port;
        String host;

        //
        if (argv.length == 2) {
            host = argv[0];
            port = Integer.parseInt(argv[1]);
        }

        //if 1 argument was passed find which one and default the other
        else if (argv.length == 1) {
            try {
                port = Integer.parseInt(argv[0]);
                host = "localhost";
            }
            catch (NumberFormatException e) {
                host = argv[0];
                port = 4413;
            }
        }

        // no arguments
        else {
            host = "localhost";
            port = 4413;
        }

        // keyboard input
        BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));

        //connect to socket
        Socket clientSocket = new Socket(host, port);

        //to send strings
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);

        

        // object input from server
        ObjectInputStream objectIn =new ObjectInputStream(clientSocket.getInputStream());

        String sentence;

        while ((sentence = inFromUser.readLine()) != null) {

            //send input to server
            outToServer.println(sentence);

            

            //if we sent the array message
            if (sentence.startsWith("array ")) {

                
                String response = (String) objectIn.readObject();

                System.out.println("FROM SERVER: " + response );

                //cast result object
                Object responseObject = objectIn.readObject();

                //if the user entered a wrongly formatted array
                if (responseObject instanceof String errorMessage) {
                    System.out.println("FROM SERVER: " + errorMessage);
                    continue; 
                }

                SumMaxResult result = (SumMaxResult) responseObject;

                //print result objects fields
                System.out.println(Arrays.toString(result.getArr())
                );

                System.out.println(
                "sum: " + result.getSum() );

                System.out.println( "max: " + result.getMax());

                System.out.println(result.getDate() );

                //PDF says array is the final line so break
                
                break;
            }

            //normal string message
            else {

                String response =(String)objectIn.readObject();

                System.out.println("FROM SERVER: " + response);
            }
        }

        objectIn.close();
        
        outToServer.close();
        clientSocket.close();
    }
}