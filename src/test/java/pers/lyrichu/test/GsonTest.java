package pers.lyrichu.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GsonTest {

    private static final Gson gson = new Gson();
    private static final JsonParser parser = new JsonParser();

    @Test
    public void test1() {
        String s1 = "{'a':1,'s':['c','m']}";
        JsonObject json1 = gson.fromJson(s1,JsonObject.class);
        System.out.println(json1);

        JsonArray arr1 = json1.getAsJsonArray("s");
        // get list
        List<String> lists1 = getListValue(json1,"s");
        System.out.println(String.join(",",lists1));
    }

    public static <T> List<T> getListValue(JsonObject jsonObject, String property) {
        JsonArray arr = jsonObject.getAsJsonArray(property);
        if (arr != null) {
            return gson.fromJson(arr,List.class);
        } else {
            return new ArrayList<T>();
        }
    }
}
