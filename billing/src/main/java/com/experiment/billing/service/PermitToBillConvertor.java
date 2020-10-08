package com.experiment.billing.service;

import com.experiment.billing.constants.Constants;
import com.experiment.billing.model.components.Client;
import com.experiment.billing.model.components.Configuration;
import com.experiment.billing.model.components.Page;
import com.experiment.billing.model.components.Particular;
import com.experiment.billing.model.components.Target;
import com.experiment.billing.model.components.Trip;
import com.experiment.billing.model.dto.Permit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.text.WordUtils;

public class PermitToBillConvertor {

  public static List<Page> convert(
      final List<Permit> permits,
      final int invoiceBegin,
      final Configuration configuration) {
    permits.sort(Comparator.comparing(Permit::getIssuedOn));
    List<Page> pages = new ArrayList<>();
    Page page = null;
    int pageNo = invoiceBegin;
    for (Permit permit : permits) {
      Particular particular = convert(permit, configuration);
      if (page == null) {
        page = new Page(permit.getIssuedOn(), pageNo++);
      } else {
        if (page.getLatestParticular() != null && !page.canParticularBeAdded(particular)) {
          pages.add(page);
          page = new Page(permit.getIssuedOn(), pageNo++);
        }
      }
      page.addParticular(particular);
    }
    return pages;
  }

  public static Particular convert(
      final Permit permit,
      final Configuration configuration) {
    return new Particular(
        permit.getIssuedOn(), permit.getTruckRegNo(),
        permit.getPassNo(),
        "",
        permit.getMineralWt(),
        permit.getDestination(),
        convertTarget(permit, configuration),
        convertTrip(permit, configuration));
  }

  private static Trip convertTrip(
      final Permit permit,
      final Configuration configuration) {
    String from = configuration.getEntity().getFrom();
    String to = permit.getDestination();
    return new Trip(from, to);
  }

  public static Target convertTarget(final Permit permit, final Configuration configuration) {
    String consignee = permit.getConsignee();
    String[] split = consignee.split("[(]");
    String[] props = split[0].split(" PROP ");
    String id = getId(split);
    String gstin = getGstin(id, configuration);
    String billTo = getCapitalized(props[0]);
    String destination = getCapitalized(permit.getDestination());
    return new Target(billTo, destination, id, gstin, Constants.STATE_NAME, Constants.STATE_CODE);
  }

  private static String getGstin(final String id, final Configuration configuration) {
    List<Client> clients = configuration.getClients();
    if (clients != null) {
      for (Client client : clients) {
        if (id.equals(client.getId()))
          return client.getGstin();
      }
    }
    return "";
  }

  public static String getCapitalized(final String destination) {
    return WordUtils.capitalize(destination.replaceAll("[,.!?;:]", "$0 ").replaceAll("\\s+", " ").toLowerCase());
  }

  public static String getId(String[] split) {
    String gstin = "";
    if (split.length>1) {
      String[] split1 = split[1].split("[)]");
      gstin = split1[0];
    }
    return gstin;
  }
}
