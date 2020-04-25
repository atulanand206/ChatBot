package com.creations.tools.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class JsonConvertor {
    
    private final Gson mGson;

    public JsonConvertor(@NonNull final Gson gson) {
        this.mGson = gson;
    }

    public <T> T fromJson(String jsonString, Class<T> classOfT) {
        return mGson.fromJson(jsonString, classOfT);
    }

    public <T> T fromJson(String jsonString, Class<T> classOfT, String namingPolicy) {
        return mGson.fromJson(jsonString, classOfT);
    }

    public <T> T fromJson(String jsonString, Type type) {
        return mGson.fromJson(jsonString, type);
    }

    public <T> String toJson(T obj) {
        return mGson.toJson(obj, new TypeToken<T>() {
        }.getType());
    }

    /**
     * @param reader
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T fromJson(Reader reader, Class<T> classOfT) {
        return mGson.fromJson(reader, classOfT);
    }


    public <T> List<T> fromJson(List<String> jsonList, Class<T> classOfT) {
        List<T> resultList = new ArrayList<T>();
        for (String jsonString : jsonList) {
            resultList.add(mGson.fromJson(jsonString, classOfT));
        }
        return resultList;
    }

    public <T> String toJson(List<T> obj) {
        return mGson.toJson(obj, new TypeToken<List<T>>() {
        }.getType());
    }


    public <T> T fromJsonElement(JsonElement jsonElement, Class<T> classOfT) {
        return mGson.fromJson(jsonElement, classOfT);
    }

    public <T> JsonElement toJsonElement(T object) {
        return mGson.toJsonTree(object, new TypeToken<T>() {
        }.getType());
    }

    public <T> List<T> fromJsonArray(JsonArray jsonArray, Class<T> classOfT) {
        List<T> resultList = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            resultList.add(mGson.fromJson(jsonElement, classOfT));
        }
        return resultList;
    }

}
