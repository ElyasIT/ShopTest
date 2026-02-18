/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

/**
 *
 * @author elyas
 */
public class Client extends Person implements Payable {

    private int memberId;
    private Amount balance;

    private static final int MEMBER_ID = 456;
    private static final double INITIAL_BALANCE = 50.00;

    public Client(String name) {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = new Amount(INITIAL_BALANCE);
    }

    @Override
    public boolean pay(Amount amount) {
        double finalBalance = this.balance.getValue() - amount.getValue();
        if (finalBalance >= 0) {
            this.balance.setValue(finalBalance);
            return true;
        } else {
            return false;
        }
    }
    

    

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Cliente: " + name + " | Saldo: " + balance;
    }

}
