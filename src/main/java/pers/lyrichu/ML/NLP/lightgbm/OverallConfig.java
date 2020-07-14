package pers.lyrichu.ML.NLP.lightgbm;

import java.util.Map;

/**
 * @author lwj
 */
public class OverallConfig extends ConfigBase {
    public IOConfig io_config = new IOConfig();

    public void set(Map<String, String> params) {
        io_config.set(params);
    }
}
