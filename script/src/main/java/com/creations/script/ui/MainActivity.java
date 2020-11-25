package com.creations.script.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.creations.script.R;
import com.creations.script.models.CanvasP;
import com.creations.script.ui.container.ContainerContract;
import com.creations.script.ui.container.ContainerFragment;
import com.example.application.base.BaseActivity;
import com.example.application.utils.MVVMInjector;

import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.script.utils.FragmentHelper.getContainerFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        ContainerContract.InteractionListener, MVVMInjector {

  private FrameLayout mFrameLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mFrameLayout = findViewById(R.id.main_pager);
    CanvasP preset = new CanvasP();
    getSupportFragmentManager().beginTransaction().add(R.id.main_pager,
            getContainerFragment(preset, MainActivity.this)).commit();
  }
}