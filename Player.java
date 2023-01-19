/*
 * Player.java
 *
 * Version: 1.0
 *
 * Revisions: 1
 */

/**
* The class provides the methods to initialise the details of two players
* who are going to play a game and also to evaluate the winning condition. 

*/

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Player
 */
public class Player {
    private static boolean tcpServer = false;
    private static int playerCount = 0;
    private String playerName;
    private float score;
    private PictureI picture;
    private char[] playerGuess;
    private String correctWord;

    /**
     * sets the type of server to TCP
     */
    public static void setServerTypeAsTCP()
    {
        tcpServer = true;
    }

    public Player() {
        // to track number of players
        playerCount++;

        // default player name
        playerName = "player" + playerCount;
        if (tcpServer) {
            picture = new Picture();
        } else
            picture = new PictureDG();
    }

    /**
     * Method used to return the name of the player.
     * 
     * @return: the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Method used to return the word guessed.
     * 
     * @return: the correct word
     */
    public String getCorrectWord() {
        return correctWord;
    }

    /**
     * Sets the name of the player to it's variable.
     * 
     * @param: name of string format return: none
     */
    public void setPlayerName(String name) {
        playerName = name;
    }

    /**
     * Setting the player's guessed letters in the form of letters and dots (.)
     * 
     * @param: word of string format
     * 
     *              return: none
     */
    public void setPlayersWord(String word) {
        correctWord = word;

        playerGuess = new char[correctWord.length()];

        for (int i = 0; i < playerGuess.length; i++) {
            playerGuess[i] = '.';
        }
    }

    /**
     * Checks whether the guessed letter is present.
     * 
     * @param guessed character to be guessed
     * 
     *                return: none
     */
    public void checkGuessedLetter(char guess) {
        int lettersGuessed = 0;
        int index = correctWord.indexOf(guess);
        if (correctWord.indexOf(guess) != -1) {
            while (index >= 0) {
                if (playerGuess[index] == guess) {
                    System.out.println("already guessed");
                    break;
                }
                playerGuess[index] = guess;
                lettersGuessed++;
                index = correctWord.indexOf(guess, index + 1);
            }
            /*
             * if that alphabet exists in the word1, guessed alphabet will be printed along
             * with missing spaces. Now have to iterate through a decision clause for first
             * player's turn.
             */
            System.out.println("Your guess was correct : " + String.valueOf(playerGuess));
            score += ((float) lettersGuessed / correctWord.length()) * 100;
            // 25 indicates the number of lines in the txt file.
            picture.printImage(score);

        } else {
            System.out.println("wrong guess " + String.valueOf(playerGuess));
        }
    }

    /**
     * Checks if either target string is equal to the player's guessed word.
     * 
     * @param player the current player word
     * @param guess  the guessed letter
     * 
     *               return: true or false (boolean)
     */
    public boolean hasWon() {
        if (String.valueOf(playerGuess).equals(correctWord)) {
            return true;
        }
        return false;
    }

    /**
     * this method loads the picture if the file exists and if it does not, it will
     * throw an exception saying the file has not been found.
     * 
     * @param fileName which is the name of the file which has to be loaded.
     * 
     *                 return: none
     */
    public void loadImage(String fileName, String server, int port) throws FileNotFoundException {
        try {
            picture.loadImage(fileName, server, port);
        } catch (IOException e) {
            System.out.println("Wrong image loaded");
        }
    }

}