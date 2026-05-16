/*
某长途车整点发车一次，正常情况下，汽车在发车40分钟后停靠本站。
由于路上可能出现堵车，假定汽车因此而随机耽搁0-30分钟。
假设某位旅客在每天的10:00-10:30之间一个随机时刻来到本站，那么他平均等待的时间是多少分钟。
请计算出平均等待的分钟数。
 */
package exp1;

import java.util.Random;

public class AverageWaitingTime {

    public static void main(String[] args) {
        int simulations = 1_000_000_000; // 模拟次数，越大结果越精确
        Random rand = new Random();
        double totalWait = 0.0;

        for (int i = 0; i < simulations; i++) {
            // 旅客在 [0, 30) 分钟内随机到达 (10:00 为 0 点)
            double passengerArrival = rand.nextDouble() * 30.0;

            // 9:00 发车的班次延误 [0, 30) 分钟，到站时刻为 -20 + 延误
            double bus9Arrival = -20.0 + rand.nextDouble() * 30.0;

            // 10:00 发车的班次到站时刻为 40 + 延误
            double bus10Arrival = 40.0 + rand.nextDouble() * 30.0;

            double wait;
            if (bus9Arrival >= passengerArrival) {
                // 赶上了 9:00 发车（晚点）的班次
                wait = bus9Arrival - passengerArrival;
            } else {
                // 只能等 10:00 发车的班次
                wait = bus10Arrival - passengerArrival;
            }
            totalWait += wait;
        }

        double averageWait = totalWait / simulations;
        System.out.printf("模拟 %d 次，平均等待时间: %.4f 分钟%n", simulations, averageWait);
        System.out.printf("理论值: 2015/54 ≈ %.4f 分钟%n", 2015.0 / 54.0);
    }
}
