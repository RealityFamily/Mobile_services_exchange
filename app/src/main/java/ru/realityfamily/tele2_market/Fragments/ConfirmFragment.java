package ru.realityfamily.tele2_market.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.realityfamily.tele2_market.MainActivity;
import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class ConfirmFragment extends Fragment {

    Transaction transaction;

    Button mMyOrdersBtn;
    FloatingActionButton mCheckBtn;

    TextView mType;
    TextView mPrice;
    TextView mAmount;
    TextView mSumm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.confirm_order, container, false);

        mCheckBtn = view.findViewById(R.id.CheckBTN);
        mCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        mMyOrdersBtn = view.findViewById(R.id.MyOrders);
        mMyOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                MainActivity.mTabLayout.getTabAt(3).select();
            }
        });

        mType = view.findViewById(R.id.type);
        switch (transaction.type) {
            case Buy:
                mType.setText("Цена покупки");
                break;
            case Sell:
                mType.setText("Цена продажи");
                break;
        }

        mPrice = view.findViewById(R.id.price);
        mSumm = view.findViewById(R.id.summ);
        switch (transaction.order) {
            case Market:
                mPrice.setText((transaction.type == Transaction.Type.Buy ? "не выше " : "не ниже ") +
                        transaction.price.toString() + "₽");
                mSumm.setText((transaction.type == Transaction.Type.Buy ? "до " : "от ") +
                        transaction.summ.toString() + "₽");
                break;
            case Limited:
                mPrice.setText(Integer.toString(transaction.price) + "₽");
                mSumm.setText(Integer.toString(transaction.summ) + "₽");
                break;
        }
        mAmount = view.findViewById(R.id.amount);
        mAmount.setText(Integer.toString(transaction.amount) + " шт.");

        return view;
    }

    public void AddInfo(Transaction transaction) {
        this.transaction = transaction;
    }
}
