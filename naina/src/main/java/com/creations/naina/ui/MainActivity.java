package com.creations.naina.ui;

import android.os.Bundle;
import android.util.Log;

import com.creations.naina.R;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerContract;
import com.example.application.base.BaseActivity;
import com.example.application.utils.MVVMInjector;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.naina.utils.FragmentHelper.getContainerFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        ContainerContract.InteractionListener, MVVMInjector {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, String.valueOf(getIntent().getExtras()));
        CanvasP preset = new CanvasP();
        setStatusBarColor(R.color.red);
        Fragment containerFragment = getContainerFragment(preset, MainActivity.this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_pager, containerFragment).commit();
    }
}
