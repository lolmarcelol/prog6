package relogioBerkeley;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Usuario {	
	public static void main(String [] args) throws IOException, InterruptedException{
		int port;
                InetAddress ip;
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                DatagramPacket sendPacket; new DatagramPacket(sendData,sendData.length);
		MulticastSocket mSocket = new MulticastSocket(3333);
		String group = "224.0.0.1";		
		mSocket.joinGroup(InetAddress.getByName(group));
                
                Scanner sc = new Scanner(System.in);
                System.out.println("Digite a hora, formato hh");
                int hour = sc.nextInt();
                System.out.println("Digite a hora, formato mm");
                int min = sc.nextInt();
                
                Date horaparam = new Date();
                horaparam.setHours(hour);
                horaparam.setMinutes(min);
                
		mSocket.receive(receivePacket);

                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),
				receivePacket.getLength());
                System.out.println(resposta);
                // aqui muda
                String[] horaPorta = resposta.split("-");
                Date serverDate = new Date(Long.parseLong(horaPorta[0]));
                long serverLong = Long.parseLong(horaPorta[0]);
                port = Integer.parseInt(horaPorta[1]);
                System.out.println(port);               
                long diferenca =horaparam.getTime() - serverLong;
                String mensagem = Long.toString(diferenca);
                sendData = mensagem.getBytes();
		DatagramPacket pacote = new DatagramPacket(sendData, sendData.length,receivePacket.getAddress() , port);
                mSocket.send(pacote);
                mSocket.receive(pacote);
                
	}	
	
}