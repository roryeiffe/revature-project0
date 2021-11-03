package com.company.transaction;

public class Transaction {
    // id of this transaction:
    private int id;
    // id of accounts (not user) involved in the transaction:
    private int donor_id;
    private int recip_id;
    // amount of money to be transferred:
    private int amount;
    // status of the transaction (pending, accepted, cancelled, etc.):
    private String status;

    // constructor that just takes donor and recipient ids and amount:
    public Transaction(int donor_id, int recip_id, int amount){
        this.donor_id = donor_id;
        this.recip_id = recip_id;
        this.amount = amount;
    }

    // constructor that takes all fields:
    public Transaction(int id, int donor_id, int recip_id, int amount, String status) {
        this.id = id;
        this.donor_id = donor_id;
        this.recip_id = recip_id;
        this.amount = amount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(int donor_id) {
        this.donor_id = donor_id;
    }

    public int getRecip_id() {
        return recip_id;
    }

    public void setRecip_id(int recip_id) {
        this.recip_id = recip_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", donor_id=" + donor_id +
                ", recip_id=" + recip_id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
