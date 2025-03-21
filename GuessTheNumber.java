import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class GuessTheNumber {

    private int secretNumber;
    private int maxAttempts = 10;
    private int attempts;
    private int lowestScore; 

    public GuessTheNumber() {
        this.secretNumber = new Random().nextInt(100) + 1;  
        this.attempts = 0;
        this.lowestScore = loadLowestScore();  
    }

    public void startGame() {
        System.out.println("Welcome to Guess the Number Game!");
        System.out.println("Your current lowest score is: " + (lowestScore == Integer.MAX_VALUE ? "None" : lowestScore + " attempts"));
    }

    public void endGame() {
        System.out.println("Game Over! The secret number was: " + secretNumber);

        if (attempts < lowestScore) {  
            System.out.println("New Lowest Score! You guessed the number in " + attempts + " attempts.");
            saveLowestScore(attempts);  
            lowestScore = attempts;  
        } else {
            System.out.println("Your score is not a new low score.");
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        
        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess == secretNumber) {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");

                if (attempts < lowestScore) {
                    System.out.println("New Lowest Score! Saving...");
                    saveLowestScore(attempts);
                    lowestScore = attempts;
                }
                return;
            } else if (guess < secretNumber) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }
        }

        endGame();
    }

    private int loadLowestScore() {
        File file = new File("lowest_score.txt");

        if (!file.exists()) {
            return Integer.MAX_VALUE; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String score = reader.readLine();
            if (score != null && !score.trim().isEmpty()) {
                return Integer.parseInt(score.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading lowest score file: " + e.getMessage());
        }

        return Integer.MAX_VALUE;  
    }

    private void saveLowestScore(int score) {
        try (FileWriter writer = new FileWriter("lowest_score.txt", false);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {  

            bufferedWriter.write(Integer.toString(score));
            bufferedWriter.newLine();  
            bufferedWriter.flush();  
            System.out.println("Lowest score saved: " + score);

        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GuessTheNumber game = new GuessTheNumber();  
        game.startGame();  
        game.play();  
    }
}
