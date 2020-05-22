package com.creations.inception.ui;

import android.os.Bundle;
import android.util.Log;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Board;
import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.add.AddContract;
import com.creations.inception.App;
import com.creations.inception.R;
import com.creations.inception.ui.form.RequestContract;
import com.creations.inception.ui.form.RequestFragment;
import com.creations.tools.callback.ListResponseCallback;
import com.creations.tools.model.APIResponseBody;
import com.example.application.base.BaseActivity;
import com.example.application.fragments.HomePagerAdapter;
import com.example.application.utils.DisabledViewPager;
import com.example.application.utils.MVVMInjector;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.inception.utils.FragmentHelper.getRequestFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        RequestContract.InteractionListener, MVVMInjector {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HomePagerAdapter mPagerAdapter;
    private DisabledViewPager mViewPager;
    @NonNull
    private final List<Fragment> mFragmentList = new ArrayList<>();
    @Inject
    IAPIBlocks mApiChat;

    private RequestFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragmentList);
//        Crashlytics.sharedInstance().crash();
        Log.d(TAG, String.valueOf(getIntent().getExtras()));
        App.getInstance().getFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.PURCHASE, getIntent().getExtras());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.main_pager);
        mViewPager.setAdapter(mPagerAdapter);
        Bundle bundle = getIntent().getExtras();
        retrieveFragments(bundle);
    }

    private void retrieveFragments(Bundle bundle) {
        mApiChat.getBoards(new ListResponseCallback<Board>() {
            @Override
            public void onSuccess(@NonNull List<Board> response) {
                runOnUiThread(()-> {
                    Preset preset = new Preset(response);
                    preset.setColorResId(AddContract.ViewModel.COLOR_NORMAL);
                    Fragment hostFrag = getViewPagerFragmentById(1, 1);
                    mFragmentList.add(hostFrag != null ? hostFrag : getRequestFragment(preset,MainActivity.this));
                    setStatusBarColor(R.color.black);
                    mPagerAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }
        });
    }

    @Override
    public void setStatusBarColr(int colorResId) {
        setStatusBarColor(colorResId);
    }

}
