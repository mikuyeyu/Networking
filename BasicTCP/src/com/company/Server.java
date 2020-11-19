package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <h1>Simple TCP Server</h1>
 * This class handles simple TCP connection from server-side.
 * It awaits for client message and sends keyboard input message.
 * <b>Note:</b> If client disconnects, server is waiting to accept next client.
 * @author https://github.com/mikuyeyu
 * @version 2020.11.19
 */

public class Server {

    private static final int PORT = 9999;

    private Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[INFO]Server is running on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[INFO]New client connected from: " + clientSocket.getInetAddress());

                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

                String incomingMessage;

                do {
                    incomingMessage = bufferedReader.readLine();
                    System.out.println(incomingMessage);

                    String outcomingMessage = keyboardReader.readLine();
                    printWriter.println(outcomingMessage);
                } while (!incomingMessage.equals("bye"));

                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
