import java.util.*;

public class BankApp {
    static double balance = 1000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Bank App ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("Balance: " + balance);
            }

            else if (choice == 2) {
                System.out.print("Enter deposit amount: ");
                double amount = sc.nextDouble();

                balance = amount; // BUG: should add amount to balance
                System.out.println("Deposit successful!");
            }

            else if (choice == 3) {
                System.out.print("Enter withdraw amount: ");
                double amount = sc.nextDouble();

                if (amount > balance) {
                    System.out.println("Not enough balance!");
                }

                balance = balance - amount; // BUG: still withdraws even if balance is low
                System.out.println("Withdraw successful!");
            }

            else if (choice == 4) {
                System.out.println("Bye!");
                break;
            }

            else {
                System.out.println("Invalid option");
            }
        }

        sc.close();
    }
}
