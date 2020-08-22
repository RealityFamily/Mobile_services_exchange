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
import ru.realityfamily.tele2_market.Structures.CupElement;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String mUnit;
    private Context mContext;

    public RecyclerViewAdapter(String mUnit) {
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

                viewHolder0.mUnit.setText("1 " + mUnit);
                viewHolder0.mUnitHave.setText("Доступно " + mUnit);

                break;
            case 1:
                ViewHolderCup viewHolder1 = (ViewHolderCup)holder;

                List<CupElement> Items = new ArrayList<>();
                for (int i = 60; i > 10; i -= 5) {
                    Items.add(new CupElement(i, (int)(Math.random()*100), (int)(Math.random()*100)));
                }

                viewHolder1.mRecyclerView.setNestedScrollingEnabled(false);
                viewHolder1.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Items));
        }
    }

    @Override
    public int getItemCount() {
        return 2;
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

    public class ViewHolderCup extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;

        public ViewHolderCup(@NonNull View itemView) {
            super(itemView);

            mRecyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
