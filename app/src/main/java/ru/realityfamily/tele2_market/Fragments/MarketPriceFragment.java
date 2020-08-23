package ru.realityfamily.tele2_market.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class MarketPriceFragment extends Fragment {

    public enum Order {
        Market,
        Limited
    }
    public Order order = Order.Market;
    public Transaction.Type type = Transaction.Type.Buy;
    public Client.Unit unit = Client.Unit.gigabytes;
    public Integer price = 0;

    Integer summ = 0;

    TextView mType;
    TextView mT1;
    TextView mT2;
    TextView mT3;
    EditText mPrice;
    TextView mT4;
    EditText mAmount;
    TextView mSumm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.order_input, container, false);

        mType = view.findViewById(R.id.Type);
        mT1 = view.findViewById(R.id.t1);
        mT2 = view.findViewById(R.id.t2);
        mT3 = view.findViewById(R.id.t3);
        mPrice = view.findViewById(R.id.price);
        mT4 = view.findViewById(R.id.t4);
        mAmount = view.findViewById(R.id.amount);
        mSumm = view.findViewById(R.id.summ);

        switch (type) {
            case Buy:
                mType.setText("Покупка");
                break;
            case Sell:
                mType.setText("Продажа");
                break;
        }

        switch (order){
            case Market:
                mT1.setText("по рыночной цене, ");
                mT2.setText(type == Transaction.Type.Buy ? "не выше" : "не ниже");
                mT3.setText(" указанной");

                mPrice.setEnabled(false);
                break;
            case Limited:
                mT1.setText("по указанной цене");
                mT2.setText("");
                mT3.setText("");
                break;
        }

        mPrice.setText(Integer.toString(price));

        switch (unit) {
            case gigabytes:
                mT4.setText("1 лот = 1 ГБ");
                break;
            case minutes:
                mT4.setText("1 лот = 50 Мин");
                break;
            case sms:
                mT4.setText("1 лот = 50 SMS");
                break;
        }

        if (!mPrice.getText().toString().equals("") && !mAmount.getText().toString().equals("")) {
            summ = Integer.parseInt(mPrice.getText().toString()) * Integer.parseInt(mAmount.getText().toString());
        }
        switch (type) {
            case Buy:
                mSumm.setText("- " + Integer.toString(summ) + "₽");
                break;
            case Sell:
                mSumm.setText("+ " + Integer.toString(summ) + "₽");
                break;
        }


        mPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mPrice.getText().toString().equals("") && !mAmount.getText().toString().equals("")) {
                    summ = Integer.parseInt(mPrice.getText().toString()) * Integer.parseInt(mAmount.getText().toString());
                }
                switch (type) {
                    case Buy:
                        mSumm.setText("- " + Integer.toString(summ) + "₽");
                        break;
                    case Sell:
                        mSumm.setText("+ " + Integer.toString(summ) + "₽");
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mPrice.getText().toString().equals("") && !mAmount.getText().toString().equals("")) {
                    summ = Integer.parseInt(mPrice.getText().toString()) * Integer.parseInt(mAmount.getText().toString());
                }
                switch (type) {
                    case Buy:
                        mSumm.setText("- " + Integer.toString(summ) + "₽");
                        break;
                    case Sell:
                        mSumm.setText("+ " + Integer.toString(summ) + "₽");
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    public void AddInfo(Order order, Transaction.Type type, Client.Unit unit, Integer price) {
        this.order = order;
        this.type = type;
        this.unit = unit;
        this.price = price;
    }

    public Transaction GetOrder() {
        return new Transaction(type, unit, order, Integer.parseInt(mAmount.getText().toString()), price, summ);
    }
}
