package ru.realityfamily.tele2_market.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import ru.realityfamily.tele2_market.Adapters.RecyclerViewAdapter;
import ru.realityfamily.tele2_market.MainActivity;
import ru.realityfamily.tele2_market.R;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.Market;
import ru.realityfamily.tele2_market.Structures.Transaction;

public class GigFragment extends Fragment {
    ExtendedFloatingActionButton BuyBtn;
    ExtendedFloatingActionButton SellBtn;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gig_page, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(Client.Unit.gigabytes));

        BuyBtn = view.findViewById(R.id.buyBTN);
        final OrderFragment fragment = new OrderFragment();
        fragment.AddInfo(Client.Unit.gigabytes, Transaction.Type.Buy,
                Market.GetFromMemory(getContext()).gigabyte_buy, 0,
                Client.GetFromMemory(getContext()).balance);

        BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Frame, fragment).commit();
                MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        SellBtn = view.findViewById(R.id.sellBTN);
        final OrderFragment fragment1 = new OrderFragment();
        fragment1.AddInfo(Client.Unit.gigabytes, Transaction.Type.Sell,
                Market.GetFromMemory(getContext()).gigabyte_sell, 0,
                Client.GetFromMemory(getContext()).balance);

        SellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.Frame, fragment1).commit();
                MainActivity.mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(Client.Unit.gigabytes));
    }
}
