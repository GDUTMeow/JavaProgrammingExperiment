package exp2;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsumingPay {

    ArrayList<Card> cards = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    int password;

    public static void main(String[] args) {
        System.out.println("[+] First launch detected. Setting up consuming pay system...");
        System.out.print("[!] Please set a password for your payment (6 digits): ");
        int passwd;
        passwd = 0;
        while (true) {
            try {
                passwd = scanner.nextInt();
                if (passwd < 100000 || passwd > 999999) {
                    System.out.println("[-] Invalid password. Please enter a 6-digit number.");
                    continue;
                }

                break;
            } catch (Exception e) {
                System.out.println("[-] Invalid input. Please enter a valid 6-digit number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        ConsumingPay pay = new ConsumingPay(passwd);
        System.out.println("[*] Consuming pay system setup complete. You can now bind your card and start making payments.");
        pay.bind(new Card(5176883292773435L, "Pie", 123456, 1000), 123456);
        pay.bind(new Card(4716749673493150L, "Ivanuschenko", 654321, 500), 123456);     // Wrong password bind
        pay.bind(new Card(5460656282761478L, "Brustein", 111111, 10000), 111111);
        System.out.println("[*] All cards have been processed. You can now make payments using the bound cards.");
        System.out.println("[*] Attempting to pay 200 using the bound cards...");
        if (pay.pay(200)) {
            System.out.println("[*] Payment successful.");
        } else {
            System.out.println("[-] Payment failed. Please check your card balances and try again.");
        }
        System.out.println("[*] Attempting to pay 100000 using the bound cards...");
        if (pay.pay(100000)) {
            System.out.println("[*] Payment successful.");
        } else {
            System.out.println("[-] Payment failed. Please check your card balances and try again.");
        }
        System.out.println("[*] Attempting to pay 2000 using the bound cards...");
        if (pay.pay(2000)) {
            System.out.println("[*] Payment successful.");
        } else {
            System.out.println("[-] Payment failed. Please check your card balances and try again.");
        }
        System.out.println("[+] Recharging 500 to Pie's card...");
        try {
            pay.cards.get(0).recharge(5000);
        } catch (Card.InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("[*] Attempting to pay 2000 using the bound cards after recharge...");
        if (pay.pay(2000)) {
            System.out.println("[*] Payment successful.");
        } else {
            System.out.println("[-] Payment failed. Please check your card balances and try again.");
        }
        System.out.println("[*] Consuming pay system demonstration complete. Thank you for using our service!");
    }

    ConsumingPay(int password) {
        this.password = password;
    }

    public void bind(Card card, int password) {
        if (card.verify(password)) {
            cards.add(card);
            System.out.println("[*] Card binding successful for card holder: " + card.getCardHolder());
        } else {
            System.out.println("[-] Incorrect password. Card binding failed.");
        }
    }

    public boolean pay(int amount) {
        int inputPassword;
        System.out.print("[*] Please enter the password to proceed with the payment: ");
        inputPassword = 0;
        while (true) {
            try {
                inputPassword = scanner.nextInt();
                if (inputPassword < 100000 || inputPassword > 999999) {
                    System.out.print("[-] Invalid password. Please enter a 6-digit number: ");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.print("[-] Invalid input. Please enter a valid 6-digit number: ");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        if (inputPassword != this.password) {
            System.out.println("[-] Incorrect password. Payment failed.");
            return false;
        }
        for (Card card : cards) {
            try {
                card.pay(amount);
                System.out.println("[*] Payment of " + amount + " successful using card: " + card.getCardHolder());
                System.out.println("[*] Remaining balance on card: " + card.getCardHolder() + " is updated. Currently is " + card.getCardBalance());
                return true;
            } catch (Card.InsufficientBalanceException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }
}

interface CardOperation {

    void pay(int amount);   // 消费

    void recharge(int amount);  // 存钱

    boolean verify(int password);  // 验证密码
}

class Card implements CardOperation {

    private long cardId;
    private String cardHolder;
    private int cardPass;
    private int cardBalance;

    class InsufficientBalanceException extends RuntimeException {

        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    class InvalidAmountException extends RuntimeException {

        public InvalidAmountException(String message) {
            super(message);
        }
    }

    Card(long cardId, String cardHolder, int cardPass, int cardBalance) {
        this.cardId = cardId;
        this.cardHolder = cardHolder;
        this.cardPass = cardPass;
        this.cardBalance = cardBalance;
    }

    @Override
    public void pay(int amount) {
        if (amount > cardBalance) {
            throw new InsufficientBalanceException("[-] Insufficient balance. Current balance: " + cardBalance + ", attempted payment: " + amount);
        }
        cardBalance -= amount;
        System.out.println("[*] Payment of " + amount + " successful. Remaining balance: " + cardBalance);
    }

    @Override
    public void recharge(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("[-] Invalid recharge amount: " + amount + ". Amount must be greater than zero.");
        }
        cardBalance += amount;
        System.out.println("[*] Recharge of " + amount + " successful. Current balance: " + cardBalance);
    }

    @Override
    public boolean verify(int password) {
        return this.cardPass == password;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public int getCardBalance() {
        return cardBalance;
    }
}
