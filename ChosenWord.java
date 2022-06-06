public class ChosenWord {
    private String word;
    private boolean[] charsGuessed;
    
    //// Constructor to convert the word into all lowercase letters
    public ChosenWord(String word) {
        this.word = word.toLowerCase();
        //! create new array of boolean to check if the character inputted is correct as the character 
        //! in the position of the answer
        charsGuessed = new boolean[word.length()];
    }

    //// Function to check if the word is already guessed or not
    public boolean isEntireWordGuessed() {
        //! if there is false in the answer, return false
        for (boolean b : charsGuessed) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    //// Function to alocate index of the character guessed
    public void charGuess(char guess) {
        int index = word.indexOf(guess);

        while (index >= 0) {
            charsGuessed[index] = true;
            index = word.indexOf(guess, index + 1);
        }
    }

    @Override
    //// Function to convert char into string
    public String toString() {
        StringBuilder formattedWord = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (charsGuessed[i]) {
                formattedWord.append(word.charAt(i));
            } else {
                formattedWord.append('_');
            }

            formattedWord.append(' ');
        }
        return formattedWord.toString();
    }
}
