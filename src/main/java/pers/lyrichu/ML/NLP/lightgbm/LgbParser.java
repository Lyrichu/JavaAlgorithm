package pers.lyrichu.ML.NLP.lightgbm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import pers.lyrichu.ML.NLP.lightgbm.model.Boosting;


/**
 * Created by lwj on 2017/8/25.
 */
public class LgbParser {

    public Predictor parse(String filePath) throws IOException {
        File modelFile = new File(filePath);
        if(!modelFile.exists()|| !modelFile.isFile()) {
            throw new IOException(filePath + " is not a file path.");
        }
        Predictor predictor = null;
        try{
            Boosting boosting = Boosting.createBoosting(filePath);
            Map<String, String> map = new HashMap<String, String>();
            OverallConfig config = new OverallConfig();
            config.set(map);
            predictor =
                    new Predictor(boosting, config.io_config.num_iteration_predict, config.io_config.is_predict_raw_score,
                            config.io_config.is_predict_leaf_index, config.io_config.pred_early_stop,
                            config.io_config.pred_early_stop_freq, config.io_config.pred_early_stop_margin);
        }catch (Exception e){
            throw new IOException(filePath + "LGB MODEL LOAD ERROR not a file path.");

        }

        return predictor;
    }
    public Map<String, Integer> parseFeatureMap(String filePath)throws IOException {
        File modelFile = new File(filePath);
        if(!modelFile.exists()|| !modelFile.isFile()) {
            throw new IOException(filePath + " is not a fea file path.");
        }
        Map<String, Integer> featureMap = new HashMap<>(1024);
        try{
            List<String> lines = IOUtils.readLines(new FileInputStream(filePath));
            for (String line : lines) {
                String[] pair = line.trim().split("\t");
                if(pair.length>1){
                    featureMap.put(pair[0], Integer.valueOf(pair[1]));
                }else {
                    throw new Exception("feature format exception "+line);
                }
            }
        }catch (Exception e){
            throw new IOException(filePath + "LGB MODEL LOAD ERROR not a file path.");
        }
        return featureMap;
    }
}
