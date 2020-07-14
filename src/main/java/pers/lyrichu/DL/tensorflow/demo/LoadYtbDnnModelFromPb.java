package pers.lyrichu.DL.tensorflow.demo;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;

/**
 * 测试，加载 youtube dnn embedding model
 */
public class LoadYtbDnnModelFromPb {

  private InputStream pbModelInputStream =
      LoadModelFromPb.class.getClassLoader().getResourceAsStream("tf_model/ytb_epoch0.pb");
  private final int MAX_WINDOW_SIZE = 1000;
  private final int EMBEDDING_SIZE = 256;

  public static void main(String[] args) throws Exception {
    LoadYtbDnnModelFromPb model = new LoadYtbDnnModelFromPb();
    model.testLoadPb();
  }

  private void testLoadPb() throws Exception {
    int[][] x_batch = new int[1][MAX_WINDOW_SIZE];
    for (int i = 0;i < MAX_WINDOW_SIZE;i++) {
      x_batch[0][i] = 0;
    }
    x_batch[0][0] = 2;
    x_batch[0][1] = 5;
    x_batch[0][2] = 15;
    x_batch[0][3] = 1206;

    float[][][] emb_mask = new float[1][MAX_WINDOW_SIZE][1];
    for (int i = 0;i < 4;i++) {
      emb_mask[0][i][0] = 1.0f;
    }

    float[][] word_num = new float[1][1];
    word_num[0][0] = 4;

    float[][] pred = new float[1][EMBEDDING_SIZE];

    try (Graph graph = new Graph()) {
      //导入图
      byte[] graphBytes = IOUtils.toByteArray(pbModelInputStream);
      graph.importGraphDef(graphBytes);

      //根据图建立Session
      try(Session session = new Session(graph)){
        //相当于TensorFlow Python中的sess.run(z,feed_dict = {'x': 10.0})
        Runner runner = session.runner()
            .feed("x_batch", Tensor.create(x_batch))
            .feed("emb_mask",Tensor.create(emb_mask))
            .feed("word_num",Tensor.create(word_num))
            .fetch("pred");
        Tensor tensor;
        for (int i = 0;i < 20;i++) {
          long startTime = System.currentTimeMillis();
          tensor = runner.run().get(0);
          tensor.copyTo(pred);
          System.out.printf("%d:predict cost:%d ms.\n",i,System.currentTimeMillis() - startTime);
        }
        for (int i = 0;i < EMBEDDING_SIZE;i++) {
          System.out.print(pred[0][i] + ",");
        }
        System.out.println();
      }
    }
  }
}
