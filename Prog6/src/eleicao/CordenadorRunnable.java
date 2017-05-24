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
                System.out.println("entrei no loop cordenador");
                multiSocket.receive(receivePacket);
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                System.out.println("Eu cordenador recebi: "+resposta);
                String mensagem = Integer.toString(socket.getLocalPort());
                send = mensagem.getBytes();
                DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , receivePacket.getPort());
                socket.send(pacote);
                System.out.println(" E enviei minha porta : "+mensagem+"/n/n");

            }catch(Exception ex){
                System.out.println(ex);
            }   
        }

    }   
}
