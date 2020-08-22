package ru.realityfamily.tele2_market.Structures;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ru.realityfamily.tele2_market.R;

public class Client {
    public static final String CLIENT_KEY = "CLIENT";

    public enum Unit {
        gigabytes,
        minutes,
        sms
    }

    public Integer gigabytes;
    public Integer minutes;
    public Integer sms;

    public Integer sold_gigabytes;
    public Integer sold_minutes;
    public Integer sold_sms;

    public Integer balance;
    public Integer income;

    public List<Transaction> history;

    public Client () {

        gigabytes = new Random().nextInt(50);
        minutes = new Random().nextInt(2000);
        sms = new Random().nextInt(1500);

        sold_gigabytes = 0;
        sold_minutes = 0;
        sold_sms = 0;

        balance = new Random().nextInt(1500);
        income = 0;

        history = new ArrayList<>();
        history.add(new Transaction(Transaction.Type.Buy, Unit.gigabytes, 2, 30));
        history.add(new Transaction(Transaction.Type.Sell, Unit.gigabytes, 6, 90));
        history.add(new Transaction(Transaction.Type.Buy, Unit.minutes, 1, 40));
        history.add(new Transaction(Transaction.Type.Sell, Unit.minutes, 5, 200));
        history.add(new Transaction(Transaction.Type.Sell, Unit.sms, 3, 120));

        for (Transaction transaction: history) {
            if (transaction.status == Transaction.Status.Closed) {
                if (transaction.type == Transaction.Type.Sell) {
                    switch (transaction.unit) {
                        case gigabytes:
                            sold_gigabytes += transaction.amount;
                            break;
                        case minutes:
                            sold_minutes += transaction.amount * 50;
                            break;
                        case sms:
                            sold_sms += transaction.amount * 50;
                            break;
                    }
                    income += transaction.summ;
                } else {
                    income -= transaction.summ;
                }
            }
        }

        income = sold_gigabytes + sold_minutes + sold_sms;
    }

    public static void LoadToMemory(Context context, Client client) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferences), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        String json = new Gson().toJson(client);
        editor.putString(CLIENT_KEY, json);
        editor.commit();
    }

    public static Client GetFromMemory(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferences), Context.MODE_PRIVATE);

        String json = sp.getString(CLIENT_KEY, "");
        Client out = new Gson().fromJson(json, Client.class);
        return out;
    }

    public void Buy(Context context, Unit unit, int amount, int summ) {
        switch (unit){
            case gigabytes:
                gigabytes += amount;
                balance -= summ;
                history.add(new Transaction(Transaction.Type.Buy, unit, amount, summ));
                break;

            case minutes:
                minutes += amount;
                balance -= summ;
                history.add(new Transaction(Transaction.Type.Buy, unit, amount, summ));
                break;

            case sms:
                sms += amount;
                balance -= summ;
                history.add(new Transaction(Transaction.Type.Buy, unit, amount, summ));
                break;
        }

        LoadToMemory(context, this);
    }

    public void Sell(Context context, Unit unit, int amount, int summ) {
        amount *= new Random().nextBoolean() ? 1 : -1;
        if (amount > 0) {
            income += summ;
            balance += summ;
        }

        switch (unit){
            case gigabytes:
                gigabytes -= Math.abs(amount);
                history.add(new Transaction(Transaction.Type.Sell, unit, amount, summ));
                break;

            case minutes:
                minutes += Math.abs(amount);
                history.add(new Transaction(Transaction.Type.Sell, unit, amount, summ));
                break;

            case sms:
                sms += Math.abs(amount);
                history.add(new Transaction(Transaction.Type.Sell, unit, amount, summ));
                break;
        }

        LoadToMemory(context, this);
    }
}
