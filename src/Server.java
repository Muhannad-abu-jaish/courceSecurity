import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;

public class Server {
    static Cipher cipher;
    static String KEY = "aesEncryptionKey";
    static String initVector = "encryptionIntVec";
    Server() throws Exception {
        try {
            //Data type تقوم بعمل Socket من نوع استماع
            //حيث تستسمع للاتصالات وتجهز بيانات المتصل وتعيد socket فيهاالبيانات
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
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
                //KEY1 = clientHandler.getSecretKey();

                if (!messages.isEmpty())
                {
                    synchronized (messages)
                    {
                        for (int i = 0 ; i < messages.size() ; i++)
                        {
                            //send the messages to the other client
                            clientHandler2.sendMessage(decrypt(messages.get(i)));
                        }
                        messages.clear();
                    }
                }


                //Received the messages from the first client(clientHandler2)
                messages = clientHandler2.getReceivedMessages() ;
                //KEY2 = clientHandler2.getSecretKey();
                if (!messages.isEmpty())
                {
                    synchronized (messages)
                    {
                        for (int i = 0 ; i < messages.size() ; i++)
                        {
                            //send the messages to the other client
                            clientHandler.sendMessage(decrypt(messages.get(i)));
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
                //System.out.println("KEY1 = " + KEY1 + " KEY2 = " + KEY2);
            }
            //End of while loop
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    public static String decrypt(String encryptedText)
            throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(),"AES");
        Base64.Decoder decoder = Base64.getDecoder();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec , ivParameterSpec);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
}
