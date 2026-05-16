package exp1;

/*
利用随机函数产生25个随机整数给一个5行5列的二维数组赋值。
1 按行列输出该数组；
2 求其最外一圈元素之和；
3 求主对角线中最大元素的值，指出其位置。
基本思路：求最外一圈元素之和的关键是找出最外一圈元素的特征。主对角线上元素的特征是行列值相等。
 */
import java.util.Random;

public class Matrix {

    static int getRoundSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == 4 || j == 0 || j == 4) {
                    sum += matrix[i][j];
                }
            }
        }
        return sum;
    }

    static void fillMatrix(int[][] matrix) {
        Random generator = new Random();    // 新建随机生成器
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int tmp = generator.nextInt(100);
                matrix[i][j] = tmp;     // 填充
            }
        }
    }

    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j]);
                System.out.print("\t");
            }
            System.out.print("\n");
        }
    }

    static int getDiagonalSum(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == j) {
                    sum += matrix[i][j];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int sum;
        int[][] matrix = new int[5][5]; // 新建一个 5x5 的 int 类型矩阵
        q1_4333.fillMatrix(matrix);     // 填充矩阵
        q1_4333.printMatrix(matrix);    // 打印矩阵
        sum = q1_4333.getRoundSum(matrix);  // 获取最外一圈元素之和
        System.out.print("The summary of outer round is ");
        System.out.println(sum);    // 输出最外一圈元素之和
        sum = q1_4333.getDiagonalSum(matrix);
        System.out.print("The summary of diagonal is ");    // 输出主对角线元素之和
        System.out.println(sum);
    }

}
