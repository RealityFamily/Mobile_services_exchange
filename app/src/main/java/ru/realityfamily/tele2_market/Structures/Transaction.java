package ru.realityfamily.tele2_market.Structures;

import java.util.Date;
import java.util.Random;

public class Transaction {
    public enum Status {
        Active,
        Closed
    }
    public enum Type {
        Buy,
        Sell
    }

    public Type type;
    public Date date;
    public Status status;
    public Client.Unit unit;
    public Integer amount;
    public Integer summ;

    public Transaction (Type type, Client.Unit unit, Integer amount, Integer summ) {
        this.date = new Date();
        this.type = type;
        switch (type) {
            case Buy:
                status = Status.Closed;
                break;

            case Sell:
                status = new Random().nextBoolean() ? Status.Active : Status.Closed;
                break;
        }
        this.unit = unit;
        this.amount = amount;
        this.summ = summ;
    }
}
