
package eleicao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class Eleicao {
    public static void main(String [] args) throws IOException, InterruptedException{
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
        // incial pronto,falta logica depois de entrar
        multiSocket.setSoTimeout(0);
        

        
        while(!cordenador){
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
                System.out.println("ñcord enviei aya");
                socket.receive(pacote);
                resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                System.out.println(resposta);
                
            }catch(Exception ex){
                System.out.println(ex);
                System.out.println("cornador morreu, começar eleicao");
                mensagem = "eleicao";
                send = mensagem.getBytes();
                pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                multiSocket.send(pacote);
                mensagem = idaux[0];
                send = mensagem.getBytes();
                pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
            }
        }
            // ERRO AO PEGAR A PORTA DO SOCKET, ESTA VOLTANDO -1 SABE DEUS PQ


//        String mensagem = agora.getTime() +"-"+ Integer.toString(port);
//
//            send = mensagem.getBytes();
//            DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
//            multiSocket.send(pacote);

        
                
    }	
    
}
