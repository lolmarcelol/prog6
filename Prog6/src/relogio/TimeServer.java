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
                String formatoFull = "HH:mm:ss:SS";
                // recebe pacote 
		socket.receive(receivePacket);
                // i0
                Date antes = new java.util.Date();
                SimpleDateFormat formata = new SimpleDateFormat(formato);
                String i0 = formata.format(antes);
                long i0ms = antes.getTime();
                
                // data agora
		Date agora = new java.util.Date();
                SimpleDateFormat formataFull = new SimpleDateFormat(formatoFull);
                String hora = formataFull.format(agora);
                long datams = agora.getTime();
                
                //i1
                Date depois = new java.util.Date();
                formata = new SimpleDateFormat(formato);
                String i1 = formata.format(depois);
                long i1ms = depois.getTime();
                
                long i = i1ms - i0ms;
                String mensagem = datams +"-"+ i;
                System.out.println(mensagem);
                byte[] sendData = new byte[1024];

                sendData = mensagem.getBytes();
                System.out.println(sendData);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,receivePacket.getPort());
                socket.send(sendPacket);
	}
	
	
}