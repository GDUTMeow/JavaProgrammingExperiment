package exp2;

import java.util.Scanner;

public class SingerJudgingLine {
    final static int JUDGEMENTS = 5;    // 评委数
    public static void main(String[] args) {
        InputScore inputScore = new InputScore(JUDGEMENTS);
        inputScore.inputScores();
        float[] scores = inputScore.getScores();
        System.out.print("[*] The scores before processing: ");
        for (float score : scores) {
            System.out.print(score + " ");
        }
        System.out.println();
        DelScore delScore = new DelScore(scores);
        delScore.delMaxMin();
        ComputerAver computerAver = new ComputerAver(delScore.getProcessedScores());
        float average = computerAver.computeAverage();

        System.out.print("[*] The scores after processing: ");
        for (float score : delScore.getProcessedScores()) {
            System.out.print(score + " ");
        }
        System.out.println();
        System.out.println("[*] The average score is: " + average);
    }
}

class InputScore {

    private Scanner scanner = new Scanner(System.in);
    private float[] scores;

    InputScore(int count) {     // 评委数
        this.scores = new float[count];
    }

    void inputScores() {
        for (int i = 0; i < scores.length; i++) {
            try {
                System.out.print("[+] Please enter the score for judge " + (i + 1) + ": ");
                scores[i] = scanner.nextFloat();
            } catch (java.util.InputMismatchException e) {
                System.out.println("[-] Invalid input. Please enter a valid number.");
                scanner.nextLine();
                i--;
            } catch (Exception e) {
                System.out.println("[-] An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
                i--;
            }
        }
        System.out.println("[*] Scores have been entered.");
    }

    float[] getScores() {
        return scores;
    }
}

class DelScore {

    private float[] scores;
    private float[] processedScores;

    class ScoresTooFewException extends RuntimeException {

        public ScoresTooFewException(String message) {
            super(message);
        }
    }

    DelScore(float[] scores) {
        this.scores = scores;
        if (scores.length < 3) {
            throw new ScoresTooFewException("[-] Not enough scores to remove max and min. Only " + scores.length + " scores provided.");
        }
        this.processedScores = new float[scores.length - 2];    // 去掉最大最小值后的数组
    }

    void delMaxMin() {
        if (scores.length < 3) {
            throw new ScoresTooFewException("[-] Not enough scores to remove max and min. Only " + scores.length + " scores provided.");
        }
        float max = scores[0];
        float min = scores[0];
        for (float score : scores) {
            if (score > max) {
                max = score;
            }
            if (score < min) {
                min = score;
            }
        }
        System.out.println("[*] Max score: " + max);
        System.out.println("[*] Min score: " + min);
        // 去掉最大最小值
        int index = 0;
        for (float score : scores) {
            if (score != max && score != min) {
                processedScores[index++] = score;
            }
        }
    }

    float[] getProcessedScores() {
        return processedScores;
    }
}

class ComputerAver {

    private float[] scores;

    class ScoresTooFewException extends RuntimeException {

        public ScoresTooFewException(String message) {
            super(message);
        }
    }

    ComputerAver(float[] scores) {
        this.scores = scores;
    }

    float computeAverage() {
        if (scores.length == 0) {
            throw new ScoresTooFewException("[-] No scores available to compute average.");
        }
        float sum = 0;
        for (float score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
}
