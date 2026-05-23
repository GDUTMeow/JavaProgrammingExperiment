package exp2;

import java.util.Calendar;

public class SalaryPayment {

    static final String[] MONTH_MAPPING = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public static void main(String[] args) {
        Employee[] employees = new Employee[4];
        employees[0] = new SalariedEmployee("10001", "Alice", new int[]{1968, 6}, 5000);
        employees[1] = new HourlyEmployee("10002", "Bob", new int[]{1990, 5}, 30);
        employees[2] = new CommissionEmployee("10003", "Charlie", new int[]{1985, 12}, 0, 0.8f);
        employees[3] = new BasePlusCommissionEmployee("10004", "David", new int[]{1980, 8}, 3000, 0.5f);
        System.out.println("Current month is " + MONTH_MAPPING[Calendar.getInstance().get(Calendar.MONTH)] + ".");
        System.out.println(employees[0].name + "'s salary: " + employees[0].getSalary());
        System.out.println(employees[1].name + "'s salary (170 hours): " + ((HourlyEmployee) employees[1]).getSalary(170));
        System.out.println(employees[2].name + "'s salary (10000 sales): " + ((CommissionEmployee) employees[2]).getSalary(10000));
        System.out.println(employees[3].name + "'s salary (5000 sales): " + ((BasePlusCommissionEmployee) employees[3]).getSalary(5000));
    }
}

interface Payable {
    float getSalary();
}

class Employee implements Payable {

    String id;  // 工号
    String name;    // 姓名
    int[] birth;    // 出生年月
    int income;     // 月薪或时薪

    @Override
    public float getSalary() {
        float tmp = this.income;
        if (Calendar.getInstance().get(Calendar.MONTH) + 1 == this.birth[1]) {   // 当月生日 +100
            tmp += 100;
        }
        return tmp;
    }

    Employee(String id, String name, int[] birth, int income) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.income = income;
    }
}

class SalariedEmployee extends Employee {

    @Override
    public float getSalary() {
        return super.getSalary();
    }

    SalariedEmployee(String id, String name, int[] birth, int income) {
        super(id, name, birth, income);
    }
}

class HourlyEmployee extends Employee {

    private final float OvertimeCoefficient = 1.5f;    // 工作日加班系数
    private final int OvertimeThreshold = 160;  // 每月加班线，超过即加班部分

    class NoHourSpecifiedException extends RuntimeException {

        NoHourSpecifiedException(String message) {
            super(message);
        }
    }

    @Override
    public float getSalary() {
        throw new NoHourSpecifiedException("Hourly employee salary cannot be calculated without specifying hours worked.");
    }

    public float getSalary(int hours) {
        float tmp = Calendar.getInstance().get(Calendar.MONTH) + 1 == this.birth[1] ? 100 : 0;   // 当月生日 +100
        if (hours > this.OvertimeThreshold) {   // 本月工作时间超过了加班阈值
            tmp += this.OvertimeThreshold * this.income;    // 前 160 小时
            tmp += (hours - this.OvertimeThreshold) * this.income * this.OvertimeCoefficient;    // 超过 160 小时的部分
        } else {
            tmp += hours * this.income;
        }
        return tmp;
    }

    HourlyEmployee(String id, String name, int[] birth, int income) {
        super(id, name, birth, income);
    }
}

class CommissionEmployee extends Employee {

    float commissionRate;    // 佣金率

    class NoSalesSpecifiedException extends RuntimeException {

        NoSalesSpecifiedException(String message) {
            super(message);
        }
    }

    @Override
    public float getSalary() {
        throw new NoSalesSpecifiedException("Commission employee salary cannot be calculated without specifying sales.");
    }

    public float getSalary(int sales) {
        return sales * this.commissionRate + (Calendar.getInstance().get(Calendar.MONTH) + 1 == this.birth[1] ? 100 : 0);   // 销售额 * 佣金率 + 当月生日奖励
    }

    CommissionEmployee(String id, String name, int[] birth, int income, float commissionRate) {     // 佣金不用，但是 extends 会用到，留一下
        super(id, name, birth, income);
        this.commissionRate = commissionRate;
    }
}

class BasePlusCommissionEmployee extends CommissionEmployee {

    @Override
    public float getSalary(int sales) {
        return sales * this.commissionRate + this.income + (Calendar.getInstance().get(Calendar.MONTH) + 1 == this.birth[1] ? 100 : 0);   // 基本工资 + 销售额 * 佣金率 + 当月生日奖励
    }

    BasePlusCommissionEmployee(String id, String name, int[] birth, int income, float commissionRate) {
        super(id, name, birth, income, commissionRate);
    }
}
