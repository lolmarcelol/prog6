package eleicao;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Main {
    //public static int portaCordenador;
    public static void main(String[] args) throws IOException  {
        int portaCordenador;
        boolean cordenador = false;
        InetAddress ip;
        String resposta;
        byte[] send = new byte[1024];
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        MulticastSocket multiSocket = new MulticastSocket(3333);
        String group = "224.0.0.2";		
        multiSocket.joinGroup(InetAddress.getByName(group));
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);
        multiSocket.setSoTimeout(5000);
        String aux = ManagementFactory.getRuntimeMXBean().getName();
        String[] idaux = aux.split("@");
        int id = Integer.parseInt(idaux[0]);
        System.out.println(id);
        String mensagem = "cordenador";
        send = mensagem.getBytes();
        DatagramPacket pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
        socket.send(pacote);
        System.out.println("enviou cordenador");
        try{
            socket.receive(receivePacket);
            //multiSocket.receive(receivePacket);
            resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
            System.out.println("recebeu a porta do cordenador:"+resposta);
            portaCordenador = Integer.parseInt(resposta);
        }catch(Exception ex){
            System.out.println("virei cordenador");
            cordenador = true;
            portaCordenador = socket.getLocalPort();
        }
        socket.setSoTimeout(0);
        multiSocket.setSoTimeout(0);
        if(cordenador){
            new Thread(new CordenadorRunnable(multiSocket,socket)).start();
        }else{
            //inicia o cliente
            new Thread(new ClienteRunnable(multiSocket,socket,portaCordenador,idaux[0],receivePacket)).start();
            while(true){
                multiSocket.receive(receivePacket);
                resposta = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                if(!resposta.equals("cordenador")){
                    System.out.println("CORDENADOR MORREU OMG !!");
                    int idOutro = Integer.parseInt(resposta);
                    if(id > idOutro){
                        mensagem = "ok";
                        send = mensagem.getBytes();
                        pacote = new DatagramPacket(send, send.length,receivePacket.getAddress() , receivePacket.getPort());
                        socket.send(pacote);
                        // iniciar eleicao
                        mensagem = Integer.toString(id);
                        send = mensagem.getBytes();
                        try {
                            pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                            socket.send(pacote);
                            socket.receive(receivePacket); // receber ok
                            socket.setSoTimeout(0);
                            multiSocket.receive(receivePacket); // espera para receber a nova porta de cordenador
                            String port = new String(receivePacket.getData(), receivePacket.getOffset(),receivePacket.getLength());
                            portaCordenador = Integer.parseInt(port);

                        } catch (IOException ex1) {
                            System.out.println("sou cordenador");
                            //virar cordenador
                            mensagem = Integer.toString(socket.getLocalPort());
                            send = mensagem.getBytes();
                            pacote = new DatagramPacket(send, send.length,InetAddress.getByName(group) , 3333);
                            socket.send(pacote);
                            new Thread(new CordenadorRunnable(multiSocket,socket)).start();
                        }
                    } 
                }else{
                    System.out.println("NOP");
                }
                
            }
        }
        
        
    }
}