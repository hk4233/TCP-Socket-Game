/*
 * Game.java
 *
 * Version: 1.0
 *
 * Revisions: 1
 */

/**
* The class provides the methods to facilitate the game between the players. 
*

*/

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    // Declaring players
    private Player player1;
    private Player player2;

    // This is used to switch between players.
    private boolean firstPlayerTurn;

    // This is used to scan the file.
    private Scanner scanner;

    public Game() {
        
        player1 = new Player();
        player2 = new Player();
        scanner = new Scanner(System.in);
        firstPlayerTurn = true;
    }

    /**
     * Used to create players.
     * 
     * @param args
     * 
     *             return: none
     * @throws FileNotFoundException
     */
    void createPlayers(String[] args) throws FileNotFoundException {
        int properArguments = 0;
        System.out.println(args.length);
        String image1= "", image2= "",server = "";
        int port=0;
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-me")) {
                player1.setPlayerName(args[i + 1]);
                properArguments++;
            } else if (args[i].equals("-meWords")) {

                player1.setPlayersWord(args[i + 1]);
                properArguments++;

            } else if (args[i].equals("-mePicture")) {

                image1 = args[i + 1];
                properArguments++;

            } else if (args[i].equals("-you")) {

                player2.setPlayerName(args[i + 1]);
                properArguments++;

            } else if (args[i].equals("-youWords")) {

                player2.setPlayersWord(args[i + 1]);
                properArguments++;

            } else if (args[i].equals("-youPicture")) {

                image2 = args[i + 1];
                properArguments++;

            }else if (args[i].equals("-server")) {

                server = args[i + 1];
                properArguments++;

            }else if (args[i].equals("-port")) {
                try {
                    
                    port = Integer.parseInt(args[i + 1]);
                } catch (Exception e) {
                    System.out.println("port is an integer");
                    System.exit(1);
                }
                properArguments++;

            }

             else {
                 System.out.println(args[i]);
                break;
            }
            
        }
        System.out.println("wwwww");

        /*
         * If the total number of arguments is not equal to 6, we have to print the
         * following statements.
         */
        if (properArguments != 8) {
            System.out.println(properArguments);
            System.out.println("Unrecognized or not enough arguments ");
            System.out.println("-me 'name' or -you 'name' to define " + "player names ");
            System.out.println("-meWords 'word' or -youWords 'word' " + "to define player name ");
            System.out.println("-mePicture 'file_location' or -youPicture" + " 'file_location' to define player name ");
            System.out.println("-server 'location' or -port" + " 'portno' to define server ");
            System.exit(1);
        }
        player1.loadImage(image1, server, port);
        System.out.println("wwwww");

        player2.loadImage(image2, server, port);
        System.out.println("wwwww");

    }

    /**
     * Used to print which player's turn it is.
     * 
     * @param player to be played
     * 
     *               return: none
     */
    private void getInputForPlayer(Player player) {
        System.out.print(player.getPlayerName() + "'s turn : ");

        String guess = scanner.next();
        while (guess.length() > 1) {
            System.out.print("Enter single character: ");
            guess = scanner.next();
        }

        player.checkGuessedLetter(guess.charAt(0));

    }

    /**
     * Used to switch between players and see if the current player guessed the
     * correct alphabet.
     * 
     * Also to print the word they have guessed till now.
     * 
     * @param player
     * 
     *               return: none
     */
    public void run() {
        while (true) {

            /* Loop keeps executing until either the ch array */
            if (firstPlayerTurn) {
                getInputForPlayer(player1);
                firstPlayerTurn = false;
            } else {
                getInputForPlayer(player2);
                firstPlayerTurn = true;
            }

            /*
             * If the player whose guessed characters matches the target string, they'll win
             * the match.
             */
            if (player1.hasWon()) {
                System.out.println("The word \"" + player1.getCorrectWord() + "\" guessed correctly was : "
                        + player1.getPlayerName());
                break;
            } else if (player2.hasWon()) {
                System.out.println("The word \"" + player2.getCorrectWord() + "\" guessed correctly was : "
                        + player2.getPlayerName());
                break;
            }
        }

    }

}
