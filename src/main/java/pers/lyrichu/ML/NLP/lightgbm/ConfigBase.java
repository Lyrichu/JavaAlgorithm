package pers.lyrichu.ML.NLP.lightgbm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lwj
 */
public abstract class ConfigBase {
    private static final Logger logger = LoggerFactory.getLogger(ConfigBase.class);

    public abstract void set(Map<String, String> params);

    String getString(Map<String, String> params, String name) {
        return params.get(name);
    }
    Integer getInt(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Integer.valueOf(s);
    }

    Double getDouble(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Double.valueOf(s);
    }

    Boolean getBool(Map<String, String> params, String name) {
        String s = params.get(name);
        if (s == null)
            return null;
        return Boolean.valueOf(s);
    }

    static Map<String, String> str2Map(String parameters) {
        Map<String, String> params = new HashMap<String, String>();
        List<String> args = Common.split(parameters, " \t\n\r");
        for (String arg : args) {
            List<String> tmp_strs = Common.split(arg, '=');
            if (2 == tmp_strs.size()) {
                String key = Common.removeQuotationSymbol(tmp_strs.get(0).trim());
                String value = Common.removeQuotationSymbol(tmp_strs.get(1).trim());
                if (key.length() <= 0) {
                    continue;
                }
                params.put(key, value);
            } else if (arg.trim().length() > 0) {
                logger.warn("Unknown parameter " + arg);
            }
        }
        ParameterAlias.keyAliasTransform(params);
        return params;
    }
}
