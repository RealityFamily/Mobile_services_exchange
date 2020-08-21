package ru.realityfamily.tele2_market.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.realityfamily.tele2_market.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String mUnit;

    public RecyclerViewAdapter(String mUnit) {
        this.mUnit = mUnit;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new ViewHolderHeader(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.header, parent, false));
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
                ViewHolderHeader viewHolder = (ViewHolderHeader)holder;

                viewHolder.mUnit.setText("1 " + mUnit);
                viewHolder.mUnitHave.setText("Доступно " + mUnit);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView mUnit;
        TextView mUnitHave;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);

            mUnit = itemView.findViewById(R.id.unit);
            mUnitHave = itemView.findViewById(R.id.unitHave);
        }
    }
}
