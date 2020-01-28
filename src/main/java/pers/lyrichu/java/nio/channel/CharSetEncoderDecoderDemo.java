package pers.lyrichu.java.nio.channel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 编码与解码
 */
public class CharSetEncoderDecoderDemo {

  public static void main(String[] args) {
    showAvailableCharSets();
    encoderDecoderDemo();
  }

  /**
   * 显示所有可以获取的字符集
   */
  private static void showAvailableCharSets() {
    Charset.availableCharsets().entrySet()
        .forEach(v -> System.out.println(v.getKey() + ":" + v.getValue()));
  }

  private static void encoderDecoderDemo() {
    Charset gbk = Charset.forName("GBK");
    CharsetEncoder gbkEncoder = gbk.newEncoder();
    CharsetDecoder gbkDecoder = gbk.newDecoder();
    // CharBuffer
    CharBuffer cb1 = CharBuffer.allocate(1024);
    cb1.put("我爱你中国!");
    // encode
    cb1.flip();
    try {
      ByteBuffer bb1 = gbkEncoder.encode(cb1);
      for (int i = 0;i < bb1.limit();i++) {
        System.out.println(bb1.get());
      }
      bb1.flip();
      // decode
      CharBuffer cb2 = gbkDecoder.decode(bb1);
      System.out.println(cb2.toString());
      // 如果尝试使用 utf-8 去解码 gbk 编码，则输出会乱码
      Charset utf8 = Charset.forName("UTF-8");
      bb1.flip();
      CharBuffer cb3 = utf8.decode(bb1);
      System.out.println(cb3.toString());
    } catch (CharacterCodingException e) {
      e.printStackTrace();
    }
  }
}
