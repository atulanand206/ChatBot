package com.creations.inception.ui;

import android.os.Bundle;
import android.util.Log;

import com.creations.condition.Preconditions;
import com.creations.inception.App;
import com.creations.inception.R;
import com.creations.inception.ui.form.RequestContract;
import com.creations.inception.ui.form.RequestFragment;
import com.example.application.base.BaseActivity;
import com.example.application.fragments.HomePagerAdapter;
import com.example.application.utils.DisabledViewPager;
import com.example.application.utils.MVVMInjector;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.inception.utils.FragmentHelper.getRequestFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        RequestContract.InteractionListener, MVVMInjector {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private HomePagerAdapter mPagerAdapter;
    private DisabledViewPager mViewPager;
    @NonNull
    private final List<Fragment> mFragmentList = new ArrayList<>();

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

    @Override
    protected void onNetworkStatusChanged(boolean isConnected) {

    }

    private void retrieveFragments(Bundle bundle) {

        Fragment hostFrag = getViewPagerFragmentById(1, 1);
        mFragmentList.add(hostFrag != null ? hostFrag : getRequestFragment(this));
        setStatusBarColor(R.color.black);
        mPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void setStatusBarColr(int colorResId) {
        setStatusBarColor(colorResId);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Inject
    @Nullable
    Map<Class<? extends Fragment>, Provider<AndroidInjector.Builder<? extends Fragment>>> mInjectorFactories;

    @NonNull
    @Override
    public <T extends Fragment, B extends AndroidInjector.Builder<T>> B getBuilder(Class<T> klass, Class<B> builderKlass) {
        Preconditions.verifyNonNull(mInjectorFactories, "InjectorFactoriesMap");
        Preconditions.verify(mInjectorFactories.containsKey(klass), "InjectorFactoriesContainsKlass");
        Provider<AndroidInjector.Builder<? extends Fragment>> provider = Preconditions.verifyNonNull(mInjectorFactories.get(klass), "BuilderForFragment");

        AndroidInjector.Builder<? extends Fragment> builder = provider.get();

        Preconditions.verify(builderKlass.isInstance(builder), "BuilderIsB");

        //noinspection unchecked
        return (B)builder;
    }

}
