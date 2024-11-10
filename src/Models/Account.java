package Models;

import java.util.ArrayList;

public class Account {
    private String accno;
    private double balance;
    private ArrayList<String> transaction_ids;
    public Account(String accno, double balance) {
        this.accno = accno;
        this.balance = balance;
        this.transaction_ids = new ArrayList<>();
    }
    public String getAccno() {
        return accno;
    }
    public double getBalance() {
        return balance;
    }
    private void setBalance (double balance) {
        this.balance = balance;
    }
    public ArrayList<String> getTransaction_ids() {
        return transaction_ids;
    }
    public void addTransaction(Transaction transaction) {
        double money = transaction.getMoney();
        setBalance(money + balance);
        transaction_ids.add(transaction.getId());
    }


}
