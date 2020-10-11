package com.creations.naina.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.creations.naina.R;
import com.creations.naina.models.CanvasP;
import com.creations.naina.models.FileUploadType;
import com.creations.naina.services.SessionContext;
import com.creations.naina.ui.container.ContainerContract;
import com.creations.naina.ui.container.ContainerFragment;
import com.example.application.base.BaseActivity;
import com.example.application.utils.MVVMInjector;
import com.experiment.billing.model.components.Client;
import com.experiment.billing.model.components.Configuration;
import com.experiment.billing.model.components.Page;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static com.creations.naina.models.FileUploadType.FILE_UPLOAD_TYPE;
import static com.creations.naina.utils.FileUtils.readFromTsv;
import static com.creations.naina.utils.FragmentHelper.getContainerFragment;
import static com.experiment.billing.service.BillService.*;
import static com.experiment.billing.service.BillService.getClients;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        ContainerContract.InteractionListener, MVVMInjector {
  private static final String TAG = MainActivity.class.getSimpleName();

  @Inject
  Gson gson;
  @Inject
  SessionContext sessionContext;
  private RelativeLayout mProgressBar;
  private FrameLayout mFrameLayout;
  ContainerFragment containerFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mProgressBar = findViewById(R.id.progress_bar);
    mFrameLayout = findViewById(R.id.main_pager);
    requestStoragePermission();
    Log.d(TAG, String.valueOf(getIntent().getExtras()));
    CanvasP preset = new CanvasP();
    setStatusBarColor(R.color.red);
    containerFragment = (ContainerFragment) getContainerFragment(preset, MainActivity.this);
    getSupportFragmentManager().beginTransaction().add(R.id.main_pager, containerFragment).commit();
    externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    outputFileName = externalStoragePublicDirectory + "/result.pdf";
  }

  File externalStoragePublicDirectory;

  public void toggleProgress(boolean show) {
    if (show) {
      mFrameLayout.setVisibility(View.GONE);
      mProgressBar.setVisibility(View.VISIBLE);
    } else {
      mFrameLayout.setVisibility(View.VISIBLE);
      mProgressBar.setVisibility(View.GONE);
    }
  }

  @Override
  public void onDocumentEventClicked(String fileName) {
    toggleProgress(true);
    showToast("Converting!");
    outputFileName = externalStoragePublicDirectory + "/" + fileName + ".pdf";
    Log.d(TAG, outputFileName);
    convertToPdf();
    toggleProgress(false);
    showToast("Finished! Please open documents folder.");
  }

  @Override
  public void onSaveConfig(String text) {
    writeConfigurations();
  }

  @Override
  public void onLoadConfig(String toString) {
    readConfigurations();
  }

  @Override
  public void onUploadEventClicked() {
    showFileChooser(FileUploadType.PERMITS);
  }

  private static final int FILE_SELECT_CODE = 0;
  private String outputFileName;
  private List<List<String>> lists = new ArrayList<>();

  private void showFileChooser(FileUploadType type) {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
    intent.setType("*/*");
    intent.putExtra(FILE_UPLOAD_TYPE, type.name());
    intent.addCategory(Intent.CATEGORY_OPENABLE);

    try {
      startActivityForResult(
              Intent.createChooser(intent, "Select a File to Upload"),
              FILE_SELECT_CODE);
    } catch (android.content.ActivityNotFoundException ex) {
      // Potentially direct the user to the Market with a Dialog
      Toast.makeText(this, "Please install a File Manager.",
              Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
      processData(data);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  private void processData(Intent data) {
    try {
      String fileType = data.getStringExtra(FILE_UPLOAD_TYPE);
      Uri uri = data.getData();
      if (uri != null) {
          lists.clear();
          lists.addAll(readFromTsv(getContentResolver().openInputStream(uri)));
          setClients();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  List<Page> pages;

  private void setClients() {
    Configuration configuration;
    configuration = sessionContext.getConfig().getConfiguration();
    pages = getPages(lists, configuration);
    Set<Client> clients = getClients(pages, configuration);
    List<Client> clientsList = new ArrayList<>(clients);
    Collections.sort(clientsList, (o1, o2) -> o1.getClient().compareTo(o2.getClient()));
    configuration.setClients(clientsList);
    sessionContext.setConfiguration(configuration);
    containerFragment.setFileName(String.format("Document Attached with %d permits", lists.size()));
  }

  private void convertToPdf() {
    convert(pages, sessionContext.getConfig().getConfiguration(), outputFileName);
    Log.d(TAG, "PDF");
  }

  private void readConfigurations() {
    containerFragment.loadMainLayout();
  }

  private void writeConfigurations() {
    String data = gson.toJson(sessionContext.getConfig());
    writeConfigurationsToFile(data);
  }

  private void writeConfigurationsToFile(String data) {
    try {
      String path = String.format("content://%s/%s",externalStoragePublicDirectory, "config.txt");
//      Uri parse = Uri.fromFile(new File(path));
      Uri parse = Uri.parse(path);
      Log.d(TAG, parse.getPath());
      ParcelFileDescriptor parcelFileDescriptor = getApplicationContext().getContentResolver().openFileDescriptor(parse, "r", null);
      if (parcelFileDescriptor == null)
        return;
      FileOutputStream outputStreamWriter = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStreamWriter);
      bufferedOutputStream.write(data.getBytes());
      bufferedOutputStream.close();
    }
    catch (IOException e) {
      Log.e("Exception", "File write failed: " + e.toString());
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {

    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  private static final int STORAGE_PERMISSION_CODE = 1000;
  private static final int REQUEST_CHECK_STORAGE_SETTINGS = 1001;

  private void requestStoragePermission() {
    EasyPermissions.requestPermissions(
            new PermissionRequest.Builder(this, STORAGE_PERMISSION_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .setRationale(getString(R.string.storage_rationale))
                    .setPositiveButtonText("ok")
                    .setNegativeButtonText("cancel")
                    .setTheme(R.style.Rationale)
                    .build());
  }
}
