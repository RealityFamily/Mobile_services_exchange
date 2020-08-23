package ru.realityfamily.tele2_market.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import ru.realityfamily.tele2_market.Adapters.ViewPagerAdapter;
import ru.realityfamily.tele2_market.MainActivity;
import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.Market;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class OrderFragment extends Fragment {
    Client.Unit unit = Client.Unit.gigabytes;
    Transaction.Type type = Transaction.Type.Buy;
    Integer balance = 0;
    Integer price = 0;
    Integer limPrice = 0;

    TextView mUnit;
    TextView mPrice;
    TextView mBalance;
    TextView mBalanceText;

    TabLayout mTabLayout;
    ViewPager mViewPager;

    ExtendedFloatingActionButton mConfirmBtn;

    public void AddInfo(Client.Unit unit, Transaction.Type type, Integer price, Integer limPrice, Integer balance) {
        this.unit = unit;
        this.type = type;
        this.balance = balance;
        this.price = price;
        this.limPrice = limPrice;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_page, container, false);

        mUnit = view.findViewById(R.id.unit);
        mPrice = view.findViewById(R.id.price);
        mBalance = view.findViewById(R.id.balance);
        mBalanceText = view.findViewById(R.id.balanceText);

        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.orderViewPager);

        final ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        MarketPriceFragment fragment = new MarketPriceFragment();
        fragment.AddInfo(MarketPriceFragment.Order.Market, type, unit, price);
        adapter.AddFragment(fragment, getResources().getString(R.string.MarketOrder));

        MarketPriceFragment fragment1 = new MarketPriceFragment();
        fragment1.AddInfo(MarketPriceFragment.Order.Limited, type, unit, limPrice);
        adapter.AddFragment(fragment1, getResources().getString(R.string.LimitedOrder));

        mViewPager.setAdapter(adapter);
        mViewPager.setSaveFromParentEnabled(false);
        mTabLayout.setupWithViewPager(mViewPager);

        if (limPrice != 0) {
            mTabLayout.getTabAt(1).select();
        }

        mConfirmBtn = view.findViewById(R.id.ConfirmBTN);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmFragment fragment = new ConfirmFragment();
                Transaction transaction = null;

                MarketPriceFragment page = (MarketPriceFragment) adapter.getItem(mTabLayout.getSelectedTabPosition());
                if (page != null) {
                    transaction = ((MarketPriceFragment)page).GetOrder();

                    Transaction.newTransaction(getContext(), transaction);

                    fragment.AddInfo(transaction);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, fragment).commit();
                }
            }
        });

        switch (unit) {
            case gigabytes:
                mUnit.setText("1 ГБ");
                break;
            case minutes:
                mUnit.setText("50 Мин");
                break;
            case sms:
                mUnit.setText("50 SMS");
                break;
        }

        switch (type) {
            case Buy:
                mBalance.setText(Client.GetFromMemory(getContext()).balance.toString() + "₽");
                mBalanceText.setText("Ваш баланс");
                break;
            case Sell:
                switch (unit) {
                    case gigabytes:
                        mBalance.setText(Client.GetFromMemory(getContext()).gigabytes.toString() + " ГБ");
                        break;
                    case minutes:
                        mBalance.setText(Client.GetFromMemory(getContext()).minutes.toString() + " Мин");
                        break;
                    case sms:
                        mBalance.setText(Client.GetFromMemory(getContext()).sms.toString() + " SMS");
                        break;
                }
                mBalanceText.setText("Доступно к продаже");
                break;
        }

        mPrice.setText(price.toString() + "₽");

        return view;
    }
}
