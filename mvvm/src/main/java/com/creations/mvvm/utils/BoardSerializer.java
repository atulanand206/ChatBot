package com.creations.mvvm.utils;

import com.creations.mvvm.models.blocks.Board;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BoardSerializer implements JsonSerializer<Board>, JsonDeserializer<Board> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String BOARD = "board";
    @Override
    public Board deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        int i=0;
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int id = jsonObject.get(ID).getAsInt();
        String name = jsonObject.get(NAME).getAsString();
        String board = jsonObject.get(BOARD).getAsString();
        Board board1 = BoardUtils.board(board.replace("\"","").split(","));
        board1.setId(id);
        board1.setName(name);
        board1.setBoard(board);
        return board1;
    }

    @Override
    public JsonElement serialize(Board jsonElement, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(ID, jsonElement.getId());
        jsonObject.addProperty(NAME, jsonElement.getName());
        jsonObject.addProperty(BOARD, jsonElement.getBoard().replace("\"", "\\\""));
        return jsonObject;
    }
}
