package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) throws IOException {
        
        double altura=1.83;
        double peso=80.0;
        Double imc = 0.0;
        
        Socket cliente = new Socket("10.96.26.91", 3333);
        DataOutputStream output = new DataOutputStream(cliente.getOutputStream());
        DataInputStream input = new DataInputStream(cliente.getInputStream());
        
        output.writeDouble(altura);
        output.writeDouble(peso);
        
        imc = input.readDouble();
        System.out.println("Olá, seu Indice de massa corporal é: " +imc);
    }
    
}
