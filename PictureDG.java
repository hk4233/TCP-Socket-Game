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

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.net.*;
import java.io.*;

public class PictureDG implements PictureI {

    final static String EMPTY = " ";
    
    // This is used for random generations of the characters.
    final static Random randomNumberGenerator = new Random();
    
    // Vector is used to store the image.
    private Vector<String> playerImage;
    public PictureDG() {
        playerImage = new Vector<String>();
        System.out.println("I am being ");
    }

    /**
     * load images
     * 
     * @param fileName image file to be loaded
     * @return the image as string vector
     * @throws IOException
     */
    public void loadImage(String fileName,String server,int port) throws IOException {
        InetAddress address = InetAddress.getByName(server);
        DatagramSocket socket = new DatagramSocket();
        byte[] bytes = fileName.getBytes();
        DatagramPacket request = new DatagramPacket(bytes, bytes.length, address, port);
    
        byte[] buffer = new byte[2048];
        socket.send(request);
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);

        socket.close();

        String image = new String(buffer, 0, response.getLength());
        String[] array = image.split("\n");
        playerImage = new Vector<String>(Arrays.asList(array));
        
    }

    /**
     * displays a percent of image
     * 
     * @param percent the percent to be displyed
     * 
     * return: none
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
        Game game = new Game();
        try {
            game.createPlayers(args);
        } catch (FileNotFoundException e) {
            System.out.println("file not found on server");
        }
        game.run();

    }
}