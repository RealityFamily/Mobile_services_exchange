package ru.realityfamily.tele2_market.Structures;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.realityfamily.tele2_market.R;

public class Market {
    public final static String MARKET_KEY = "MARKET";

    public Integer gigabyte_buy;
    public Integer gigabyte_sell;
    public List<Integer> gigabyte_history;
    public List<CupElement> gigabyte_cup;

    public Integer minute_buy;
    public Integer minute_sell;
    public List<Integer> minute_history;
    public List<CupElement> minute_cup;

    public Integer sms_buy;
    public Integer sms_sell;
    public List<Integer> sms_history;
    public List<CupElement> sms_cup;

    public Market() {
        Random rnd = new Random();

        gigabyte_buy = rnd.nextInt(5) + 15;
        gigabyte_sell = gigabyte_buy + rnd.nextInt(10) + 5;
        gigabyte_history = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            gigabyte_history.add(gigabyte_buy + rnd.nextInt(20) * (rnd.nextBoolean() ? 1 : -1));
        }
        gigabyte_cup = new ArrayList<>();
        int delt = (gigabyte_sell - gigabyte_buy) / 2;
        gigabyte_cup.add(new CupElement());

        gigabyte_cup.add(new CupElement(gigabyte_sell + delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));
        gigabyte_cup.add(new CupElement(gigabyte_sell + delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        gigabyte_cup.add(new CupElement(gigabyte_sell, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1)));
        gigabyte_cup.add(new CupElement(gigabyte_sell - delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        gigabyte_cup.add(new CupElement(gigabyte_sell - delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));

        gigabyte_cup.add(new CupElement(gigabyte_buy + delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));
        gigabyte_cup.add(new CupElement(gigabyte_buy + delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6), 0));
        gigabyte_cup.add(new CupElement(gigabyte_buy, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1), 0));
        gigabyte_cup.add(new CupElement(gigabyte_buy - delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6), 0));
        gigabyte_cup.add(new CupElement(gigabyte_buy - delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));


        minute_buy = rnd.nextInt(5) + 40;
        minute_sell = minute_buy + rnd.nextInt(10) + 5;
        minute_history = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            minute_history.add(minute_buy + rnd.nextInt(20) * (rnd.nextBoolean() ? 1 : -1));
        }
        minute_cup = new ArrayList<>();
        delt = (minute_sell - minute_buy) / 2;
        minute_cup.add(new CupElement());

        minute_cup.add(new CupElement(minute_sell + delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));
        minute_cup.add(new CupElement(minute_sell + delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        minute_cup.add(new CupElement(minute_sell, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1)));
        minute_cup.add(new CupElement(minute_sell - delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        minute_cup.add(new CupElement(minute_sell - delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));

        minute_cup.add(new CupElement(minute_buy + delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));
        minute_cup.add(new CupElement(minute_buy + delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6), 0));
        minute_cup.add(new CupElement(minute_buy, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1), 0));
        minute_cup.add(new CupElement(minute_buy - delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6), 0));
        minute_cup.add(new CupElement(minute_buy - delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));


        sms_buy = rnd.nextInt();
        sms_sell = sms_buy + rnd.nextInt(10);
        sms_history = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            sms_history.add(sms_buy + rnd.nextInt(20) * (rnd.nextBoolean() ? 1 : -1));
        }
        sms_cup = new ArrayList<>();
        delt = (sms_sell - sms_buy) / 2;
        sms_cup.add(new CupElement());

        sms_cup.add(new CupElement(sms_sell + delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));
        sms_cup.add(new CupElement(sms_sell + delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        sms_cup.add(new CupElement(sms_sell, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1)));
        sms_cup.add(new CupElement(sms_sell - delt, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6)));
        sms_cup.add(new CupElement(sms_sell - delt * 2, 0, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11)));

        sms_cup.add(new CupElement(sms_buy + delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));
        sms_cup.add(new CupElement(sms_buy + delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6), 0));
        sms_cup.add(new CupElement(sms_buy, (rnd.nextInt(50) + 100) / (rnd.nextInt(5) + 1), 0));
        sms_cup.add(new CupElement(sms_buy - delt, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 6  ), 0));
        sms_cup.add(new CupElement(sms_buy - delt * 2, (rnd.nextInt(50) + 100) / (rnd.nextInt(10) + 11), 0));
    }

    public static Market GetFromMemory(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferences), Context.MODE_PRIVATE);

        String json = sp.getString(MARKET_KEY, "");
        Market out = new Gson().fromJson(json, Market.class);
        return out;
    }

    public static void LoadToMemory(Context context, Market market) {
        SharedPreferences sp = context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferences), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        String json = new Gson().toJson(market);
        editor.putString(MARKET_KEY, json);
        editor.commit();
    }
}
