import javax.swing.*;
import java.util.*;
import java.io.*;

public class Game implements WordsBank{
    private int numberOfGuesses;
    private int tries;
    private String[] words;
    private String unguessedCharacters;
    private ChosenWord chosenWord;
    private JFrame frame = new JFrame("Revamped Hangman");
    private JFrame frame1 = new JFrame("Game Over");

    @Override
    //// Function to get a random word from "words.txt";
    public String getRandomWord() {
        words = new String[5757];
        //// Source from: https://github.com/SimonVutov/WordleGUI/blob/main/GUI.java
        try {
            File myObj = new File("words.txt");
            Scanner myReader = new Scanner(myObj);
            int counter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //! add data into array
                words[counter] = data;
                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException f) {
            System.out.println("An error has occured");
            f.printStackTrace();
        }
        // // returns a random word from the txt file
        return words[(int)(Math.random() * (words.length - 1))];
    }

    //// Function to start the game
    public void startNewGame() {
        this.unguessedCharacters = "abcdefghijklmnopqrstuvwxyz";

        numberOfGuesses = 0;
        tries = 8;
        this.chosenWord = new ChosenWord(getRandomWord());

        inputUserLetterGuess();
    }

    //// Function to validate the user's guess
    private void handleUserLetterGuess(char guessedChar) {
        numberOfGuesses++;
        tries--;

        removeOptionalCharGuess(guessedChar);

        chosenWord.charGuess(guessedChar);
    }

    //// Function to remove guessed characters from the option
    private void removeOptionalCharGuess(char guessedChar) {
        //! replace the guessed characters as blank
        unguessedCharacters = unguessedCharacters.replace(Character.toString(guessedChar), "");
    }

    //// Function to validate the user's guesses
    private void inputUserLetterGuess() {
        //! create an array of the unguessed characters
        Character[] charactersArray = new Character[unguessedCharacters.length()];

        for(int i = 0; i < charactersArray.length; i++) {
            charactersArray[i] = Character.valueOf(unguessedCharacters.charAt(i));
        }

        //! display a window of the unguessed characters
        Character guessedLetter = (Character) JOptionPane.showInputDialog(frame, "What letter do you want to guess?",
                                                                        "Revamped Hangman", 
                                                                        JOptionPane.QUESTION_MESSAGE, 
                                                                        null, 
                                                                        charactersArray, 
                                                                        //! sets the first unguessed letter of the alphabet as a placeholder
                                                                        charactersArray[0]);

        //! if all letters have been guessed or tries are 0, exit the program
        if (guessedLetter == null || tries == 0) {
            GameOver(frame1);
            return;
        }

        handleUserLetterGuess((guessedLetter));

        displayUserGuessResults(frame);
    }

    //// Function to show progress of the guesses
    private void displayUserGuessResults(JFrame frame){
        //! shows a character if the character guessed is correct
        JLabel wordStartLabel = new JLabel("After your guess: " + chosenWord.toString());
        JLabel triesLeft = new JLabel("Tries left: " + (tries + 1));
        JButton button = new JButton();

        JPanel panel = new JPanel();
        panel.add(wordStartLabel);
        panel.add(triesLeft);
        panel.add(button);

        //! Sets the window
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        //! makes sure the exit is working smoothly
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //! makes the frame visible
        frame.setVisible(true);

        //// Checks if the word is completely exposed
        if (!chosenWord.isEntireWordGuessed()){
            //! If word isn't completely guessed, continue guessing
            button.addActionListener(e -> {
                frame.remove(panel);
                inputUserLetterGuess();
            });

            //! makes a button to continue guessing
            button.setText("Continue guessing");

        } else {
            //! If word is completely guessed, show results and give option to start a new game
            JLabel guessesLabel = new JLabel("Congratulations! number of guesses is: " + numberOfGuesses);
            panel.add(guessesLabel);
            button.addActionListener(e -> {
                frame.remove(panel);
                startNewGame();
            });
            button.setText("Start a new game");
        }
    }

    private void GameOver(JFrame frame) {
        JLabel gameOver = new JLabel("Game Over");

        JPanel panel = new JPanel();

        panel.add(gameOver);
        frame.add(panel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        exit();
    }

    //// Function to close the frame on forced exit
    private void exit() {
        frame.dispose();
    }
}