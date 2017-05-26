package eleicao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EleicaoRunnable implements Runnable {

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
    
    public EleicaoRunnable(MulticastSocket multiSocket,DatagramSocket socket){
        this.multiSocket = multiSocket;
        this.socket = socket;
        String aux = ManagementFactory.getRuntimeMXBean().getName();
        String[] idaux = aux.split("@");
        id = idaux[0];
    }
    
    @Override
    public void run() {
        try {
            while(true){
                multiSocket.receive(receivePacket);
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                if(resposta.equals("eleicao")){
                    //pausar a execucao das outras threads?
                    mensagem = id;
                    send = mensagem.getBytes();
                    System.out.println(portaCordenador);
                    pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , portaCordenador);
                    socket.send(pacote);

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(EleicaoRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
}
