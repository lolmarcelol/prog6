package relogioBerkeley;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeServer {

	public static void main(String [] args) throws IOException, InterruptedException{
            MulticastSocket multiSocket = new MulticastSocket();
            byte[] send = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            String group = "224.0.0.1";
            int port;
            DatagramSocket socket = new DatagramSocket(44444);
            socket.setSoTimeout(10000);
            port = 44444;
            Date agora = new java.util.Date();
            String mensagem = agora.getTime() +"-"+ Integer.toString(port);

            send = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
            multiSocket.send(pacote);
            Thread.sleep(5000);
            long media = 0;
            long count = 0;            
            while(true){
                try{
                    socket.receive(receivePacket);
                    count ++;
                    String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),
                                receivePacket.getLength());
                    media =+Long.parseLong(resposta);
                }catch(Exception ex){
                    long aux =media/count+1;
                    mensagem = Long.toString(aux);
                    send = mensagem.getBytes();
                    pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                    // logica para enviar para cada usuario o seu horario a ser acertado, map sipa
//                    socket.send(pacote);
//                    System.out.println(mensagem);
//                    long date = TimeUnit.MILLISECONDS.toMinutes(aux);
//                    System.out.println(date);
                    break;
                }

            }			
    }
	
	
}