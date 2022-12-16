import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    boolean isOn;
    Client()
    {
        isOn = true ;
        Scanner myInput = new Scanner(System.in) ;

        try {
            InetAddress ip = InetAddress.getLocalHost() ;//if the client is the same laptop

            //if the ip for other client
            // InetAddress ip = InetAddress.getByName('the ip');
            System.out.println("The ip " +ip);
            Socket other = new Socket(ip , 22000); //the first parameter is my ip , the second is the server (other person is port) IP
            DataInputStream otherReadSource = new DataInputStream(other.getInputStream())  ;//قراءة ما وصل من المتصل الآخر

            //كتابة أي معلومات للطرف الآخر المتصل من خلال السوكيت
            DataOutputStream otherWriteSource = new DataOutputStream(other.getOutputStream());

            Thread clientThread = new Thread()
            {
                @Override
                public void run()
                {
                    String serverResponse = "";
                    try {

                        while (isOn)
                        {
                            serverResponse = otherReadSource.readUTF();
                            System.out.println("Other client said : " + serverResponse);
                        }

                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            };
            clientThread.start();

            String serverResponse = "";
            while (true) {

                serverResponse = myInput.nextLine() ;

                if (serverResponse.equalsIgnoreCase("exit"))
                {
                    break;
                }
                otherWriteSource.writeUTF(serverResponse);
               // serverResponse = otherReadSource.readUTF();
               // System.out.println(serverResponse);

            }
            otherWriteSource.close();
            otherReadSource.close();
            other.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
