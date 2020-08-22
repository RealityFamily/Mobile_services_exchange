package ru.realityfamily.tele2_market.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.CupElement;
import ru.realityfamily.tele2_market.Structures.Market;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Client.Unit mUnit;
    private Context mContext;

    public RecyclerViewAdapter(Client.Unit mUnit) {
        this.mUnit = mUnit;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        switch (viewType){
            case 0:
                return new ViewHolderHeader(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header, parent, false));
            case 1:
                return new ViewHolderCup(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cup, parent, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolderHeader viewHolder0 = (ViewHolderHeader)holder;

                switch (mUnit) {
                    case gigabytes:
                        viewHolder0.mUnit.setText("1 ГБ");
                        viewHolder0.mUnitHave.setText("Доступно ГБ");

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).gigabytes.toString());

                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).gigabyte_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).gigabyte_sell + "₽");
                        break;
                    case minutes:
                        viewHolder0.mUnit.setText("50 Мин");
                        viewHolder0.mUnitHave.setText("Доступно Мин");

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).minutes.toString());
                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).minute_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).minute_sell + "₽");
                        break;
                    case sms:
                        viewHolder0.mUnit.setText("1 SMS");
                        viewHolder0.mUnitHave.setText("Доступно SMS");

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).sms.toString());
                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).sms_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).sms_sell + "₽");
                        break;
                }
                viewHolder0.mBalance.setText(Client.GetFromMemory(mContext).balance.toString() + "₽");
                break;
            case 1:
                ViewHolderCup viewHolder1 = (ViewHolderCup)holder;

                viewHolder1.mRecyclerView.setNestedScrollingEnabled(false);
                viewHolder1.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                switch (mUnit) {
                    case gigabytes:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).gigabyte_cup));
                        break;
                    case minutes:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).minute_cup));
                        break;
                    case sms:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).sms_cup));
                        break;
                }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView mUnit;
        TextView mUnitHave;
        TextView mAmount;
        TextView mBalance;
        TextView mBuyPrice;
        TextView mSellPrice;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);

            mUnit = itemView.findViewById(R.id.unit);
            mUnitHave = itemView.findViewById(R.id.unitHave);
            mAmount = itemView.findViewById(R.id.amount);
            mBalance = itemView.findViewById(R.id.balance);
            mBuyPrice = itemView.findViewById(R.id.buy_price);
            mSellPrice = itemView.findViewById(R.id.sell_price);
        }
    }

    public class ViewHolderCup extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;

        public ViewHolderCup(@NonNull View itemView) {
            super(itemView);

            mRecyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
