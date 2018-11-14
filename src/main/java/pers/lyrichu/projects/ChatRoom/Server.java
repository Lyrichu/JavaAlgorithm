package pers.lyrichu.projects.ChatRoom;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// 服务端
public class Server {
    private ArrayList<User> allUsers;
    private RoomList rooms;
    private int port;
    private ServerSocket ss;
    private long unusedUserId;
    public final long MAX_USERS = 999999; // 最大用户数量

    /*
     * 通过端口号(port)来构造服务器端对象
     * 维护一个总的用户列表和房间列表
     * @param port
     * @throws Exception
     */
    public Server(int port) throws Exception {
        allUsers = new ArrayList<>();
        rooms = new RoomList();
        this.port = port;
        unusedUserId = 1;
        ss = new ServerSocket(port);
        System.out.println("Server is built!");
    }

    /*
     * 获得下一个可用的用户id
     */

    private long getNextUserId() {
        if (unusedUserId < MAX_USERS) {
            return unusedUserId++;
        }
        return -1;
    }

    /*
     * 开始监听,当接收到新的用户连接时,就创建一个新的用户,
     * 并添加到用户列表中,
     * 然后创建一个新的服务线程用于收发该用户的消息
     * @throws Exception
     */
    public void startListen() throws Exception {
        while (true) {
            Socket socket = ss.accept();
            long id = getNextUserId();
            if (id != -1) {
                User user = new User("User_"+id,id,socket);
                System.out.println(user.getName()+" is login...");
                allUsers.add(user);
                ServerThread serverThread = new ServerThread(user,allUsers,rooms);
                serverThread.start();
            } else {
                System.out.println("Server is full!");
                socket.close();
            }
        }
    }

    /*
     * 测试使用的main方法,设置监听端口为9999,并开始监听
     * @param args
     */

    public static void main(String[] args) {
        try {
            Server server = new Server(9999);
            server.startListen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
