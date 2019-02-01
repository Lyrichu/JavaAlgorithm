package pers.lyrichu.projects.ChatRoom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class RoomList {
    private HashMap<Long,Room> map;
    private long unusedRoomId;
    public static long MAX_ROOMS = 9999; // 最大房间个数
    private int totalRooms;

    /*
     * 未使用的roomId从1开始算起,房间总数初始化为0
     */
    public RoomList() {
        map = new HashMap<>();
        unusedRoomId = 1;
        totalRooms = 0;
    }

    /*
     * 创建一个新的房间,使用未使用的房间号进行创建,如果没有可以使用的就创建失败
     * @param name:房间的名字
     * @return 创建的房间的id
     */
    public long createRoom(String name) {
        if (totalRooms < MAX_ROOMS) {
            if (name.length() == 0) {
                name = String.valueOf(unusedRoomId);
            }
            Room room = new Room(name,unusedRoomId);
            map.put(unusedRoomId,room);
            totalRooms++;
            return unusedRoomId++;
        } else {
            return -1;
        }
    }

    /*
     * 用户加入一个房间
     * @param user
     * @param roomId
     */
    public boolean join(User user,long roomId) {
        if (map.containsKey(roomId)) {
            map.get(roomId).addUser(user);
            return true;
        } else {
            return false;
        }
    }

    /*
     * 用户退出他的房间
     */
    public int escape(User user,long roomId) {
        if (map.containsKey(roomId)) {
            int number = map.get(roomId).delUser(user);
            // 如果房间人数为0,则直接删除该房间
            if (number == 0) {
                map.remove(roomId);
                totalRooms--;
                return 0;
            }
            return 1;
        } else {
            return -1;
        }
    }

    /*
     * 列出所有房间的列表,返回一个二维数组 arr,arr[i][0]存放房间的id
     * arr[i][1]存放房间的name
     */

    public String[][] listRooms() {
        String[][] arr = new String[totalRooms][2];
        int i = 0;
        // 把map转化为set,并且使用迭代器进行遍历
        Set<Map.Entry<Long,Room>> set = map.entrySet();
        Iterator<Map.Entry<Long,Room>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long,Room> entry = iterator.next();
            long key = entry.getKey();
            Room value = entry.getValue();
            arr[i][0] = String.valueOf(key);
            arr[i][1] = value.getName();
            i++;
        }
        return arr;
    }

    public Room getRoom(long roomId) {
        if (map.containsKey(roomId)) {
            return map.get(roomId);
        }
        return null;
    }


}
