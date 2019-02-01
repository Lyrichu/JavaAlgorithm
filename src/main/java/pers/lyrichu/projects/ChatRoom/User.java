package pers.lyrichu.projects.ChatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
reference:https://www.jb51.net/article/144272.htm
 */
public class User {
    private String name;
    private long id;
    private long roomId;
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    /*
     * @param name: user name
     * @param id: user id
     * @param socket:user connectted socket
     * @throws IOException
     */
    public User(String name,long id,final Socket socket) throws IOException {
        this.name = name;
        this.id = id;
        this.socket = socket;
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.pw = new PrintWriter(socket.getOutputStream());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void setPw(PrintWriter pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "#User"+id+"#"+name+"[#Room"+roomId+"#]<socket:"+socket+">";
    }
}
