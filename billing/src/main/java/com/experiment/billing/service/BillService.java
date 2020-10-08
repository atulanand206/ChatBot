package com.experiment.billing.service;

import com.experiment.billing.model.components.Client;
import com.experiment.billing.model.components.Configuration;
import com.experiment.billing.model.components.Page;
import com.experiment.billing.model.components.Particular;
import com.experiment.billing.model.components.Target;
import com.experiment.billing.model.dto.Permit;
import com.itextpdf.kernel.PdfException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BillService {

    public static void convert(final List<Page> pages,
                               final Configuration configuration,
                               final String outputFileName) {
        try {
            new BillWriter(configuration, outputFileName, pages).writeContent();
        } catch (IOException | PdfException e) {
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

    public static Set<Client> getClients(final List<Page> pages, final Configuration configuration) {
        Set<Client> clients = new HashSet<>();
        Set<String> ids = new HashSet<>();
        for (Page page : pages) {
            for (Particular particular : page.getParticulars()) {
                Target target = particular.getTarget();
                String id = target.getId().trim();
                String billTo = target.getBillTo();
                String gstin = target.getGstin();
                if (!ids.contains(id)) {
                    ids.add(id);
                    clients.add(new Client(billTo, id, gstin));
                }
            }
        }
        return clients;
    }
}
