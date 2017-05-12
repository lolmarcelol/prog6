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
                long t0ms = antes.getTime();
                System.out.println("T0:"+T0int);

                // ----
		mSocket.send(sendPacket);
                mSocket.receive(receivePacket);
                // hora de recebimento T1
                Date depois = new java.util.Date();
                formata = new SimpleDateFormat(formato);
                String T1 = formata.format(depois);
                int T1int = Integer.parseInt(T1);
                System.out.println("T1: "+T1int);
                long t1ms = depois.getTime();
                // ----
                
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength());
                System.out.println("Recebido do server: "+resposta);

                String[] horaPorta = resposta.split("-");
                Long hora = Long.parseLong(horaPorta[0]); // usuario
                String i = horaPorta[1];
                long ilong = Long.parseLong(i);
                System.out.println("Hora: "+hora);
                System.out.println("iLon: "+ilong);
                long d = (t1ms-t0ms -ilong)/2;
                System.out.println("D; "+d);
                long horafinal = hora - d;
                Date datefinal=new Date(horafinal);
                System.out.println("Data antiga: "+horaparam);
                System.out.println("A hora atualizada é: "+datefinal);
	}	
	
}