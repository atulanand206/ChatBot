package com.experiment.billing.utils;

import com.experiment.billing.model.components.Configuration;

public class JacksonUtils {

  public static Configuration readJSON(){
    return   new Configuration();
//    return mapper.readValue(TestUtils.readFromJson("invoice.json").replace("\n", ""), Configuration.class);
  }
}
