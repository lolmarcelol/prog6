package relogioBerkeley;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class TimeServer {

	public static void main(String [] args) throws IOException, InterruptedException{
            MulticastSocket multiSocket = new MulticastSocket();
            Date horasServer;
            byte[] send = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            String group = "224.0.0.2";
            int port;
            DatagramSocket socket = new DatagramSocket(44444);
            socket.setSoTimeout(10000);
            port = 44444;
            Date agora = new java.util.Date();
            String mensagem = agora.getTime() +"-"+ Integer.toString(port);

            send = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
            multiSocket.send(pacote);
            //Thread.sleep(5000);
            long soma = 0;
            long count = 1;
            long aux = 0;
            Map<Integer, String> mapa = new HashMap<Integer, String>();
            while(true){
                try{
                    
                    socket.receive(receivePacket);
                    count ++;
                    String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),
                                receivePacket.getLength());
                    soma =+Long.parseLong(resposta);
                    mapa.put(receivePacket.getPort(),resposta);

                }catch(Exception ex){
                    long media =soma/count;
                    for(Map.Entry<Integer,String> entry : mapa.entrySet()){
                        aux = Long.parseLong(entry.getValue()) - media;
                        aux = aux*-1;
                        System.out.println("Resposta a ser dada ao cliente"+count+": "+TimeUnit.MILLISECONDS.toMinutes(aux)+" Minutos");
                        System.out.println("soma: "+TimeUnit.MILLISECONDS.toMinutes(soma)+" Minutos");
                        System.out.println("numero de usuario: "+count+"\n");
                        mensagem = Long.toString(aux);
                        send = mensagem.getBytes();
                        pacote = new DatagramPacket(send, send.length,receivePacket.getAddress() , entry.getKey());
                        socket.send(pacote);
                    }
                long agoraLong = agora.getTime();
                long horasLong = agoraLong + aux;
                horasServer = new Date(horasLong);
                break;
                }

            }	
            System.out.println("Horario do servidor: "+agora);
            System.out.println("Horario após sincronização: "+ horasServer);
    }
	
	
}