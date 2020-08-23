package ru.realityfamily.tele2_market.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import ru.realityfamily.tele2_market.Fragments.OrderFragment;
import ru.realityfamily.tele2_market.MainActivity;
import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.CupElement;
import ru.realityfamily.tele2_market.Structures.Market;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class CupAdapter extends RecyclerView.Adapter<CupAdapter.ViewHolder> {

    List<CupElement> mItems;
    Client.Unit unit;
    Context mContext;

    public CupAdapter(List<CupElement> mItems, Client.Unit unit) {
        this.unit = unit;
        this.mItems = mItems;
        this.mItems.add(new CupElement());
        this.mItems.add(new CupElement());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cup_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position == 0) {
            holder.mPrice.setText("Цена");
            holder.mSellOrders.setText("Продажа");
            holder.mSellOrders.setBackgroundColor(Color.TRANSPARENT);
            holder.mSellOrders.setTextColor(ContextCompat.getColor(mContext, R.color.Sell));
            holder.mBuyOrders.setText("Покупка");
            holder.mBuyOrders.setBackgroundColor(Color.TRANSPARENT);
            holder.mBuyOrders.setTextColor(ContextCompat.getColor(mContext, R.color.Buy));
        }
        else if (position == mItems.size() - 2 || position == mItems.size() - 1) {
            holder.mPrice.setText("");
            holder.mSellOrders.setText("");
            holder.mBuyOrders.setText("");

            holder.mDownSep.setVisibility(View.INVISIBLE);
            holder.mUpSep.setVisibility(View.INVISIBLE);

            holder.mBuyOrders.setBackgroundColor(Color.TRANSPARENT);
            holder.mSellOrders.setClickable(false);

            holder.mSellOrders.setBackgroundColor(Color.TRANSPARENT);
            holder.mSellOrders.setClickable(false);
        }
        else {
            holder.mPrice.setText(mItems.get(position).getPrice().toString());
            holder.mSellOrders.setText(mItems.get(position).getSell_orders() != 0 ?
                    mItems.get(position).getSell_orders().toString() : "");
            holder.mBuyOrders.setText(mItems.get(position).getBuy_orders() != 0 ?
                    mItems.get(position).getBuy_orders().toString() : "");

            int width = mContext.getResources().getDisplayMetrics().widthPixels/3;

            holder.mPriceContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    width,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            holder.mBuyContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    width,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            holder.mSellContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    width,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            holder.mBuyOrders.setLayoutParams(new RelativeLayout.LayoutParams(
                    get_buy_width(mItems.get(position)),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            if (mItems.get(position).getBuy_orders() == 0) {
                holder.mBuyOrders.setBackgroundColor(Color.TRANSPARENT);
                holder.mSellOrders.setClickable(false);
            }
            holder.mBuyOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderFragment fragment = new OrderFragment();
                    switch (unit) {
                        case gigabytes:
                            fragment.AddInfo(Client.Unit.gigabytes, Transaction.Type.Sell,
                                    Market.GetFromMemory(mContext).gigabyte_sell, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                        case minutes:
                            fragment.AddInfo(Client.Unit.minutes, Transaction.Type.Sell,
                                    Market.GetFromMemory(mContext).minute_sell, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                        case sms:
                            fragment.AddInfo(Client.Unit.sms, Transaction.Type.Sell,
                                    Market.GetFromMemory(mContext).sms_sell, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                    }
                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, fragment).commit();
                    MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });


            holder.mSellOrders.setLayoutParams(new RelativeLayout.LayoutParams(
                    get_sell_width(mItems.get(position)),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            if (mItems.get(position).getSell_orders() == 0) {
                holder.mSellOrders.setBackgroundColor(Color.TRANSPARENT);
                holder.mSellOrders.setClickable(false);
            }
            holder.mSellOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderFragment fragment = new OrderFragment();
                    switch (unit) {
                        case gigabytes:
                            fragment.AddInfo(Client.Unit.gigabytes, Transaction.Type.Buy,
                                    Market.GetFromMemory(mContext).gigabyte_buy, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                        case minutes:
                            fragment.AddInfo(Client.Unit.minutes, Transaction.Type.Buy,
                                    Market.GetFromMemory(mContext).minute_buy, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                        case sms:
                            fragment.AddInfo(Client.Unit.sms, Transaction.Type.Buy,
                                    Market.GetFromMemory(mContext).sms_buy, mItems.get(position).getPrice(),
                                    Client.GetFromMemory(mContext).balance);
                            break;
                    }
                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.Frame, fragment).commit();
                    MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button mBuyOrders;
        Button mSellOrders;
        Button mPrice;

        RelativeLayout mPriceContainer;
        RelativeLayout mBuyContainer;
        RelativeLayout mSellContainer;

        ImageView mUpSep;
        ImageView mDownSep;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mBuyOrders = itemView.findViewById(R.id.buy_orders);
            mSellOrders = itemView.findViewById(R.id.sell_orders);
            mPrice = itemView.findViewById(R.id.price);

            mPriceContainer = itemView.findViewById(R.id.price_container);
            mBuyContainer = itemView.findViewById(R.id.buy_container);
            mSellContainer = itemView.findViewById(R.id.sell_container);

            mUpSep = itemView.findViewById(R.id.upSep);
            mDownSep = itemView.findViewById(R.id.downSep);
        }
    }

    int get_max_buy() {
        int result = 0;
        for (CupElement i : mItems) {
            if (result < i.getBuy_orders()) {
                result = i.getBuy_orders();
            }
        }
        return result;
    }

    int get_max_sell() {
        int result = 0;
        for (CupElement i : mItems) {
            if (result < i.getSell_orders()) {
                result = i.getSell_orders();
            }
        }
        return result;
    }

    Integer get_buy_width(CupElement mItem) {
        return mItem.getBuy_orders() * 100 / get_max_buy() + 150;
    }

    Integer get_sell_width(CupElement mItem) {
        return mItem.getSell_orders() * 100 / get_max_sell() + 150;
    }
}
