package ru.realityfamily.tele2_market;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import ru.realityfamily.tele2_market.Adapters.ViewPagerAdapter;
import ru.realityfamily.tele2_market.Fragments.GigFragment;
import ru.realityfamily.tele2_market.Fragments.MinFragment;
import ru.realityfamily.tele2_market.Fragments.RequestFragment;
import ru.realityfamily.tele2_market.Fragments.SMSFragment;
import ru.realityfamily.tele2_market.Structures.Client;
import ru.realityfamily.tele2_market.Structures.Market;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    public static BottomSheetBehavior mBottomSheetBehavior;
    public static TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        viewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new GigFragment(), getResources().getString(R.string.Gigs));
        adapter.AddFragment(new MinFragment(), getResources().getString(R.string.Mins));
        adapter.AddFragment(new SMSFragment(), getResources().getString(R.string.SMSs));
        adapter.AddFragment(new RequestFragment(), getResources().getString(R.string.Requests));
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);

        Client.LoadToMemory(this, new Client());
        Market.LoadToMemory(this, new Market());
    }
}