package pers.lyrichu.projects.ChatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerThread extends Thread {
    private User user;
    private ArrayList<User> userList; // 用户列表
    private RoomList roomList;
    private long roomId;
    private PrintWriter pw;

    public ServerThread(User user,ArrayList<User> userList,RoomList roomList) {
        this.user = user;
        this.userList = userList;
        this.roomList = roomList;
        this.pw = null;
        this.roomId = -1;
    }

    /*
     * 线程运行部分,持续读取用户socket发送来的数据,并解析
     */

    @Override
    public void run() {
        try {
            while (true) {
                String msg = user.getBr().readLine();
                // 打印用户发送的数据
                System.out.println(msg);
                // 解析
                parseMsg(msg);
            }
        } catch (SocketException se) {
            // 处理用户断开的异常
            System.out.println("User " + user.getName() + " logout.");
        } catch (Exception e) {
            // 捕获其他异常
            e.printStackTrace();
        } finally {
            try {
                /*
                 * 用户断开或者退出,需要将用户移除
                 * 并关闭socket 连接
                 */
                remove(user);
                user.getBr().close();
                user.getSocket().close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /*
     * 用正则表达式匹配数据的格式
     * 根据不同的类型指令,调用不同的方法处理
     * @param msg
     */
    private void parseMsg(String msg) {
        String code = null;
        String message = null;
        if (msg.length() > 0) {
            // 匹配指令类型部分字符串
            Pattern pattern = Pattern.compile("<code>(.*)</code>");
            Matcher matcher = pattern.matcher(msg);
            if (matcher.find()) {
                code = matcher.group(1);
            }
            // 匹配消息部分的字符串
            pattern = Pattern.compile("<msg>(.*)</msg>");
            matcher = pattern.matcher(msg);
            if (matcher.find()) {
                message = matcher.group(1);
            }

            switch (code) {
                case "join":
                    // add to the room
                    // code = 1,直接显示在textArea中
                    // code = 11,在list中加入
                    // code = 21,把当前房间的所有用户返回给client

            }
        }
    }
}
