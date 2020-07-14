package pers.lyrichu.DL.tensorflow.demo;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

/**
 * 测试，从一个 tf python api 生成的 pb model 中 进行加载 pb model,输入、输出测试
 * reference:https://cloud.tencent.com/developer/article/1143250
 */
public class LoadModelFromPb {

  private String pbModelPath =
      LoadModelFromPb.class.getClassLoader().getResource("tf_model/test_model.pb").getPath();

  public static void main(String[] args) throws Exception {
    LoadModelFromPb model = new LoadModelFromPb();
    model.testLoadPb();
  }

  private void testLoadPb() throws Exception {
    try (Graph graph = new Graph()) {
      //导入图
      byte[] graphBytes = IOUtils.toByteArray(new
          FileInputStream(pbModelPath));
      graph.importGraphDef(graphBytes);

      //根据图建立Session
      try(Session session = new Session(graph)){
        //相当于TensorFlow Python中的sess.run(z,feed_dict = {'x': 10.0})
        float z = session.runner()
            .feed("x", Tensor.create(10.0f))
            .fetch("z").run().get(0).floatValue();
        System.out.println("z = " + z);
      }
    }
  }
}
