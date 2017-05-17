
package eleicao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class Eleicao {
    public static void main(String [] args) throws IOException, InterruptedException{
        boolean cordenador;
        int portaCordenador;
        InetAddress ip;
        String resposta;
        byte[] send = new byte[1024];
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        MulticastSocket multiSocket = new MulticastSocket(3333);
        String group = "224.0.0.2";		
        multiSocket.joinGroup(InetAddress.getByName(group));
        DatagramSocket socket = new DatagramSocket();
        multiSocket.setSoTimeout(10000);
        String aux = ManagementFactory.getRuntimeMXBean().getName();
        String[] idaux = aux.split("@");
        int id = Integer.parseInt(idaux[0]);
        System.out.println(id);
        String mensagem = "cordenador/"+idaux[0];
        send = mensagem.getBytes();
        DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
        multiSocket.send(pacote);
        try{
            multiSocket.receive(receivePacket);
            resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
            portaCordenador = Integer.parseInt(resposta);
        }catch(Exception ex){
            cordenador = true;
            portaCordenador = socket.getPort();
        }
        // incial pronto,falta logica depois de entrar
        

//        String mensagem = agora.getTime() +"-"+ Integer.toString(port);
//
//            send = mensagem.getBytes();
//            DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
//            multiSocket.send(pacote);

        
                
    }	
    
}
