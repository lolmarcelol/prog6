package eleicao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class ClienteRunnable implements Runnable {
    MulticastSocket multiSocket;
    byte[] receiveData = new byte[1024];
    DatagramPacket receivePacket;
    DatagramSocket socket;
    byte[] send = new byte[1024];
    String group = "224.0.0.2";
    String mensagem;
    DatagramPacket pacote;
    int portaCordenador;
    String id;
    public ClienteRunnable(MulticastSocket multiSocket,DatagramSocket socket,int portaCordenador,String id,DatagramPacket receivePacket){
        this.multiSocket = multiSocket;
        this.socket = socket;
        this.portaCordenador = portaCordenador;
        this.id = id;
        this.receivePacket = receivePacket;
    }
    
    @Override
    public void run() {
        try{
        socket.setSoTimeout(5000);
           while(true){
               System.out.println("entrei no loop nao cordenador");
               Random random = new Random();
               int randomNumber = random.nextInt(10)+5;
               randomNumber = randomNumber*1000;
               System.out.println("ñ cord dormi");
               Thread.sleep(randomNumber);
               mensagem = "aya";
               send = mensagem.getBytes();
               System.out.println(portaCordenador);
               pacote = new DatagramPacket(send, send.length,receivePacket.getAddress() , portaCordenador);
               socket.send(pacote);
               System.out.println("enviei:"+mensagem+"para: "+portaCordenador);
               System.out.println("Minha porta eh: "+socket.getLocalPort());
               socket.receive(receivePacket);
               String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
               System.out.println(resposta);
           }
       }catch(Exception ex){
           System.out.println(ex);
           System.out.println("cornador morreu, começar eleicao");
           
       }
    }
       
}
   
