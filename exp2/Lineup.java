package exp2;

import java.util.Random;

public class Lineup {

    Random generator = new Random();
    int[] soldiers = new int[10];

    public static void main(String[] args) {
        Lineup lineup = new Lineup();
        for (int i = 0; i < lineup.soldiers.length; i++) {
            lineup.soldiers[i] = lineup.generator.nextInt(100);
        }
        System.out.println("[*] Original soldiers' heights: ");
        for (int height : lineup.soldiers) {
            System.out.print(height + " ");
        }
        System.out.println("\n");

        Army army = new Army(new StrategySelectiveAcsending());
        army.LineupAlgorithm(new StrategySelectiveAcsending(), lineup.soldiers);
        System.out.println("[*] Soldiers' heights after selective ascending arrangement: ");
        for (int height : lineup.soldiers) {
            System.out.print(height + " ");
        }
        System.out.println("\n");

        army.LineupAlgorithm(new StrategyBubbleDescending(), lineup.soldiers);
        System.out.println("[*] Soldiers' heights after bubble descending arrangement: ");
        for (int height : lineup.soldiers) {
            System.out.print(height + " ");
        }
        System.out.println("\n");

        army.LineupAlgorithm(new StrategyOddEvenAcsending(), lineup.soldiers);
        System.out.println("[*] Soldiers' heights after odd-even ascending arrangement: ");
        for (int height : lineup.soldiers) {
            System.out.print(height + " ");
        }
    }
}

interface LineupStrategy {

    abstract void arrange(int[] soldiers);
}

class Army {

    private LineupStrategy strategy;

    public void LineupAlgorithm(LineupStrategy strategy, int[] soldiers) {
        this.strategy = strategy;
        strategy.arrange(soldiers);
    }

    Army(LineupStrategy strategy) {
        this.strategy = strategy;
    }
}

class StrategySelectiveAcsending implements LineupStrategy {

    // 选择法升序排列
    @Override
    public void arrange(int[] soldiers) {
        System.out.println("[*] Arranging soldiers in selective ascending order.");
        for (int i = 0; i < soldiers.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < soldiers.length; j++) {
                if (soldiers[j] < soldiers[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = soldiers[i];
            soldiers[i] = soldiers[minIndex];
            soldiers[minIndex] = temp;
        }
    }
}

class StrategyBubbleDescending implements LineupStrategy {

    // 冒泡法降序排列
    @Override
    public void arrange(int[] soldiers) {
        System.out.println("[*] Arranging soldiers in bubble descending order.");
        for (int i = 0; i < soldiers.length - 1; i++) {
            for (int j = 0; j < soldiers.length - 1 - i; j++) {
                if (soldiers[j] < soldiers[j + 1]) {
                    int temp = soldiers[j];
                    soldiers[j] = soldiers[j + 1];
                    soldiers[j + 1] = temp;
                }
            }
        }
    }
}

class StrategyOddEvenAcsending implements LineupStrategy {

    // 奇偶法升序排列
    @Override
    public void arrange(int[] soldiers) {
        System.out.println("[*] Arranging soldiers in odd-even ascending order.");
        for (int i = 0; i < soldiers.length - 1; i++) {
            for (int j = 0; j < soldiers.length - 1 - i; j++) {
                if ((j % 2 == 0 && soldiers[j] > soldiers[j + 1]) || (j % 2 == 1 && soldiers[j] < soldiers[j + 1])) {
                    int temp = soldiers[j];
                    soldiers[j] = soldiers[j + 1];
                    soldiers[j + 1] = temp;
                }
            }
        }
    }
}
