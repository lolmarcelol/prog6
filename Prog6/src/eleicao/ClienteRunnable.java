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
    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    DatagramSocket socket;
    byte[] send = new byte[1024];
    String group = "224.0.0.2";
    String mensagem;
    DatagramPacket pacote;
    int portaCordenador;
    String id;
    public ClienteRunnable(MulticastSocket multiSocket,DatagramSocket socket,int portaCordenador,String id){
        this.multiSocket = multiSocket;
        this.socket = socket;
        this.portaCordenador = portaCordenador;
        this.id = id;
    }
    
    @Override
    public void run() {
        while(true){
             try{
                System.out.println("entrei no loop nao cordenador");
                Random random = new Random();
                int randomNumber = random.nextInt(10)+5;
                randomNumber = randomNumber*1000;
                System.out.println("ñ cord dormi");
                Thread.sleep(randomNumber);
                mensagem = "aya";
                send = mensagem.getBytes();
                pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , portaCordenador);
                socket.send(pacote);
                System.out.println("enviei:"+mensagem);
                socket.receive(pacote);
                String resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                System.out.println(resposta);
                
            }catch(Exception ex){
                System.out.println(ex);
                System.out.println("cornador morreu, começar eleicao");
                mensagem = "eleicao";
                send = mensagem.getBytes();
                try{
                    pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                    multiSocket.send(pacote);
                    mensagem = id;
                    send = mensagem.getBytes();
                    pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333); 
                }catch(IOException io){
                    
                }
            }
        }
       
    }
    
}
