import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    //a thread for each client

    //the class handle the received message
    //and send the messages to the same client
    Socket client ;
    DataInputStream inputStream ;
    DataOutputStream outputStream ;

    ArrayList<String> receivedMessages = new ArrayList<>();

    ClientHandler(Socket clientSocket)
    {
        client = clientSocket ;

        try {
            inputStream = new DataInputStream(client.getInputStream());
            outputStream = new DataOutputStream(client.getOutputStream());
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            //read from the client
            String message ;
            while (true)
            {
                message = inputStream.readUTF() ;
                synchronized (receivedMessages)
                {
                    //تعني الانتظار حتى الانتهاء من التعامل من المصفوفة في حال كان أحد يستخدمها لأننا نتعامل مع threads
                    receivedMessages.add(message);
                }
            }
        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try {
            if (outputStream!=null)
         outputStream.close();

        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try {
            if (inputStream!=null)
            inputStream.close();

        }catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try {
            if (client!=null)
            client.close();

        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }


    public void sendMessage(String message)
    {
        try {


                outputStream.writeUTF(message);


        }catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public ArrayList<String> getReceivedMessages()
    {
        return receivedMessages;
    }
}
