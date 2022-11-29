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
    Boolean flag_release_ticket = false;
    Boolean flag_book_tickets = false;
    
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
                
                //System.out.println("Recieved data <" + clientCommand + "> from client : " 
                 //                   + socketConnection.getRemoteSocketAddress().toString());

                /***************
                         Your DB code goes here
                ****************/
                StringTokenizer tokenizer = new StringTokenizer(clientCommand);
                queryInput = tokenizer.nextToken();
                try
                {
                   // Thread.sleep(6000);
                    System.out.println("Executing SQL query....");
                    stmt = dbConnection.createStatement();
                    if(queryInput.length()>=4){
                        if(flag_release_ticket==false){
                            String createfunction = "CREATE OR REPLACE FUNCTION release_train( train_id_curr INT, calender_date_curr DATE, num_AC_coach INT, num_Sleeper_coach INT) RETURNS void AS $$ DECLARE Current_variable INTEGER := 1; Seats INTEGER := 0; AC_availabe INTEGER := num_AC_coach*18; Sleeper_availabe INTEGER := num_Sleeper_coach*24; Name_ac varchar := 'ac_seats'||train_id_curr || calender_date_curr; Name_sleeper varchar := 'sleeper_seats'||train_id_curr || calender_date_curr; BEGIN EXECUTE format('CREATE TABLE IF NOT EXISTS %I (train_id INT , calender_date DATE , current_seat INT, current_coach INT,seats_available INT,seats_booked INT,PRIMARY KEY(train_id,calender_date))',Name_ac); EXECUTE format('CREATE TABLE IF NOT EXISTS %I (train_id INT , calender_date DATE , current_seat INT, current_coach INT,seats_available INT,seats_booked INT,PRIMARY KEY(train_id,calender_date))',Name_sleeper); EXECUTE 'INSERT INTO ' || quote_ident( Name_ac )||'VALUES (' || train_id_curr ||',$1,'|| Current_variable||','||Current_variable||','||AC_availabe||','||Seats||')' using calender_date_curr; EXECUTE 'INSERT INTO ' || quote_ident( Name_sleeper )||'VALUES (' || train_id_curr ||',$1,'|| Current_variable||','||Current_variable||','||Sleeper_availabe||','||Seats||')' using calender_date_curr;END; $$ LANGUAGE plpgsql;";
                            stmt.execute(createfunction);
                            flag_release_ticket = true;
                        }
                        String runfunction = "{ call release_train(? , ? , ?, ? ) }";
                        String[] words=clientCommand.split("\\s");
                        
                        for(String w:words){
                            System.out.println(w);
                        }
                        CallableStatement callstmnt = dbConnection.prepareCall(runfunction);
                        
                        callstmnt.setInt(1, Integer.parseInt(words[0]));
                         
                        callstmnt.setDate(2, Date.valueOf(words[1]));
                        callstmnt.setInt(3, Integer.parseInt(words[2]));
                        callstmnt.setInt(4, Integer.parseInt(words[3]));
                        
                        
                        callstmnt.execute();
                        
                        
                        printWriter.println("Train released");



                    }
                    else{
                        if(flag_book_tickets){
                            String createfunction = "CREATE OR REPLACE FUNCTION book_tickets(IN num_passengers INTEGER, IN name_passengers VARCHAR, IN train_id INTEGER, IN doj DATE, IN class VARCHAR) returns text as $$ declare ac_seats_availability VARCHAR:='ac_seats'||train_id||doj; sleeper_seats_availability VARCHAR:='sleeper_seats'||train_id||doj; coach_no INT; berth_no INT; available_seats INT; seats_booked INT; pnr VARCHAR; output text default ''; begin IF class='AC' THEN SELECT curr_seat, curr_coach, seats_available, seats_booked INTO berth_no, coach_no, available_seats, seats_booked FROM ac_seats_availability; IF available_seats>=num_passengers THEN  UPDATE ac_seats_availability SET seats_available=seats_available-num_passengers; UPDATE ac_seats_availability SET seats_booked=seats_booked+num_passengers; UPDATE ac_seats_availability SET curr_seat=seats_booked%18; UPDATE ac_seats_availability SET curr_coach=seats_booked/18; pnr:=train_id||'_'||doj||'_'||class||'_'||coach_no||'_'||berth_no; coach_no:=coach_no+1; berth_no:=berth_no+1; INSERT INTO ticket_details(pnr, train_id, doj, class, num_passengers, coach_no, berth_no); END IF; END IF; IF class='SL' THEN SELECT curr_seat, curr_coach, seats_available, seats_booked INTO berth_no, coach_no, available_seats, seats_booked FROM sleeper_seats_availability; IF available_seats>=num_passengers THEN  UPDATE sleeper_seats_availability SET seats_available=seats_available-num_passengers; UPDATE sleeper_seats_availability SET seats_booked=seats_booked+num_passengers; UPDATE sleeper_seats_availability SET curr_seat=seats_booked%24; UPDATE sleeper_seats_availability SET curr_coach=seats_booked/24; pnr:=train_id||'_'||doj||'_'||class||'_'||coach_no||'_'||berth_no; coach_no:=coach_no+1;berth_no:=berth_no+1; INSERT INTO ticket_details(pnr, train_id, doj, class, num_passengers, coach_no, berth_no); END IF; END IF; RETURN output; end; $$ language plpgsql;";
                            stmt.execute(createfunction);
                            flag_book_tickets = true;
                        }
                        String runfunction = "{ ? = call book_tickets(? , ? , ?, ? ,?) }";
                        String[] words=clientCommand.split("\\s");
                        
                        for(String w:words){
                            System.out.println(w);
                        }
                        CallableStatement callstmnt = dbConnection.prepareCall(runfunction);
                        callstmnt.registerOutParameter(1, Types.VARCHAR);
                        callstmnt.setInt(2, Integer.parseInt(words[0]));
                        callstmnt.setString(3, words[1]);
                        callstmnt.setInt(4, Integer.parseInt(words[3]));
                        callstmnt.setDate(5, Date.valueOf(words[4]));
                        callstmnt.setInt(6, Integer.parseInt(words[5]));
                        
                        
                        callstmnt.execute();
                        
                        
                        printWriter.println("Tickets booked");
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
                printWriter.println(responseQuery);
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
public class newServiceModule 
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
    static final String PASSWORD = "Guddu@2022";
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