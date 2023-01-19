import java.io.*;
import java.net.*;

/**
 * PictureServerDG
 */

/**
 * This program loads the image from TCP/IP socket server.
 *

 */

public class PictureServer {
    private ServerSocket serverSocket;

    public PictureServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: PictureServer <port>");
            return;
        }

        // String quoteFile = args[0];
        int port = Integer.parseInt(args[0]);

        try {
            PictureServer server = new PictureServer(port);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private void service() throws IOException {
        while (true) {
            Socket request = serverSocket.accept();
            InputStream input = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String quote = loadImageFromFile((reader.readLine().trim()));
            OutputStream output = request.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(quote);
            request.close();
        }
    }

    private String loadImageFromFile(String file) throws IOException {
        System.out.println("file "+file);
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