package ru.realityfamily.tele2_market.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        switch (viewType){
            case 0:
                return new ViewHolderHeader(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.request_header, parent, false));

            case 1:
                return new ViewHolderHistory(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.history, parent, false));

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ViewHolderHeader viewHolder = (ViewHolderHeader) holder;

                viewHolder.mGigabyteHistory.setText(Client.GetFromMemory(mContext).sold_gigabytes.toString() + " ГБ");
                viewHolder.mMinutesHistory.setText(Client.GetFromMemory(mContext).sold_minutes.toString() + " Мин");
                viewHolder.mSMSHistory.setText(Client.GetFromMemory(mContext).sold_sms.toString() + " SMS");

                viewHolder.mIncome.setText(Client.GetFromMemory(mContext).income.toString() + "₽");
                break;

            case 1:
                ViewHolderHistory viewHolder1 = (ViewHolderHistory) holder;

                viewHolder1.mActive.setNestedScrollingEnabled(false);
                viewHolder1.mActive.setLayoutManager(new LinearLayoutManager(mContext));
                viewHolder1.mActive.setAdapter(new HistoryAdapter(Client.GetFromMemory(mContext).history, Transaction.Status.Active));

                viewHolder1.mClosed.setNestedScrollingEnabled(false);
                viewHolder1.mClosed.setLayoutManager(new LinearLayoutManager(mContext));
                viewHolder1.mClosed.setAdapter(new HistoryAdapter(Client.GetFromMemory(mContext).history, Transaction.Status.Closed));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView mGigabyteHistory;
        TextView mMinutesHistory;
        TextView mSMSHistory;
        TextView mIncome;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);

            mGigabyteHistory = itemView.findViewById(R.id.gigabyte_history);
            mMinutesHistory = itemView.findViewById(R.id.minutes_history);
            mSMSHistory = itemView.findViewById(R.id.sms_history);
            mIncome = itemView.findViewById(R.id.income);
        }
    }

    public class ViewHolderHistory extends RecyclerView.ViewHolder{

        RecyclerView mActive;
        RecyclerView mClosed;

        public ViewHolderHistory(@NonNull View itemView) {
            super(itemView);

            mActive = itemView.findViewById(R.id.ActiveList);
            mClosed = itemView.findViewById(R.id.ClosedList);
        }
    }
}
