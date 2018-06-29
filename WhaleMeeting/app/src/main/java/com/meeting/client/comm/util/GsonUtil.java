package com.meeting.client.comm.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.meeting.client.domain.base.BaseHR;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh 2014年12月22日
 * @Desc:
 */
public class GsonUtil {


    private static Gson gson;

    /**
     * @param json
     * @param clz
     * @return T
     * @author lxh
     * @Desc: TODO
     */
    public static <T> T toDomain(String json, Class<T> clz) {

        if (gson == null)
            gson = new Gson();

        T t = null;
        try {
            t = gson.fromJson(json, clz);
        } catch (Exception e) {
            return null;
        }

        return t;
    }

    public static BaseHR toNewDomain(String json, Type clz) {

        if (gson == null)
            gson = new Gson();

        ParameterizedType type = type(BaseHR.class, new Type[]{clz});

        return (BaseHR) gson.fromJson(json, type);
    }

    /**
     * @param t
     * @return String
     * @author lxh
     * @Desc: TODO
     */
    public static <T> String toJson(T t) {
        if (gson == null)
            gson = new Gson();


        String json = gson.toJson(t);
        return json;
    }


    public static <T> ArrayList<T> toList(String json, Class<T> cls) {
        try {
            ArrayList<T> mList = new ArrayList<T>();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                mList.add(gson.fromJson(elem, cls));
            }
            return mList;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> Map<String, T> toMap(String json, Class<T> clz) {
        if (gson == null)
            gson = new Gson();

        Map<String, T> map = gson.fromJson(json,
                new TypeToken<Map<String, T>>() {
                }.getType());

        return map;
    }

    public static String toJson(HashMap<String, String> hashMap) {
        if (gson == null)
            gson = new Gson();

        String jsonStr = gson.toJson(hashMap);

        return jsonStr;
    }




    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
