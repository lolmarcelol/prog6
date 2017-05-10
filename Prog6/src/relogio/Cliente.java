package relogio;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente {	
	public static void main(String [] args) throws IOException, InterruptedException{
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
                InetAddress IPAddress = InetAddress.getByName("localhost");
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		DatagramSocket mSocket = new DatagramSocket();
                
                Scanner sc = new Scanner(System.in);
                System.out.println("Digite uma data formato hh:mm");
                String horaparam = sc.next();
                sendData = horaparam.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                // hora de envio T0
                String formato = "HHmmssSSS";
                Date antes = new java.util.Date();
                SimpleDateFormat formata = new SimpleDateFormat(formato);
                String T0 = formata.format(antes);
                int T0int = Integer.parseInt(T0);

                // ----
		mSocket.send(sendPacket);
                System.out.println("enviou");
                mSocket.receive(receivePacket);
                System.out.println("recebeu");
                // hora de recebimento T1
                Date depois = new java.util.Date();
                formata = new SimpleDateFormat(formato);
                String T1 = formata.format(depois);
                int T1int = Integer.parseInt(T1);
                // ----
                
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength());
                System.out.println("Recebido do server: "+resposta);

                String[] horaPorta = resposta.split("-");
                String hora = horaPorta[0]; // usuario
                String i = horaPorta[1];
                int iint = Integer.parseInt(i);
                System.out.println("I: "+i);
                System.out.println("Hora: "+hora);
                System.out.println(T0int);
                System.out.println(T1int);
                System.out.println("iInt: "+iint);
                int d = (T1int-T0int -iint)/2;
                System.out.println("D; "+d);
	}	
	
}