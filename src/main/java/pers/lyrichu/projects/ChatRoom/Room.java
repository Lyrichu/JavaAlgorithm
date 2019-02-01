package pers.lyrichu.projects.ChatRoom;

import java.util.ArrayList;

public class Room {
    private String name;
    private long roomId;
    private ArrayList<User> list;
    private int totalUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    /*
     * 向房间中添加一个用户
     * @param user
     */
    public void addUser(User user) {
        if (!list.contains(user)) {
            list.add(user);
            totalUsers++;
        } else {
            System.out.println("User is already exist in Room<" + name + ">:" + user);
        }
    }

    /*
     *从房间中删除一个用户
     * @param user
     * @return left users num
     */
    public int delUser(User user) {
        if (list.contains(user)) {
            list.remove(user);
            return --totalUsers;
        } else {
            System.out.println("User is not in Room<" + name + ">:" + user);
            return totalUsers;
        }
    }

    /*
     * 获得当前用户列表
     */
    public ArrayList<User> getList() {
        return list;
    }

    /*
     * 获得当前房间用户昵称的列表
     */
    public String[] getUserNames() {
        String[] names = new String[list.size()];
        int i = 0;
        for (User user:list) {
            names[i++] = user.getName();
        }
        return names;
    }

    public Room(String name,long roomId) {
        this.name = name;
        this.roomId = roomId;
        this.totalUsers = 0;
        list = new ArrayList<>();
    }

}
