//package pers.lyrichu.projects.ChatRoom;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.SocketException;
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ServerThread extends Thread {
//    private User user;
//    private ArrayList<User> userList; // 用户列表
//    private RoomList roomList;
//    private long roomId;
//    private PrintWriter pw;
//
//    public ServerThread(User user,ArrayList<User> userList,RoomList roomList) {
//        this.user = user;
//        this.userList = userList;
//        this.roomList = roomList;
//        this.pw = null;
//        this.roomId = -1;
//    }
//
//    /*
//     * 线程运行部分,持续读取用户socket发送来的数据,并解析
//     */
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                String msg = user.getBr().readLine();
//                // 打印用户发送的数据
//                System.out.println(msg);
//                // 解析
//                parseMsg(msg);
//            }
//        } catch (SocketException se) {
//            // 处理用户断开的异常
//            System.out.println("User " + user.getName() + " logout.");
//        } catch (Exception e) {
//            // 捕获其他异常
//            e.printStackTrace();
//        } finally {
//            try {
//                /*
//                 * 用户断开或者退出,需要将用户移除
//                 * 并关闭socket 连接
//                 */
//                remove(user);
//                user.getBr().close();
//                user.getSocket().close();
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            }
//        }
//    }
//
//    /*
//     * 用正则表达式匹配数据的格式
//     * 根据不同的类型指令,调用不同的方法处理
//     * @param msg
//     */
//    private void parseMsg(String msg) {
//        String code = null;
//        String message = null;
//        if (msg.length() > 0) {
//            // 匹配指令类型部分字符串
//            Pattern pattern = Pattern.compile("<code>(.*)</code>");
//            Matcher matcher = pattern.matcher(msg);
//            if (matcher.find()) {
//                code = matcher.group(1);
//            }
//            // 匹配消息部分的字符串
//            pattern = Pattern.compile("<msg>(.*)</msg>");
//            matcher = pattern.matcher(msg);
//            if (matcher.find()) {
//                message = matcher.group(1);
//            }
//
//            switch (code) {
//                case "join":
//                    // add to the room
//                    // code = 1,直接显示在textArea中
//                    // code = 11,在list中加入
//                    // code = 21,把当前房间的所有用户返回给client
//                    if (roomId == -1) {
//                        roomId = Long.parseLong(message);
//                        roomList.join(user,roomId);
//                        sendRoomMsgExceptSelf(buildCodeWithMsg(
//                                "<name>" + user.getName() + "</name>" + "<id>" + user.getId() + "</id>",11
//                        ));
//                        // 这个消息需要加入房间里已有用户的列表
//                        returnMsg(buildCodeWithMsg(
//                                "你加入了房间:" + roomList.getRoom(roomId).getName(),1
//                        ));
//                        returnMsg(buildCodeWithMsg(getMembersInRoom(),21));
//                    } else {
//                      // 退出房间
//                      roomList.escape(user,roomId);
//                      sendRoomMsg
//                    }
//
//            }
//        }
//    }
//
//    /*
//     * 获得该用户房间中的所有用户列表,并构成一定格式的消息返回
//     */
//    private String getMembersInRoom() {
//      // 先从room 列表获得该用户的room
//      Room room = roomList.getRoom(roomId);
//      StringBuffer buffer = new StringBuffer();
//      if (room != null) {
//        // 获得房间中所有用户的列表,然后构造一定的格式发送出去
//        ArrayList<User>
//      }
//    }
//
//}
