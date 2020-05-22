package com.creations.naina.ui;

import android.os.Bundle;
import android.util.Log;

import com.creations.naina.R;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerContract;
import com.example.application.base.BaseActivity;
import com.example.application.fragments.HomePagerAdapter;
import com.example.application.utils.DisabledViewPager;
import com.example.application.utils.MVVMInjector;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.naina.utils.FragmentHelper.getContainerFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        ContainerContract.InteractionListener, MVVMInjector {
    private static final String TAG = MainActivity.class.getSimpleName();

    private HomePagerAdapter mPagerAdapter;
    private DisabledViewPager mViewPager;
    @NonNull
    private final List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, String.valueOf(getIntent().getExtras()));
//        App.getInstance().getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.PURCHASE, getIntent().getExtras());
        Bundle bundle = getIntent().getExtras();
        CanvasP preset = new CanvasP();
        Fragment hostFrag = getViewPagerFragmentById(0, 0);
        mFragmentList.add(hostFrag != null ? hostFrag : getContainerFragment(preset,MainActivity.this));
        mFragmentList.add(hostFrag != null ? hostFrag : getContainerFragment(preset,MainActivity.this));
        mFragmentList.add(hostFrag != null ? hostFrag : getContainerFragment(preset,MainActivity.this));
        setStatusBarColor(R.color.black);

        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager = findViewById(R.id.main_pager);
        mViewPager.setAdapter(mPagerAdapter);
    }
}
