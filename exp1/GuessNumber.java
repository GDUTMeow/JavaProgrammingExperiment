/*
编写一个猜数程序。命令行显示菜单：1.开始；2.退出。
用户选择1，则程序生成一个0~99之间的随机整数，命令行显示“请输入你猜的数：”让用户猜。
用户输入猜测的数据，猜对了命令行显示“你猜对了”，并且出现菜单：1.再来一次；2.退出。
没有猜对程序给出提示（如：太大了，太小了），并要求在命令行继续输入猜测的值；
三次没猜对则程序公布正确的数字，并且出现菜单：1.再来一次；2.退出。
猜数时任何一个环节，命令行上总会显示用户的总得分情况。
得分规则如下：一次猜中得3分，第二次猜中得2分，其三次得1分，三次没有猜中扣2分。
主类的名称定为GuessNumber。
提示：题目只是大致描述了命令行的输入和输出的要求，可以自行设计命令行的输入输出的形式，使得用户在使用时觉得界面友好。
同样，整个猜测的流程可以根据自己的理解进行优化和调整，使得用户在使用时更为流畅。
 */
package exp1;

import java.util.Random;

public class GuessNumber {

    static int score = 0;

    static void printMenu() {
        System.out.println("[+] Welcome to the number guessing game!");
        System.out.println("[+] Rule: You give me your answer. If hit, u win 3 pts. Each guess will cost 1 pt.");
        System.out.println("[+] If you miss 3 times, you will lose 2 pts. Good luck!");
        System.out.println("[*] Current score: " + score);
        System.out.println();
        System.out.println("[1] Start");
        System.out.println("[2] Exit");
        System.out.print("[*] What do you want to do: ");
    }

    static void exit() {
        System.out.println("[-] Thank you for your playing! You got " + score + " pts in the end. See you next time!");
        System.exit(0);
    }

    static void start() {
        System.out.println("[*] Starting the game...");
        Random generator = new Random();
        int target = generator.nextInt(100);
        int guessCount = 0;
        while (guessCount < 3) {
            System.out.print("[*] Tell me your answer: ");
            int guess = new java.util.Scanner(System.in).nextInt();
            guessCount++;
            if (guess == target) {
                System.out.println("[+] You got it! You win " + (4 - guessCount) + " pts!");
                System.out.println();
                score += 4 - guessCount;
                return;
            } else if (guess < target) {
                System.out.println("[-] Too small!");
            } else {
                System.out.println("[-] Too big!");
            }
        }
        System.out.println("[-] You failed to guess the number. The correct number is " + target + ".");
        System.out.println("[-] You lose 2 pts!");
        System.out.println();
        score -= 2;
    }

    static void handleChoice(int choice) {
        switch (choice) {
            case 1 ->
                start();
            case 2 ->
                exit();
            default ->
                System.out.println("[!] Invalid choice. You must choose between 1 and 2.");
        }
    }

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = new java.util.Scanner(System.in).nextInt();
            handleChoice(choice);
        }
    }
}
