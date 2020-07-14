package pers.lyrichu.ML.NLP.lightgbm.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.ML.NLP.lightgbm.Common;

/**
 * @author lwj
 */
public class MulticlassOVA extends ObjectiveFunction {

    private static final long serialVersionUID = 3602643708521256870L;
    private static final Logger logger = LoggerFactory.getLogger(MulticlassOVA.class);
    int numClass;

    MulticlassOVA(String[] strs) {
        numClass = -1;
        for (String str : strs) {
            String[] tokens = str.split(":");
            if (tokens.length == 2) {
                if (tokens[0].equals("num_class")) {
                    numClass = Integer.parseInt(tokens[1]);
                }
            }
        }
        if (numClass < 0) {
            logger.error("Objective should contains num_class field");
        }
    }

    public void convertOutput(double[] input, double[] output) {
        Common.softmax(input, output, numClass);
    }
}
