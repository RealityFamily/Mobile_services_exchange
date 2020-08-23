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
}
