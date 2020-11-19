package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * <h1>Simple UDP Client</h1>
 * This class handles simple UDP connection from client-side.
 * It sends keyboard input message to the server and awaits respond.
 * <b>Note:</b> Loop ends when client sends "bye" message to the server.
 * @author https://github.com/mikuyeyu
 * @version 2020.11.19
 */

public class Client {

    private static final String ip = "localhost";
    private static final int port = 9999;

    private Client() {

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            System.out.println("[INFO]Connecting with: " + ip + ":" + port);

            InetAddress IPAddress = InetAddress.getByName(ip);
            byte[] sendMessageArray;
            byte[] incomingMessageArray = new byte[1024];
            String message = "";

            while (!message.equals("bye")) {

                BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
                message = keyboardReader.readLine();
                sendMessageArray = message.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendMessageArray, sendMessageArray.length, IPAddress, port);
                clientSocket.send(sendPacket);

                DatagramPacket incomingPacket = new DatagramPacket(incomingMessageArray, incomingMessageArray.length);
                clientSocket.receive(incomingPacket);

                String incomingMessage = new String(incomingPacket.getData());
                System.out.println(incomingMessage);
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Server not found on: " + ip + ":" + port);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}