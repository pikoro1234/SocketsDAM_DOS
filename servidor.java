import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class servidor{
    public static void main(final String[] args) {
        
        Scanner entradaEscaner = new Scanner (System.in);

        ServerSocket servidor = null;

        Socket sc = null;

        final int PUERTO= 5500;

        DataInputStream in;

        DataOutputStream out;

        try {

            servidor = new ServerSocket(PUERTO);

            System.out.println( "        ********* CHATS **********");

            System.out.println("         ++ esperando consultas ++");

            while (true) {

                sc = servidor.accept();

                in = new DataInputStream(sc.getInputStream());

                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();

                System.out.println(" ");

                System.out.println("---------------------------------");

                System.out.println("cliente dice: "+mensaje);

                System.out.print("responder: ");

                String respuesta = entradaEscaner.nextLine();

                System.out.println("escribiendo...");

                System.out.println("---------------------------------");

                System.out.println(" ");

                out.writeUTF(respuesta);

            }

        } catch (Exception e) {
            //TODO: handle exception
        }

    }
}
