/*
蒙特卡洛方法求圆周率。蒙特卡洛方法通过随机采样估计数值，计算圆周率π的步骤如下：
1、区域设定‌：考虑单位正方形（0≤x≤1，0≤y≤1）及其内接四分之一圆（x² + y² ≤ 1）。四分之一圆面积为π/4，正方形面积为1。
2、随机采样‌：生成大量均匀分布在正方形内的随机点（x, y）。
3、统计比例‌：计算落在四分之一圆内的点数N_in，总点数N_total。概率上，N_in/N_total ≈ π/4。
4、估计π值‌：将比例乘以4，得到π ≈ 4 × (N_in / N_total)。
 */
package exp1;

import java.util.Random;

public class MonteCarlo {

    private static final int POINT_COUNT = 1_000_000;

    public static void main(String[] args) {
        int insideCount = 0;
        Random generator = new Random();
        for (int i = 0; i < POINT_COUNT; i++) {
            // 进度条
            System.out.print("|" + "=".repeat((int) (i / (POINT_COUNT / 100.0))) + " ".repeat(100 - (int) (i / (POINT_COUNT / 100.0))) + "| " + (int) (i / (POINT_COUNT / 100.0)) + "% " + (i + 1) + "/" + POINT_COUNT + "\r");
            double x = generator.nextDouble();
            double y = generator.nextDouble();
            if (x * x + y * y <= 1) {
                insideCount++;
            }
        }
        double estimatedPI = 4.0 * insideCount / POINT_COUNT;
        System.out.println();
        System.out.println("Estimated π: " + estimatedPI);
    }
}
