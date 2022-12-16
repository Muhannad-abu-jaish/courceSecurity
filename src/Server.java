import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    Server()
    {
        try {
            //Data type تقوم بعمل Socket من نوع استماع
            //حيث تستسمع للاتصالات وتجهز بيانات المتصل وتعيد socket فيهاالبيانات

            ServerSocket serverSocket = new ServerSocket(22000) ;
            Socket clientSocket = serverSocket.accept(); //مازال ينتظر تواصل أحد الكلاينت مع هذا البورت


            System.out.println("the first client is ready ");
            ClientHandler clientHandler = new ClientHandler(clientSocket) ;
            clientHandler.start();


             clientSocket = serverSocket.accept();
            System.out.println("the second client is ready ");
            ClientHandler clientHandler2 = new ClientHandler(clientSocket) ;
            clientHandler2.start();

            ArrayList<String> messages ;
            while (true) {
                //Received the messages from the first client(clientHandler)
                messages = clientHandler.getReceivedMessages() ;

                if (!messages.isEmpty())
                {
                    synchronized (messages)
                    {
                        for (int i = 0 ; i < messages.size() ; i++)
                        {
                            //send the messages to the other client
                            clientHandler2.sendMessage(messages.get(i));
                        }
                        messages.clear();
                    }
                }


                //Received the messages from the first client(clientHandler2)
                messages = clientHandler2.getReceivedMessages() ;
                if (!messages.isEmpty())
                {
                    synchronized (messages)
                    {
                        for (int i = 0 ; i < messages.size() ; i++)
                        {
                            //send the messages to the other client
                            clientHandler.sendMessage(messages.get(i));
                        }
                        messages.clear();
                    }
                }
                //This code for make a connection with the server
                /*System.out.println("waiting for the response ");
                Socket client = serverSocket.accept();//قبول اتصال من شخص واحد
                //يقوم هذا الكلاس باستقبال البيانات من المتصل وقراءتها بالطريقة التي أريد


                //لقراءة البيانات ممن المصدر القادمة منه
                DataInputStream clientReadSource = new DataInputStream(client.getInputStream());//قراءة ما وصل من المتصل الآخر

                //كتابة أي معلومات للطرف الآخر المتصل من خلال السوكيت
                DataOutputStream clientWriteSource = new DataOutputStream(client.getOutputStream());

                while (true)
                {

                    clientWriteSource.writeUTF("hi i am the server"); //sending message for the source
                    clientWriteSource.writeUTF("you can only ask me once");

                    String sourceResponse = clientReadSource.readUTF();// reading the message is sourcing
                    System.out.println("Client said: " + sourceResponse);
                    if (sourceResponse.equalsIgnoreCase("exit"))
                    {
                        break;
                    }
                    clientWriteSource.writeUTF("we have no service right now, good");

                }
                clientWriteSource.close();
                clientReadSource.close();
                client.close();*/

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//End of while loop
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
