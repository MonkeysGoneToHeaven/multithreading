package ru.test.multithread.sudoku;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sudoku {

    public static final int GRID_HEIGHT = 9, GRID_WIDTH = 9;

    public byte[][] grid;
    public byte nextX, nextY;
    private SyncStack taskStack;
    private Validator v;

    public Sudoku(byte[][] grid, byte nextX, byte nextY, SyncStack taskStack) {
        this.grid = grid;
        this.nextX = nextX;
        this.nextY = nextY;
        this.taskStack = taskStack;
        this.v = new Validator(this);
    }

    public Sudoku cloneAndMod(byte nextX, byte nextY) {
        byte grid2[][] = new byte[grid.length][grid[0].length];
        for (int i = 0; i < grid2.length; i++)
            System.arraycopy(grid[i], 0, grid2[i], 0, grid[i].length);
        return new Sudoku(grid2, nextX, nextY, taskStack);
    }


    //return true if it return a solved sudoku
    public void solve() {

        if (nextX >= GRID_WIDTH) {
            nextY++;
            nextX = 0;
        }

        //test if end of sudoku
        if (nextY >= GRID_HEIGHT) {
            taskStack.solutions.add(this);
            return;
        }


        //test if on already  full case
        if (!v.isCaseEmpty(nextX, nextY)) {
            nextX = (byte) (nextX + 1);
            taskStack.push(this);
            return;
        }

        //try all the numbers
        for (byte i = 1; i <= 9; i++) {
            if (v.isValidInput(i, nextX, nextY)) {
                Sudoku s = cloneAndMod((byte) (nextX + 1), nextY);
                s.grid[nextX][nextY] = i;
                taskStack.push(s);
            }
        }
    }

    private byte mod9(byte v) {
        return (byte) (v % 9);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("???????????????????\n");

        for (int x = 0; x < grid.length; x++) {
            sb.append("? ");
            for (int y = 0; y < grid[x].length; y++) {
                sb.append(grid[x][y]);
                if (y == 2 || y == 5)
                    sb.append(" ? ");
            }
            sb.append(" ?\n");

            if (x == 2 || x == 5)
                sb.append("???????????????????\n");
        }

        sb.append("???????????????????");
        return sb.toString();
    }
}
