package ru.realityfamily.tele2_market.Structures;

import android.content.Context;

import java.util.Date;
import java.util.Random;

import ru.realityfamily.tele2_market.Fragments.MarketPriceFragment;

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
    public MarketPriceFragment.Order order;
    public Integer amount;
    public Integer price;
    public Integer summ;

    public Transaction (Type type, Client.Unit unit, MarketPriceFragment.Order order, Integer amount, Integer price, Integer summ) {
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
        this.order = order;
        this.amount = amount;
        this.price = price;
        this.summ = summ;
    }

    public static void newTransaction(Context context, Transaction transaction) {
        Market market = Market.GetFromMemory(context);
        switch (transaction.type) {
            case Buy:
                switch (transaction.unit) {
                    case gigabytes:
                        boolean end = false;
                        for(int i = 0; i < market.gigabyte_cup.size() && !end; i++) {
                            if (market.gigabyte_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.gigabyte_cup.size()) {
                                            if (market.gigabyte_cup.get(i).sell_orders == 0) {
                                                i--;
                                                continue;
                                            }
                                            CupElement cup = market.gigabyte_cup.get(i);
                                            cup.sell_orders -= 1;
                                            market.gigabyte_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true; }
                                        else {transaction.status = Status.Active; end = true; }
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.gigabyte_cup.get(i).sell_orders > 0) {
                                                CupElement cup = market.gigabyte_cup.get(i);
                                                cup.sell_orders -= 1;
                                                market.gigabyte_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.gigabyte_cup.get(i);
                                                cup.buy_orders += 1;
                                                market.gigabyte_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                    case minutes:
                        end = false;
                        for(int i = 0; i < market.minute_cup.size() && !end; i++) {
                            if (market.minute_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.minute_cup.size()) {
                                            if (market.minute_cup.get(i).sell_orders == 0) {
                                                i--;
                                                continue;
                                            }
                                            CupElement cup = market.minute_cup.get(i);
                                            cup.sell_orders -= 1;
                                            market.minute_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true; }
                                        else {transaction.status = Status.Active; end = true; }
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.minute_cup.get(i).sell_orders > 0) {
                                                CupElement cup = market.minute_cup.get(i);
                                                cup.sell_orders -= 1;
                                                market.minute_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.minute_cup.get(i);
                                                cup.buy_orders += 1;
                                                market.minute_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                    case sms:
                        end = false;
                        for(int i = 0; i < market.sms_cup.size() && !end; i++) {
                            if (market.sms_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.sms_cup.size()) {
                                            if (market.sms_cup.get(i).sell_orders == 0) {
                                                i--;
                                                continue;
                                            }
                                            CupElement cup = market.sms_cup.get(i);
                                            cup.sell_orders -= 1;
                                            market.sms_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true;}
                                        else {transaction.status = Status.Active; end = true; }
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.sms_cup.get(i).sell_orders > 0) {
                                                CupElement cup = market.sms_cup.get(i);
                                                cup.sell_orders -= 1;
                                                market.sms_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.sms_cup.get(i);
                                                cup.buy_orders += 1;
                                                market.sms_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                }
                break;
            case Sell:
                switch (transaction.unit) {
                    case gigabytes:
                        boolean end = false;
                        for(int i = 0; i < market.gigabyte_cup.size() && !end; i++) {
                            if (market.gigabyte_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.gigabyte_cup.size()) {
                                            if (market.gigabyte_cup.get(i).buy_orders == 0) {
                                                i++;
                                                continue;
                                            }
                                            CupElement cup = market.gigabyte_cup.get(i);
                                            cup.buy_orders -= 1;
                                            market.gigabyte_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true;}
                                        else {transaction.status = Status.Active; end = true;}
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.gigabyte_cup.get(i).buy_orders > 0) {
                                                CupElement cup = market.gigabyte_cup.get(i);
                                                cup.buy_orders -= 1;
                                                market.gigabyte_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.gigabyte_cup.get(i);
                                                cup.sell_orders += 1;
                                                market.gigabyte_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                    case minutes:
                        end = false;
                        for(int i = 0; i < market.minute_cup.size() && !end; i++) {
                            if (market.minute_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.minute_cup.size()) {
                                            if (market.minute_cup.get(i).buy_orders == 0) {
                                                i++;
                                                continue;
                                            }
                                            CupElement cup = market.minute_cup.get(i);
                                            cup.buy_orders -= 1;
                                            market.minute_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true; }
                                        else {transaction.status = Status.Active; end = true; }
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.minute_cup.get(i).buy_orders > 0) {
                                                CupElement cup = market.minute_cup.get(i);
                                                cup.buy_orders -= 1;
                                                market.minute_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.minute_cup.get(i);
                                                cup.sell_orders += 1;
                                                market.minute_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                    case sms:
                        end = false;
                        for(int i = 0; i < market.sms_cup.size() && !end; i++) {
                            if (market.sms_cup.get(i).price.equals(transaction.price)) {
                                switch (transaction.order) {
                                    case Market:
                                        transaction.summ = 0;
                                        int j = transaction.amount;
                                        while (j > 0 && i < market.sms_cup.size())  {
                                            if (market.sms_cup.get(i).buy_orders == 0) {
                                                i++;
                                                continue;
                                            }
                                            CupElement cup = market.sms_cup.get(i);
                                            cup.buy_orders -= 1;
                                            market.sms_cup.set(i, cup);
                                            transaction.summ += cup.price;
                                            j--;
                                        }

                                        if (j == 0) { transaction.status = Status.Closed; end = true; }
                                        else {transaction.status = Status.Active; end = true; }
                                        break;
                                    case Limited:
                                        boolean flag = false;
                                        transaction.summ = 0;
                                        for (j = transaction.amount; j > 0; j--) {
                                            if (market.sms_cup.get(i).buy_orders > 0) {
                                                CupElement cup = market.sms_cup.get(i);
                                                cup.buy_orders -= 1;
                                                market.sms_cup.set(i, cup);
                                                transaction.summ += cup.price;
                                            } else {
                                                flag = true;
                                                CupElement cup = market.sms_cup.get(i);
                                                cup.sell_orders += 1;
                                                market.sms_cup.set(i, cup);
                                            }
                                        }
                                        if (flag) {transaction.status = Status.Active; end = true; }
                                        else {transaction.status = Status.Closed; end = true; }
                                        break;
                                }
                            }
                        }
                        break;
                }
                break;
        }
        Market.LoadToMemory(context, market);

        Client client = Client.GetFromMemory(context);

        client.history.add(transaction);
        if (transaction.status == Status.Closed) {
            switch (transaction.type) {
                case Buy:
                    switch (transaction.unit) {
                        case gigabytes:
                            client.gigabytes += transaction.amount;
                            break;
                        case minutes:
                            client.minutes += transaction.amount * 50;
                            break;
                        case sms:
                            client.sms += transaction.amount * 50;
                            break;
                    }
                    client.balance -= transaction.summ;
                    client.income -= transaction.summ;
                    break;
                case Sell:
                    switch (transaction.unit) {
                        case gigabytes:
                            client.gigabytes -= transaction.amount;
                            client.sold_gigabytes += transaction.amount;
                            break;
                        case minutes:
                            client.minutes -= transaction.amount * 50;
                            client.sold_minutes += transaction.amount * 50;
                            break;
                        case sms:
                            client.sms -= transaction.amount * 50;
                            client.sold_sms += transaction.amount * 50;
                            break;
                    }
                    client.balance += transaction.summ;
                    client.income += transaction.summ;
                    break;
            }
        }
        Client.LoadToMemory(context, client);
    }
}
