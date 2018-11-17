package com.creations.chatbot.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonConvertor {
    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
        return getGsonObject().fromJson(jsonString, classOfT);
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT, String namingPolicy) {
        Gson gson = getGsonObject();
        return gson.fromJson(jsonString, classOfT);
    }

    public static <T> T fromJson(String jsonString, Type type) {
        Gson gson = getGsonObject();
        return gson.fromJson(jsonString, type);
    }

    public static <T> String toJson(T obj) {
        return getGsonObject().toJson(obj, new TypeToken<T>() {
        }.getType());
    }

    /**
     * @param reader
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(Reader reader, Class<T> classOfT) {
        return getGsonObject().fromJson(reader, classOfT);
    }


    public static <T> List<T> fromJson(List<String> jsonList, Class<T> classOfT) {
        Gson gson = getGsonObject();
        List<T> resultList = new ArrayList<T>();
        for (String jsonString : jsonList) {
            resultList.add(gson.fromJson(jsonString, classOfT));
        }
        return resultList;
    }

    public static <T> String toJson(List<T> obj) {
        return getGsonObject().toJson(obj, new TypeToken<List<T>>() {
        }.getType());
    }


    public static <T> T fromJsonElement(JsonElement jsonElement, Class<T> classOfT) {
        return getGsonObject().fromJson(jsonElement, classOfT);
    }

    public static <T> JsonElement toJsonElement(T object) {
        return getGsonObject().toJsonTree(object, new TypeToken<T>() {
        }.getType());
    }

    public static <T> List<T> fromJsonArray(JsonArray jsonArray, Class<T> classOfT) {
        Gson gson = getGsonObject();
        List<T> resultList = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            resultList.add(gson.fromJson(jsonElement, classOfT));
        }
        return resultList;
    }

    private static Gson getGsonObject() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
