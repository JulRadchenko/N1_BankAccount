import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.IntStream;

public class BankAccount {

    public static void main(String[] args){

        System.out.println("Bank accounts information:");

        Person oliver = new Person("Oliver");
        oliver.balance = 13000;
        oliver.opening_date = LocalDateTime.of(2020, 9, 1, 8, 0, 0);
        oliver.is_blocked = false;
        oliver.setNumber();
        oliver.print();

        Person olivia = new Person("Olivia");
        olivia.balance = 110500;
        olivia.opening_date = LocalDateTime.of(2026, 2, 20, 11, 31, 40);
        olivia.is_blocked = false;
        olivia.setNumber();
        olivia.print();

        Person sophia = new Person("Sophia");
        sophia.balance = 0;
        sophia.opening_date = LocalDateTime.of(2013,1,1,9,13,12);
        sophia.is_blocked = true;
        sophia.setNumber();
        sophia.print();

        System.out.println("\nTransaction: DEPOSIT");
        oliver.deposit(1000);
        oliver.print();
        sophia.deposit(2000);
        sophia.print();

        System.out.println("\nTransaction: WITHDRAW");
        oliver.withdraw(2000);
        oliver.print();
        olivia.withdraw(120000);
        olivia.print();
        sophia.withdraw(1000);
        sophia.print();

        System.out.println("\nTransaction: TRANSFER");
        oliver.transfer(2000,oliver,olivia);
        oliver.print();
        olivia.print();
        olivia.transfer(120000,olivia,oliver);
        olivia.print();
        oliver.print();
        sophia.transfer(1000,sophia,oliver);
        sophia.print();
        oliver.print();
        oliver.transfer(1000,oliver,sophia);
        oliver.print();
        sophia.print();

        System.out.println("\n[Equals]");
        System.out.println("Olivia and Oliver: " + olivia.equals(oliver));
    }

    static class Person{

        String name;
        int balance;
        LocalDateTime opening_date;
        boolean is_blocked;
        String number;
        boolean is_successful;
        String message;

        public Person(String name){
            this.name = name;
        }

        void setNumber(){

            Random random = new Random();
            StringBuilder accountNumber = new StringBuilder();

            IntStream intStream = random.ints(8, 0, 10);
            intStream.forEach(digit -> {accountNumber.append(digit);});

            this.number = accountNumber.toString();
        }

        void print(){
            System.out.printf("[Name: %s] :\t[Number: %s |\tBalance: %d |\tOpening date: %s |\tIs blocked: %b]\n",
                    name, number, balance, opening_date, is_blocked);
        }

        void deposit(int amount){
            if(!is_blocked) {
                balance += amount;
                is_successful = true;
            }
            else {
                is_successful = false;
                message = "The bank account is blocked";
            }
            System.out.println(name + "'s transaction is " + is_successful);
            if(message != null){
                System.out.println(message);
            }
        }

        void withdraw(int amount){
            if(!is_blocked && amount<=balance){
                balance -= amount;
                is_successful = true;
            }
            else if(is_blocked){
                is_successful = false;
                message = "The bank account is blocked";
            }
            else{
                is_successful = false;
                message = "The amount exceeds the balance";
            }
            System.out.println(name + "'s transaction is " + is_successful);
            if(message != null){
                System.out.println(message);
            }
        }

        void transfer(int amount, Person from, Person to){
            if(!from.is_blocked && !to.is_blocked && from.balance >= amount){
                from.balance -= amount;
                to.balance += amount;
                is_successful = true;
            }
            else if(to.is_blocked){
                is_successful = false;
                message = "The recipient's bank account is blocked";
            }
            else if(from.is_blocked){
                is_successful = false;
                message = "The sender's bank account is blocked";
            }
            else{
                is_successful = false;
                message = "The amount exceeds the balance";
            }
            System.out.println(name + "'s transaction is " + is_successful);
            if(message != null){
                System.out.println(message);
            }
        }
    }
}
