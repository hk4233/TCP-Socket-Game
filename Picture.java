/*
 * Picture.java
 *
 * Version: 1.0
 *
 * Revisions: 1
 */

/**
* The class provides methods to load and display the picture.
*

*/
import java.util.Random;
import java.util.Vector;
import java.net.*;
import java.io.*;

public class Picture implements PictureI{

    final static String EMPTY = " ";

    // This is used for random generations of the characters.
    final static Random randomNumberGenerator = new Random();

    // Vector is used to store the image.
    private Vector<String> playerImage;

    public Picture() {
        playerImage = new Vector<String>();
    }

    /**
     * load images
     * 
     * @param fileName image file to be loaded
     * @return the image as string vector
     * @throws IOException
     */
    public void loadImage(String fileName, String server, int port) throws IOException {
        try (Socket socket = new Socket(server, port)) {
            System.out.println("socket");
            fileName.strip();
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(fileName);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line="";
            while ((line = reader.readLine()) != null) {
                playerImage.add(line);
            }
        }
        catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * displays a percent of image
     * 
     * @param percent the percent to be displyed
     * 
     *                return: none
     */
    public void printImage(float percent) {
        for (int index = 0; index < playerImage.size(); index++) {

            for (int i = 0; i < playerImage.elementAt(index).length(); i++) {
                if (randomNumberGenerator.nextInt(101) <= percent)
                    System.out.print(playerImage.elementAt(index).charAt(i));
                else
                    System.out.print(EMPTY);
            }
            System.out.println();

        }
    }

    public static void main(String[] args) {

        // Game object is created.

        Player.setServerTypeAsTCP();
        Game game = new Game();
        try {
            game.createPlayers(args);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("file not found on server");
        }
        game.run();

    }
}