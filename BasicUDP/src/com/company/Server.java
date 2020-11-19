package com.company;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * <h1>Simple UDP Server</h1>
 * This class handles simple UDP connection from server-side.
 * It awaits for client message and sends it back but in captial letters.
 * <b>Note:</b> If client disconnects, server is waiting to accept next client.
 * @author https://github.com/mikuyeyu
 * @version 2020.11.19
 */

public class Server {

    private static final int port = 9999;

    private Server(){
        try(DatagramSocket serverSocket = new DatagramSocket(port)){
            System.out.println("[INFO]Server is running on port: " + port);

            while(true) {
                byte[] sendMessageArray;
                byte[] incomingMessageArray = new byte[1024];;

                String message = "";

                while (!message.equals("bye")) {
                    DatagramPacket incomingPacket = new DatagramPacket(incomingMessageArray, incomingMessageArray.length);
                    serverSocket.receive(incomingPacket);

                    message = new String(incomingPacket.getData()).trim();
                    System.out.println(message);

                    String uppercaseMessage = message.toUpperCase();
                    sendMessageArray = uppercaseMessage.getBytes();

                    InetAddress ClientIPAddress = incomingPacket.getAddress();
                    int ClientPort = incomingPacket.getPort();

                    DatagramPacket sendPacket = new DatagramPacket(sendMessageArray, sendMessageArray.length, ClientIPAddress, ClientPort);
                    serverSocket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
