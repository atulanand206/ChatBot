package com.creations.mvvm.utils;

import com.creations.mvvm.models.blocks.Add;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;
import com.example.application.utils.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.application.utils.RecyclerUtils.LayoutType.LINEAR_HORIZONTAL;
import static com.example.application.utils.RecyclerUtils.LayoutType.LOOP_HORIZONTAL;

public class BoardUtils {

//    public static char randomChar() {
//        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSSTUVVWXXYZabcdefghijklmnopqrstuvwxyz";
//        final int N = alphabet.length();
//        Random r = new Random();
//        return alphabet.charAt(r.nextInt(N));
//    }

//    public static Cell randomCell() {
//        return new Cell(randomChar());
//    }

//    public static Row randomRow(final int size) {
//        List<Cell> cells = new ArrayList<>();
//        for (int i=0;i<size;i++)
//            cells.add(randomCell());
//        return new Row(cells, LOOP_HORIZONTAL);
//    }

//    public static Board randomBoard(final int size) {
//        List<Row> cells = new ArrayList<>();
//        for (int i=0;i<size;i++)
//            cells.add(randomRow(size));
//        return new Board(cells);
//    }

    public static Board testBoard() {
//        return testBoard(new char[][] {
//                {'1', '2', 'e', 'j', 'e', 'j', '5', '4', 'f', 'a', 'b', 'q', 'n'},
//                {'f', 'f', 'q', 'u', 's', 'a', 'w', 'h', '5', '4', 'f', 'y'},
//                {'a', 'n', 'r', 'g', 't', 'y', 'e', 'j', 'u', 'w', 'n', 'r', 'w'},
//                {'f', 'a', 'a', 'n', 'e', 'j', 'e', 'j', 'u', 'u', 'w', 'm'},
//                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
//                {'f', 'a', 'a', 'n', 'e', 'j', 'u', 'e', 'j', 'e', 'j', 'u', 'w', 'm'},
//                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
//                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
//                {'1', '2', '5', '4', 'f', 'a', 'b', '5', '4', 'f', 'q', 'n'},
//                {'1', '2', 'e', 'j', '5', '4', 'f', 'a', 'b', 'e', 'j', 'e', 'j', 'q', 'n'},
//                {'f', 'f', '5', '4', 'f', 'q', 'u', 's', 'a', 'w', 'h', 'y'},
//                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
//                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
//                {'f', 'a', 'a', 'n', 'e', 'j', 'u', 'e', 'j', 'e', 'j', 'u', 'w', 'm'},
//                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
////                {'1', '2', '5', '4', 'f', 'a', 'b', '5', '4', 'f', 'q', 'n'},
////                {'f', 'f', 'q', 'u', '5', '4', 'f', 's', 'a', 'w', 'h', 'y'},
////                {'a', 'n', 'r', 'g', 't', 'y', 'e', 'j', 'u', 'n', 'r', 'w'},
//                {'f', 'a', 'e', 'j', 'u', 'a', 'n', 'e', 'j', 'u', 'w', 'm'}
//        });
//        return testBoard(new char[][] {
//                {'1', '2', 'e', 'j', 'e', 'j', '5', '4', 'f', 'a', 'b', 'q', 'n'},
//                {'f', 'f', 'q', 'u', 's', 'a', 'w', 'h', '5', '4', 'f', 'y'},
//                {'a', 'n', 'r', 'g', 't', 'y', 'e', 'j', 'u', 'w', 'n', 'r', 'w'},
//                {'f', 'a', 'a', 'n', 'e', 'j', 'e', 'j', 'u', 'u', 'w', 'm'},
//                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
//                {'f', 'a', 'a', 'n', 'e', 'j', 'u', 'e', 'j', 'e', 'j', 'u', 'w', 'm'},
//                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
//        });
        return board(new String[] {
                "a woman who was formerly a particular man's wife",
                "claim as due or just",
                "of ideas images representations",
                "conformity to fact or truth",
                "take as an undesirable consequence",
                "some event or state of affair",
                "and particular and complete accordance",
                "the exact center of the"
        });
    }

    public static Board testBoard(@NonNull final char[][] blocks) {
        List<Row> rows = new ArrayList<>();
        for (char[] block : blocks){
            List<Cell> row = new ArrayList<>();
            for (char cell : block) {
                row.add(new Cell(cell));
            }
            rows.add(new Row(row, LOOP_HORIZONTAL));
        }
        return new Board(rows);
    }

    public static Cell addCell() {
        return new Cell(true);
    }

//    public static Cell minusCell() {
//        return new Cell('-', false);
//    }

    public static Row row(@Nullable String word, RecyclerUtils.LayoutType layoutType) {
        List<Cell> cells = new ArrayList<>();
        if (word == null)
            return new Row(cells, layoutType);
        for (int i=0;i<word.length();i++)
            cells.add(new Cell(word.charAt(i)));
        return new Row(cells, layoutType);
    }

    public static Board board(List<String> words) {
        List<Row> rows = new ArrayList<>();
        for (String word : words)
            rows.add(row(word, LINEAR_HORIZONTAL));
        return new Board(rows);
    }

    public static Board board(String[] words) {
        List<Row> rows = new ArrayList<>();
        for (String word : words)
            rows.add(row(word, LOOP_HORIZONTAL));
        return new Board(rows);
    }

    public static Add newRow() {
        return new Add(new Row());
    }

}
