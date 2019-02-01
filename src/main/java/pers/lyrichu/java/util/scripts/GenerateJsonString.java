package pers.lyrichu.java.util.scripts;

import org.json.JSONObject;

public class GenerateJsonString {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",1);
        jsonObject.put("b",2);
        System.out.println("json str:"+jsonObject.toString());
    }
}
