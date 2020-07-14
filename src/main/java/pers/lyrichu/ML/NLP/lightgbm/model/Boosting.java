package pers.lyrichu.ML.NLP.lightgbm.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.ML.NLP.lightgbm.PredictionEarlyStopInstance;
import pers.lyrichu.ML.NLP.lightgbm.SparseVector;

/**
 * @author lwj
 */
public abstract class Boosting implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Boosting.class);
    private static final long serialVersionUID = -3370589073161617590L;

    public static Boosting createBoosting(
        String filename) throws FileNotFoundException, IOException {
        String type = getBoostingTypeFromModelFile(filename);
        Boosting boosting = null;
        if (type.equals("tree")) {
            boosting = new GBDT();
        } else {
            logger.error("unknown submodel type in model file " + filename);
        }
        loadFileToBoosting(boosting, filename);
        return boosting;
    }

    static Boosting createBoosting(
        String type, String filename) throws FileNotFoundException, IOException {
        if (filename == null || filename.length() == 0) {
            if (type.equals("gbdt")) {
                return new GBDT();
            } else if (type.equals("dart")) {
                return new DART();
            } else if (type.equals("goss")) {
                return new GOSS();
            } else {
                return null;
            }
        } else {
            Boosting boosting = null;
            String type_in_file = getBoostingTypeFromModelFile(filename);
            if (type_in_file.equals("tree")) {
                if (type.equals("gbdt")) {
                    boosting = new GBDT();
                } else if (type.equals("dart")) {
                    boosting = new DART();
                } else if (type.equals("goss")) {
                    boosting = new GOSS();
                } else {
                    logger.error("unknown boosting type " + type);
                }
                loadFileToBoosting(boosting, filename);
            } else {
                logger.error("unknown submodel type in model file " + filename);
            }
            return boosting;
        }
    }

    static boolean loadFileToBoosting(Boosting boosting, String filename) throws FileNotFoundException, IOException {
        if (boosting != null) {
            StringBuilder sb = new StringBuilder();
            List<String> lines = IOUtils.readLines(new FileInputStream(filename));
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            if (!boosting.loadModelFromString(sb.toString()))
                return false;
        }

        return true;
    }

    static String getBoostingTypeFromModelFile(
        String filename) throws FileNotFoundException, IOException {
        List<String> lines = IOUtils.readLines(new FileInputStream(filename));
        return lines.get(0);
    }

    abstract boolean loadModelFromString(String modelStr);

    public abstract boolean needAccuratePrediction();

    public abstract int numberOfClasses();

    public abstract void initPredict(int num_iteration);

    public abstract int numPredictOneRow(int num_iteration, boolean is_pred_leaf);

    public abstract int getCurrentIteration();

    public abstract int maxFeatureIdx();

    public abstract List<Double> predictLeafIndex(SparseVector vector);

    public abstract List<Double> predictRaw(SparseVector vector, PredictionEarlyStopInstance early_stop);

    public abstract List<Double> predict(SparseVector vector, PredictionEarlyStopInstance early_stop);
}
