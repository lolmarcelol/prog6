package eleicao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Main {
    public static void main(String[] args) throws IOException  {
        boolean cordenador = false;
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
        socket.setSoTimeout(0);
        multiSocket.setSoTimeout(5000);
        String aux = ManagementFactory.getRuntimeMXBean().getName();
        String[] idaux = aux.split("@");
        int id = Integer.parseInt(idaux[0]);
        System.out.println(id);
        String mensagem = "cordenador";
        send = mensagem.getBytes();
        DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
        multiSocket.send(pacote);
         System.out.println("enviou cordenador");
        try{
            multiSocket.receive(receivePacket);
            multiSocket.receive(receivePacket);
            resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
            System.out.println("recebeu a porta do cordenador:"+resposta);
            portaCordenador = Integer.parseInt(resposta);
        }catch(Exception ex){
            System.out.println("virei cordenador");
            cordenador = true;
            portaCordenador = socket.getLocalPort();
        }
        // come~co do multiThread vamo ae
        multiSocket.setSoTimeout(0);
        
    }
}