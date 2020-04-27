package com.creations.mvvm.utils;

import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;

public class BoardUtils {

    public static char randomChar() {
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSSTUVVWXXYZabcdefghijklmnopqrstuvwxyz";
        final int N = alphabet.length();
        Random r = new Random();
        return alphabet.charAt(r.nextInt(N));
    }

    public static Cell randomCell() {
        return new Cell(randomChar());
    }

    public static Row randomRow(final int size) {
        List<Cell> cells = new ArrayList<>();
        for (int i=0;i<size;i++)
            cells.add(randomCell());
        return new Row(cells);
    }

    public static Board randomBoard(final int size) {
        List<Row> cells = new ArrayList<>();
        for (int i=0;i<size;i++)
            cells.add(randomRow(size));
        return new Board(cells);
    }

    public static Board testBoard(final int size) {
        return testBoard(new char[][] {
                {'1', '2', 'e', 'j', 'e', 'j', '5', '4', 'f', 'a', 'b', 'q', 'n'},
                {'f', 'f', 'q', 'u', 's', 'a', 'w', 'h', '5', '4', 'f', 'y'},
                {'a', 'n', 'r', 'g', 't', 'y', 'e', 'j', 'u', 'w', 'n', 'r', 'w'},
                {'f', 'a', 'a', 'n', 'e', 'j', 'e', 'j', 'u', 'u', 'w', 'm'},
                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
                {'f', 'a', 'a', 'n', 'e', 'j', 'u', 'e', 'j', 'e', 'j', 'u', 'w', 'm'},
                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
                {'1', '2', '5', '4', 'f', 'a', 'b', '5', '4', 'f', 'q', 'n'},
                {'1', '2', 'e', 'j', '5', '4', 'f', 'a', 'b', 'e', 'j', 'e', 'j', 'q', 'n'},
                {'f', 'f', '5', '4', 'f', 'q', 'u', 's', 'a', 'w', 'h', 'y'},
                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
                {'a', 'n', 'r', 'g', 't', 'e', 'j', 'u', 'w', 'y', 'n', 'r', 'w'},
                {'f', 'a', 'a', 'n', 'e', 'j', 'u', 'e', 'j', 'e', 'j', 'u', 'w', 'm'},
                {'g', 'e', 'j', 'u', 'w', 'q', 'h', 'w', 'n', 'w', 'n', 'w', 't'},
//                {'1', '2', '5', '4', 'f', 'a', 'b', '5', '4', 'f', 'q', 'n'},
//                {'f', 'f', 'q', 'u', '5', '4', 'f', 's', 'a', 'w', 'h', 'y'},
//                {'a', 'n', 'r', 'g', 't', 'y', 'e', 'j', 'u', 'n', 'r', 'w'},
                {'f', 'a', 'e', 'j', 'u', 'a', 'n', 'e', 'j', 'u', 'w', 'm'}
        });
    }

    public static Board testBoard(@NonNull final char[][] blocks) {
        List<Row> rows = new ArrayList<>();
        for (char[] block : blocks){
            List<Cell> row = new ArrayList<>();
            for (char cell : block) {
                row.add(new Cell(cell));
            }
            rows.add(new Row(row));
        }
        return new Board(rows);
    }
}