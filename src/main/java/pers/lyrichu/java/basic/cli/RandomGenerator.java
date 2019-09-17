package pers.lyrichu.java.basic.cli;

import org.apache.commons.cli.*;
import java.security.SecureRandom;

public class RandomGenerator {
    public static void main(String[] args) {
        // 创建选项集
        Options options = new Options();
        // 添加选项 -l --level
        options.addOption(Option.builder("l") // 选项名称
                .longOpt("level") // 长选项名
                .hasArg() // 需要参数
                .argName("level") // 参数显示名称
                // 选项的描述、帮助信息
                .desc("randomization level (range: 1,2,3; default: 2)" +
                        "\nlevel=1 => digit [0-9]" +
                        "\nlevel=2 => alpha [a-zA-Z]" +
                        "\nlevel=3 => alnum [0-9a-zA-Z]")
                .build());
        // 添加选项 -n --length
        options.addOption(Option.builder("n") // 选项名称
                .longOpt("length") // 长选项名
                .hasArg() // 需要参数
                .argName("length") // 参数显示名称
                // 选项的描述、帮助信息
                .desc("random string length (range: >=0; default: 20)")
                .build());
        // 添加选项 -h --help
        options.addOption(Option.builder("h") // 选项名称
                .longOpt("help") // 长选项名
                // 选项的描述、帮助信息
                .desc("show this help message and exit program")
                .build());

        // 解析器
        CommandLineParser parser = new DefaultParser();
        // 格式器
        HelpFormatter formatter = new HelpFormatter();
        // 解析结果
        CommandLine result = null;

        try {
            // 尝试解析命令行参数
            result = parser.parse(options, args);
        } catch (ParseException e) {
            // 打印解析异常
            System.err.println(e.getMessage());
            // 打印帮助信息
            formatter.printHelp("RandomGenerator", options, true);
            // 退出程序，退出码为 1
            System.exit(1);
        }

        // 如果存在 -h --help 参数
        if (result.hasOption("h")) {
            formatter.printHelp("RandomGenerator", options, true);
            System.exit(0);
        }

        int level = 2; // default level
        int length = 20; // default length

        // 如果存在 -l --level 参数
        if (result.hasOption("l")) {
            try {
                level = Integer.parseInt(result.getOptionValue("l"));
            } catch (Exception e) {
                System.err.println(e.toString());
                formatter.printHelp("RandomGenerator", options, true);
                System.exit(1);
            }
        }
        // 如果存在 -n --length 参数
        if (result.hasOption("n")) {
            try {
                length = Integer.parseInt(result.getOptionValue("n"));
            } catch (Exception e) {
                System.err.println(e.toString());
                formatter.printHelp("RandomGenerator", options, true);
                System.exit(1);
            }
        }
        // 最后根据参数，执行 random() 方法
        System.out.println(random(level, length));
    }

    // 数字
    private static final String digit = "0123456789";
    // 字母
    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    // 字母 + 数字
    private static final String alnum = digit + alpha;
    // 随机数发生器
    private static final SecureRandom rnd = new SecureRandom();

    // 随机字符串生成器
    public static String random(int level, int length) {
        StringBuilder buf = new StringBuilder(length);
        if (level == 1)
            for (int i = 0; i < length; i++)
                buf.append(digit.charAt(rnd.nextInt(digit.length())));
        else if (level == 2)
            for (int i = 0; i < length; i++)
                buf.append(alpha.charAt(rnd.nextInt(alpha.length())));
        else if (level == 3)
            for (int i = 0; i < length; i++)
                buf.append(alnum.charAt(rnd.nextInt(alnum.length())));
        return buf.toString();
    }
}
