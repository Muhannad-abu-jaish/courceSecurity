import java.util.Scanner;

public class Starter {

    public static void main(String[] args)
    {
        Scanner myInput = new Scanner(System.in) ;

        Thread firstThread = new Thread()
        {
            @Override
            public void run() {

                while (true)
                {
                    System.out.println("Thread is active1");

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
       // firstThread.start();


        if (myInput.next().equals("Server"))
        {
            //Server code
            Server server = new Server();
        }

        else
        {
            //Client code
            Client client = new Client();
        }

    }
}
