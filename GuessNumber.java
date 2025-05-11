public class GuessNumber {
    public static void main(String[] args) {
        int numberToGuess = 37;
        int rangeLeft = 0;
        int rangeRight = 100;
        int playerGuessNumber;

        do {
            playerGuessNumber = (rangeRight + rangeLeft) / 2;
            if (numberToGuess == playerGuessNumber) {
                System.out.println("Win!" + numberToGuess);
                break;
            } else if (numberToGuess < playerGuessNumber) {
                System.out.println("загаданное число меньше" + playerGuessNumber);
                rangeRight = playerGuessNumber;
            } else if (numberToGuess > playerGuessNumber) {
                System.out.println("загаданное число больше" + playerGuessNumber);
                rangeLeft = playerGuessNumber;
            }
        } while (numberToGuess != playerGuessNumber);
    }
}