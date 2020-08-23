package ru.realityfamily.tele2_market.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
                return new ViewHolderGrapf(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.grapf, parent, false));
            case 2:
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

                        int delt = Market.GetFromMemory(mContext).gigabyte_buy -
                                Market.GetFromMemory(mContext).gigabyte_history.get(Market.GetFromMemory(mContext).gigabyte_history.size() - 2);
                        if (delt > 0) {
                            viewHolder0.mVector.setText("↑ " + Integer.toString(Math.abs(delt)) + " (15.43 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Buy));
                        } else {
                            viewHolder0.mVector.setText("↓ " + Integer.toString(Math.abs(delt)) + " (6.19 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Sell));
                        }

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).gigabytes.toString() + " ГБ");
                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).gigabyte_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).gigabyte_sell + "₽");
                        break;
                    case minutes:
                        viewHolder0.mUnit.setText("50 Мин");

                        int delt1 = Market.GetFromMemory(mContext).minute_buy -
                                Market.GetFromMemory(mContext).minute_history.get(Market.GetFromMemory(mContext).minute_history.size() - 2);
                        if (delt1 > 0) {
                            viewHolder0.mVector.setText("↑ " + Integer.toString(Math.abs(delt1)) + " (15.43 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Buy));
                        } else {
                            viewHolder0.mVector.setText("↓ " + Integer.toString(Math.abs(delt1)) + " (6.19 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Sell));
                        }

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).minutes.toString() + " Мин");
                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).minute_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).minute_sell + "₽");
                        break;
                    case sms:
                        viewHolder0.mUnit.setText("50 SMS");

                        int delt2 = Market.GetFromMemory(mContext).sms_buy -
                                Market.GetFromMemory(mContext).sms_history.get(Market.GetFromMemory(mContext).sms_history.size() - 2);
                        if (delt2 > 0) {
                            viewHolder0.mVector.setText("↑ " + Integer.toString(Math.abs(delt2)) + " (15.43 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Buy));
                        } else {
                            viewHolder0.mVector.setText("↓ " + Integer.toString(Math.abs(delt2)) + " (6.19 %)");
                            viewHolder0.mVector.setTextColor(mContext.getColor(R.color.Sell));
                        }

                        viewHolder0.mAmount.setText(Client.GetFromMemory(mContext).sms.toString() + " SMS");
                        viewHolder0.mBuyPrice.setText("Покупка: " + Market.GetFromMemory(mContext).sms_buy + "₽");
                        viewHolder0.mSellPrice.setText("Продажа: " + Market.GetFromMemory(mContext).sms_sell + "₽");
                        break;
                }
                viewHolder0.mBalance.setText(Client.GetFromMemory(mContext).balance.toString() + "₽");
                break;

            case 1:
                ViewHolderGrapf viewHolder2 = (ViewHolderGrapf)holder;

                viewHolder2.mChart.getDescription().setEnabled(false);

                // enable touch gestures
                viewHolder2.mChart.setTouchEnabled(true);
                viewHolder2.mChart.setDragEnabled(true);
                viewHolder2.mChart.setDrawGridBackground(true);
                viewHolder2.mChart.setHighlightPerDragEnabled(true);


                // set an alternative background color
                viewHolder2.mChart.setBackgroundColor(mContext.getColor(R.color.Background));
                viewHolder2.mChart.setGridBackgroundColor(mContext.getColor(R.color.Background));

                Legend l = viewHolder2.mChart.getLegend();
                l.setEnabled(false);

                XAxis xAxis = viewHolder2.mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(10f);
                xAxis.setTextColor(mContext.getColor(R.color.Text));
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(true);
                xAxis.setCenterAxisLabels(true);
                xAxis.setGranularity(1f); // 24 hour
                xAxis.setValueFormatter(new ValueFormatter() {
                    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
                    @Override
                    public String getFormattedValue(float value) {

                        long millis = TimeUnit.DAYS.toMillis((long) value);
                        return mFormat.format(new Date(millis));
                    }
                });

                YAxis rightAxis = viewHolder2.mChart.getAxisRight();
                rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
                rightAxis.setTextColor(ColorTemplate.getHoloBlue());
                rightAxis.setDrawGridLines(true);
                rightAxis.setGranularityEnabled(true);
                rightAxis.setYOffset(-9f);
                rightAxis.setTextColor(mContext.getColor(R.color.Text));

                YAxis leftAxis = viewHolder2.mChart.getAxisLeft();
                leftAxis.setEnabled(false);

                viewHolder2.mChart.setOnChartValueSelectedListener(viewHolder2);
                switch (mUnit) {
                    case gigabytes:
                        setData(viewHolder2.mChart, Market.GetFromMemory(mContext).gigabyte_history);
                        break;
                    case minutes:
                        setData(viewHolder2.mChart, Market.GetFromMemory(mContext).minute_history);
                        break;
                    case sms:
                        setData(viewHolder2.mChart, Market.GetFromMemory(mContext).sms_history);
                        break;

                }
                viewHolder2.mChart.invalidate();
                break;

            case 2:
                ViewHolderCup viewHolder1 = (ViewHolderCup)holder;

                viewHolder1.mRecyclerView.setNestedScrollingEnabled(false);
                viewHolder1.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                switch (mUnit) {
                    case gigabytes:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).gigabyte_cup, Client.Unit.gigabytes));
                        break;
                    case minutes:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).minute_cup, Client.Unit.minutes));
                        break;
                    case sms:
                        viewHolder1.mRecyclerView.setAdapter(new CupAdapter(Market.GetFromMemory(mContext).sms_cup, Client.Unit.sms));
                        break;
                }
        }
    }

    private void setData(LineChart chart, List<Integer> in_values) {

        // now in hours
        long now = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
        long days = 7;
        long day = 1;

        ArrayList<Entry> values = new ArrayList<>();

        // count = hours
        float from = now - days;

        int index = 0;
        // increment by 1 hour
        for (float x = from; x < now && index < in_values.size(); x += day) {
            values.add(new Entry(x, in_values.get(index))); // add one entry per hour
            index++;
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(true);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        set1.setDrawFilled(true);

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextColor(mContext.getColor(R.color.Background));
        data.setValueTextSize(9f);


        // set data
        chart.setData(data);

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView mUnit;
        TextView mAmount;
        TextView mBalance;
        TextView mBuyPrice;
        TextView mSellPrice;
        TextView mVector;

        public ViewHolderHeader(@NonNull View itemView) {
            super(itemView);

            mUnit = itemView.findViewById(R.id.unit);
            mAmount = itemView.findViewById(R.id.amount);
            mBalance = itemView.findViewById(R.id.balance);
            mBuyPrice = itemView.findViewById(R.id.buy_price);
            mSellPrice = itemView.findViewById(R.id.sell_price);
            mVector = itemView.findViewById(R.id.vector);
        }
    }

    public class ViewHolderCup extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;

        public ViewHolderCup(@NonNull View itemView) {
            super(itemView);

            mRecyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }

    public class ViewHolderGrapf extends RecyclerView.ViewHolder implements OnChartValueSelectedListener {

        TextView mInfo;
        LineChart mChart;

        public ViewHolderGrapf(@NonNull View itemView) {
            super(itemView);

            mInfo = itemView.findViewById(R.id.value);
            mChart = itemView.findViewById(R.id.chart);
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            mInfo.setText("Покупка: " + Integer.toString(Math.round(h.getY())) + "₽");
        }

        @Override
        public void onNothingSelected() {
            mInfo.setText("");
        }
    }
}
