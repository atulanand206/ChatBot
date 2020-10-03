package com.experiment.billing.service;

import com.experiment.billing.model.components.Configuration;
import com.experiment.billing.model.components.Page;
import com.experiment.billing.model.dto.Permit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BillService {

    public static void convert(final List<List<String>> lists,
                               final Configuration configuration,
                               final String outputFileName) {
        try {
            List<Page> pages = getPages(lists, configuration);
            new BillWriter(configuration, outputFileName, pages).writeContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Page> getPages(final List<List<String>> lists,
                                      final Configuration configuration) {
        List<Permit> permits = new ArrayList<>();
        for (int i = 1; i < lists.size(); i++) {
            List<String> item = lists.get(i);
            permits.add(new Permit(item));
        }
        permits.forEach(System.out::println);
        List<Page> pages = PermitToBillConvertor.convert(permits, 1, configuration);
        for (Page page : pages) {
            page.calculateTotals(configuration);
        }
        return pages;
    }
}
