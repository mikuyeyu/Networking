package com.company;

import java.io.*;
import java.net.Socket;

/**
 * <h1>Simple TCP Client</h1>
 * This class handles simple TCP connection from client-side.
 * It sends keyboard input message to the server and awaits respond.
 * <b>Note:</b> Loop ends when client sends "bye" message to the server.
 * @author https://github.com/mikuyeyu
 * @version 2020.11.19
 */

public class Client {

    private static final String IP = "localhost";
    private static final int PORT = 9999;

    private Client() {
        try (Socket socket = new Socket(IP, PORT)) {
            System.out.println("[INFO]Connecting with: " + IP + ":" + PORT);

            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            String outcomingMessage;
            do {
                outcomingMessage = keyboardReader.readLine();
                printWriter.println(outcomingMessage);

                if (!outcomingMessage.equals("bye")) {
                    String incomingMessage = bufferedReader.readLine();
                    System.out.println(incomingMessage);
                }
            } while (!outcomingMessage.equals("bye"));

        } catch (IOException e) {
            System.err.println("[ERROR] Server not found on: " + IP + ":" + PORT);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
