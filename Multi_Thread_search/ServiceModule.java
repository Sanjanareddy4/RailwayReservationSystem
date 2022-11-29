import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.*;
import java.text.*;

class QueryRunner implements Runnable
{
    //  Declare socket for client access
    protected Socket socketConnection;
    Connection dbConnection;
    public QueryRunner(Socket clientSocket, Connection dbConnection)
    {
        this.socketConnection =  clientSocket;
        this.dbConnection = dbConnection;
    }

    public void run()
    {
      try
        {
            //  Reading data from client
            InputStreamReader inputStream = new InputStreamReader(socketConnection
                                                                  .getInputStream()) ;
            BufferedReader bufferedInput = new BufferedReader(inputStream) ;
            OutputStreamWriter outputStream = new OutputStreamWriter(socketConnection
                                                                     .getOutputStream()) ;
            BufferedWriter bufferedOutput = new BufferedWriter(outputStream) ;
            PrintWriter printWriter = new PrintWriter(bufferedOutput, true) ;

            Statement stmt = null;

            String clientCommand = "" ;
            String responseQuery = "" ;
            String queryInput = "" ;
            Boolean flag = false;
            // Read client query from the socket endpoint
            clientCommand = bufferedInput.readLine(); 
            while( ! clientCommand.equals("#"))
            {
                
                

                /***************
                         Your DB code goes here
                ****************/
                StringTokenizer tokenizer = new StringTokenizer(clientCommand);
                queryInput = tokenizer.nextToken();
                try
                {
                   // Thread.sleep(6000);
                    
                    
                   stmt = dbConnection.createStatement();
                   String runfunction = "{ ?=call search(? , ?  ) }";
                   String[] words=clientCommand.split(",");
                   
                   
                   CallableStatement callstmnt = dbConnection.prepareCall(runfunction);
                  
                   callstmnt.registerOutParameter(1, Types.VARCHAR);
                   callstmnt.setString(2, words[0]);
                    
                   callstmnt.setString(3, words[1]);
                   
                   callstmnt.execute();
                   String res = callstmnt.getString(1);
                   
                   String[] finalOutput = res.split("\\?");

                    for(int i =0;i<finalOutput.length;i++){
                        printWriter.println(finalOutput[i]);
                    }


                   stmt.close();

                } 
                catch (Exception e)  // InterruptedException replaced by Exception 
                {
                    e.printStackTrace();
                }
                

                // Dummy response send to client
                responseQuery = "*** Dummy result **";      
                //  Sending data back to the client
                // for(int i =0;i<finalOutput.length;i++){
                //     printWriter.println(Arrays.toString(finalOutput[i]));
                // }
                // Read next client query
                clientCommand = bufferedInput.readLine(); 
            }
            inputStream.close();
            bufferedInput.close();
            outputStream.close();
            bufferedOutput.close();
            printWriter.close();
            socketConnection.close();
        }
        catch(IOException e)
        {
            return;
        }
    }
}

/**
 * Main Class to controll the program flow
 */
public class ServiceModule 
{
    // Server listens to port
    static int serverPort = 7008;
    // Max no of parallel requests the server can process
    static int numServerCores = 5 ;         
    //------------ Main----------------------
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost/searchportal";

    // Databse Credentials
    static final String USER = "postgres";
    static final String PASSWORD = "2214";
    public static void main(String[] args) throws IOException 
    {    
        // Creating a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(numServerCores);
        
        try (//Creating a server socket to listen for clients
        ServerSocket serverSocket = new ServerSocket(serverPort)) {
            Socket socketConnection = null;
            Connection dbConnection = null;
            // Always-ON server
            while(true)
            {
                System.out.println("Listening port : " + serverPort 
                                    + "\nWaiting for clients...");

                try{dbConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);} catch (Exception e) {
                    System.out.println(e.getClass().getName()+": "+e.getMessage());
                    return;
                }
                socketConnection = serverSocket.accept();   // Accept a connection from a client
                System.out.println("Accepted client :" 
                                    + socketConnection.getRemoteSocketAddress().toString() 
                                    + "\n");
                //  Create a runnable task
                Runnable runnableTask = new QueryRunner(socketConnection,dbConnection);
                //  Submit task for execution   
                executorService.submit(runnableTask);   
            }
        }
    }
}
