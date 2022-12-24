import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    static Cipher cipher;
    static String KEY = "aesEncryptionKey" ;
    static String initVector = "encryptionIntVec";
    boolean isOn;

    static String getKEY(){return KEY;}
    Client(String key) throws Exception {
         //KEY = key;
        //System.out.println("KEY : " + KEY);
        isOn = true ;
        Scanner myInput = new Scanner(System.in) ;
        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        //String encryptedText = encrypt(plainText, secretKey);
        //System.out.println("Encrypted Text After Encryption: " + encryptedText);
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
                if (serverResponse.equalsIgnoreCase("exit")) {
                    break;
                }
                otherWriteSource.writeUTF(encrypt(serverResponse));
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
    public static String encrypt(String plainText)
            throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(),"AES");
        byte[] plainTextByte = plainText.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec , ivParameterSpec);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

}
