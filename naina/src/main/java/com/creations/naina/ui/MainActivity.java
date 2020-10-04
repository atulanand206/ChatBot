package com.creations.naina.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.creations.naina.R;
import com.creations.naina.api.ConfigurationRepository;
import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerContract;
import com.example.application.base.BaseActivity;
import com.example.application.utils.MVVMInjector;
import com.experiment.billing.model.components.Configuration;
import com.experiment.billing.service.BillService;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.HasSupportFragmentInjector;

import static com.creations.naina.utils.FileUtils.readFromAssets;
import static com.creations.naina.utils.FileUtils.readFromTsv;
import static com.creations.naina.utils.FragmentHelper.getContainerFragment;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector,
        ContainerContract.InteractionListener, MVVMInjector {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, String.valueOf(getIntent().getExtras()));
        CanvasP preset = new CanvasP();
        setStatusBarColor(R.color.red);
        IConfigurationRepository configurationRepository = new ConfigurationRepository(this, gson);
        Fragment containerFragment = getContainerFragment(preset, MainActivity.this, configurationRepository);
        getSupportFragmentManager().beginTransaction().add(R.id.main_pager, containerFragment).commit();
        externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        outputFileName = externalStoragePublicDirectory + "/result.pdf";
    }
    File externalStoragePublicDirectory;

    @Override
    public void onDocumentEventClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(externalStoragePublicDirectory.toString());
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent, "Documents"));
    }

    @Override
    public void onUploadEventClicked() {
        showFileChooser();
    }

    private static final int FILE_SELECT_CODE = 0;
    private String outputFileName;
    private List<List<String>> lists = new ArrayList<>();

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
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
            Uri uri = data.getData();
            if (uri != null) {
                lists.addAll(readFromTsv(getContentResolver().openInputStream(uri)));
                convertToPdf();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertToPdf() {
        Configuration configuration = gson.fromJson(readFromAssets(this, "ies.json"), Configuration.class);
        BillService.convert(lists, configuration, outputFileName);
    }
}
