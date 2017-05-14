package relogioBerkeley;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.Scanner;

public class Usuario {	
	public static void main(String [] args) throws IOException, InterruptedException{
		int port;
                InetAddress ip;
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		MulticastSocket mSocket = new MulticastSocket(3333);
		String group = "224.0.0.2";		
		mSocket.joinGroup(InetAddress.getByName(group));
                DatagramSocket socket = new DatagramSocket();
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
                String[] horaPorta = resposta.split("-");
                Date serverDate = new Date(Long.parseLong(horaPorta[0]));
                long serverLong = Long.parseLong(horaPorta[0]);
                port = Integer.parseInt(horaPorta[1]);
                long diferenca =horaparam.getTime() - serverLong;
                String mensagem = Long.toString(diferenca);
                sendData = mensagem.getBytes();
		DatagramPacket pacote = new DatagramPacket(sendData, sendData.length,receivePacket.getAddress() , port);
                socket.send(pacote);
                socket.receive(receivePacket);
                resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                
                long acertar = Long.parseLong(resposta);
                long horaFinal = horaparam.getTime() + acertar;
                Date horaFinalData = new Date(horaFinal);
                System.out.println("Hora do cliente antes de atualizar: "+horaparam);
                System.out.println("Hora atualizada do cliente"+horaFinalData);
                
	}	
	
}