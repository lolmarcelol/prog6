package relogio;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {

	public static void main(String [] args) throws IOException, InterruptedException{
		byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                DatagramSocket socket = new DatagramSocket(9876);
                InetAddress IPAddress = InetAddress.getByName("localhost");
                socket.setSoTimeout(100000);
                String formato = "SSS";
                String formatoFull = "HH:mm:ss";
                // recebe pacote 
		socket.receive(receivePacket);
                // i0
                Date antes = new java.util.Date();
                SimpleDateFormat formata = new SimpleDateFormat(formato);
                String i0 = formata.format(antes);
                
                // data agora
		Date agora = new java.util.Date();
                SimpleDateFormat formataFull = new SimpleDateFormat(formatoFull);
                String hora = formataFull.format(agora);
                
                //i1
                Date depois = new java.util.Date();
                formata = new SimpleDateFormat(formato);
                String i1 = formata.format(depois);
                
                int i = Integer.parseInt(i1) - Integer.parseInt(i0);
                String mensagem = hora +"-"+ i1;
                System.out.println(mensagem);
                byte[] sendData = new byte[1024];

                sendData = mensagem.getBytes();
                System.out.println(sendData);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,receivePacket.getPort());
                socket.send(sendPacket);
	}
	
	
}