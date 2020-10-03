package com.creations.naina.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

  public static List<List<String>> readFromTsv(final InputStream systemResourceAsStream) throws IOException {
    List<List<String>> records = new ArrayList<>();
    if (systemResourceAsStream != null) {
      final InputStreamReader templateResource = new InputStreamReader(systemResourceAsStream);
      try (BufferedReader br = new BufferedReader(templateResource)) {
        String line;
        while ((line = br.readLine()) != null) {
          String[] values = line.split("\t");
          records.add(Arrays.asList(values));
        }
      }
    }
    return records;
  }

  public static String readFromAssets(final Context context, final String inputFileName) {
    try {
      InputStream is = context.getAssets().open(inputFileName);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      return new String(buffer, "UTF-8").replace("\n", "");
    } catch (Exception e) {

    }
    return "";
  }
}
