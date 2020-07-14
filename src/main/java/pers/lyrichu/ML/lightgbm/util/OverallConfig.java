package pers.lyrichu.ML.lightgbm.util;

import java.util.Map;


public class OverallConfig extends ConfigBase {

  public IOConfig io_config = new IOConfig();

  public void set(Map<String, String> params) {
    io_config.set(params);
  }
}
