package eleicao;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class OuvirNoobs implements Runnable {
    MulticastSocket multiSocket;
    byte[] receiveData = new byte[1024];
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    byte[] send = new byte[1024];
    DatagramSocket socket;
    String group = "224.0.0.2";
    int portaCordenador;
    public OuvirNoobs(MulticastSocket multiSocket,int portaCordenador) throws SocketException{
        this.multiSocket = multiSocket;
        this.socket = new DatagramSocket();
        this.portaCordenador = portaCordenador;
        //this.mensagem = mensagem;
    }
    
    @Override
    public void run() {
        try{
            System.out.println("Eu ouvir noobs estou atendendo: "+portaCordenador);
            multiSocket.receive(receivePacket);
            String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
            System.out.println("Eu ouvido noob recebi: "+resposta);
            String mensagem = Integer.toString(portaCordenador);
            send = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , receivePacket.getPort());
            socket.send(pacote);
            System.out.println(" E enviei a porta do cordenador : "+mensagem+"para: "+receivePacket.getPort());
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
}
