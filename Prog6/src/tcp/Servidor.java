package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int porta = 33333;
        ServerSocket servidor = new ServerSocket(porta);
        while(true){
            Socket socket = servidor.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            Double peso = in.readDouble();
            Double altura = in.readDouble();
            System.out.println(peso+" "+altura);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Double imc = (peso/(altura*altura));
            System.out.println(imc);
            output.writeDouble(imc);
        }
    }
    
}
