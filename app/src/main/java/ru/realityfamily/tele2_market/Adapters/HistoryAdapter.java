package ru.realityfamily.tele2_market.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Transaction.Status status;
    List<Transaction> mItems;

    public HistoryAdapter(List<Transaction> mItems, Transaction.Status status) {
        this.mItems = mItems;
        this.status = status;

        for (int i = 0; i < this.mItems.size(); i++) {
            if (this.mItems.get(i).status != this.status) {
                this.mItems.remove(i);
            }
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        switch (mItems.get(position).unit) {
            case gigabytes:
                holder.mLogo.setImageResource(R.drawable.ic_internet);
                break;
            case minutes:
                holder.mLogo.setImageResource(R.drawable.ic_call_empty);
                break;
            case sms:
                holder.mLogo.setImageResource(R.drawable.ic_messages);
                break;
        }

        switch (mItems.get(position).type) {
            case Buy:
                holder.mStatus.setText("Покупка " + mItems.get(position).amount.toString() + " лотов");
                holder.mSumm.setText("-" + mItems.get(position).summ.toString() + "₽");
                break;
            case Sell:
                holder.mStatus.setText("Продажа " + mItems.get(position).amount.toString() + " лотов");
                holder.mSumm.setText("+" + mItems.get(position).summ.toString() + "₽");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mLogo;
        TextView mStatus;
        TextView mSumm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mLogo = itemView.findViewById(R.id.logo);
            mStatus = itemView.findViewById(R.id.status);
            mSumm = itemView.findViewById(R.id.summ);
        }
    }
}
