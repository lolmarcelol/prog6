package eleicao;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        while(true){
            try{
            socket.setSoTimeout(5000);
               while(true){
                    System.out.println("CRUN: entrei no loop nao cordenador");
                    Random random = new Random();
                    int randomNumber = random.nextInt(10)+5;
                    randomNumber = randomNumber*1000;
                    System.out.println("CRUN: ñ cord dormi");
                    Thread.sleep(randomNumber);
                    mensagem = "aya";
                    send = mensagem.getBytes();
                    System.out.println(portaCordenador);
                    System.out.println("CRUN: enviando:"+mensagem+"para: "+portaCordenador);
                    pacote = new DatagramPacket(send, send.length,receivePacket.getAddress() , portaCordenador);
                    socket.send(pacote);
                    System.out.println("CRUN: enviei:"+mensagem+"para: "+portaCordenador);
                    System.out.println("CRUN: Minha porta eh: "+socket.getLocalPort());
                    socket.receive(receivePacket);
                    String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                    System.out.println(resposta);
               }
           }catch(Exception ex){
                System.out.println(ex);
                System.out.println("CRUN: cornador morreu, começar eleicao");
                mensagem = id;
                send = mensagem.getBytes();
                try {
                    pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                    socket.send(pacote);
                    socket.receive(receivePacket); // receber ok
                    socket.setSoTimeout(0);
                    multiSocket.receive(receivePacket); // espera para receber a nova porta de cordenador
                    String port = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                    this.portaCordenador = Integer.parseInt(port);
                    
                } catch (IOException ex1) {
                    System.out.println("CRUN: virei cordenador pela thread, dei break");
                    break;
                }

           }

        }
        System.out.println("Terminei minha execução de clienteRunnable");
    }
       
}