import java.io.*;
import java.net.*;

/**
 * PictureServerDG
 */

/**
 * This program loads the image from TCP/IP socket server.
 *

 */

public class PictureServerDG {
    private DatagramSocket socket;

    public PictureServerDG(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: PictureServer <port>");
            return;
        }

        // String quoteFile = args[0];
        int port = Integer.parseInt(args[0]);

        try {
            PictureServerDG server = new PictureServerDG(port);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {
            byte[] input = new byte[1024];
            DatagramPacket request = new DatagramPacket(input, 1024);
            socket.receive(request);
            String quote = loadImageFromFile((new String(input).trim()));
            System.out.println(quote);
            byte[] buffer = quote.getBytes();

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();

            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }

    private String loadImageFromFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String aQuote = "";
        String line;
        while ((line = reader.readLine()) != null) {
            aQuote += line + "\n";
        }

        reader.close();
        return aQuote;
    }

}