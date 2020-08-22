package ru.realityfamily.tele2_market.Structures;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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

    public Map<Integer, Integer> gigabyte_history;
    public Map<Integer, Integer> minute_history;
    public Map<Integer, Integer> sms_history;

    public Client () {

        gigabytes = new Random().nextInt(50);
        minutes = new Random().nextInt(2000);
        sms = new Random().nextInt(1500);

        sold_gigabytes = 0;
        sold_minutes = 0;
        sold_sms = 0;

        balance = new Random().nextInt(1500);
        income = new Random().nextInt(20);

        gigabyte_history = new HashMap<>();
        minute_history = new HashMap<>();
        sms_history = new HashMap<>();
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
                gigabyte_history.put(amount, -summ);
                break;

            case minutes:
                minutes += amount;
                balance -= summ;
                minute_history.put(amount, -summ);
                break;

            case sms:
                sms += amount;
                balance -= summ;
                sms_history.put(amount, -summ);
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
                gigabyte_history.put(amount, -summ);
                break;

            case minutes:
                minutes += Math.abs(amount);
                minute_history.put(amount, -summ);
                break;

            case sms:
                sms += Math.abs(amount);
                sms_history.put(amount, -summ);
                break;
        }

        LoadToMemory(context, this);
    }
}
