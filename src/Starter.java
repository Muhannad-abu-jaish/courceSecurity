import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Starter {

    public static void main(String[] args) throws Exception {

        Scanner myInput = new Scanner(System.in) ;

        /*Thread firstThread = new Thread()
        {
            @Override
            public void run() {

                while (true)
                {
                    System.out.println("Thread is active2");

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        firstThread.interrupt();
        */// firstThread.start();


        if (myInput.next().equals("Server"))
        {
            //Server code
            Server server = new Server();
        }
        else
        {
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
                    + "~!@#$%^&*()}{>?':;/*-+=";
            StringBuilder sb = new StringBuilder(20);
            for (int i = 0; i < 20; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));

}
            String key = sb.toString();
            System.out.println("KEY : " + key);
            //Client code
            Client client = new Client(key);
        }

    }
}
