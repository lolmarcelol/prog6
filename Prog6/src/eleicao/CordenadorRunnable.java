package eleicao;

import static com.sun.management.jmx.Trace.send;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class CordenadorRunnable implements Runnable {
    MulticastSocket multiSocket;
    byte[] receiveData = new byte[1024];
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    DatagramSocket socket;
    byte[] send = new byte[1024];
    String group = "224.0.0.2";
    public CordenadorRunnable(MulticastSocket multiSocket,DatagramSocket socket){
        this.multiSocket = multiSocket;
        this.socket = socket;
    }
    
    @Override
    public void run() {
        while(true){
            try{
                
                new Thread(new OuvirNoobs(multiSocket,socket.getLocalPort())).start();
                System.out.println("Eu cordenadorAya atendo"+socket.getLocalPort());
                socket.receive(receivePacket);
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                System.out.println("Eu cordenadorAya recebi: "+resposta);
                String mensagem = Integer.toString(socket.getLocalPort());
                send = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , receivePacket.getPort());
                socket.send(pacote);
                System.out.println(" E enviei minha porta : "+mensagem+"para: "+receivePacket.getPort());

            }catch(Exception ex){
                System.out.println(ex);
            }   
        }

    }   
}
