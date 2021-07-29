package ru.test.multithread.sudoku;

public class Validator {
    private Sudoku s;

    public Validator(Sudoku s) {
        this.s = s;
    }

    public boolean isValidInput(byte val, byte x, byte y) {
        if (!isCaseEmpty(x, y))
            return false;
        if (!isValidVal(val))
            return false;
        if (!isValidAddVertLine(val, x))
            return false;
        if (!isValidAddHorLine(val, y))
            return false;
        if (!isValidAddSquare(val, x, y))
            return false;
        return true;
    }

    public boolean isCaseEmpty(int x, int y) {
        return s.grid[x][y] == 0;
    }

    private boolean isValidVal(byte val) {
        return val > 0 && val < 10;
    }


    private boolean isValidAddVertLine(byte val, byte x) {
        for (byte i = 0; i < Sudoku.GRID_HEIGHT; i++)
            if (s.grid[x][i] == val)
                return false;
        return true;
    }

    private boolean isValidAddHorLine(byte val, byte y) {
        for (byte i = 0; i < Sudoku.GRID_WIDTH; i++)
            if (s.grid[i][y] == val)
                return false;
        return true;
    }

    private boolean isValidAddSquare(byte val, byte x, byte y) {
        //included borns
        byte xMin = (byte) (x - x % 3);
        byte yMin = (byte) (y - y % 3);
        for (byte i = xMin; i < xMin + 3; i++)
            for (byte j = yMin; j < yMin + 3; j++) {
                if (s.grid[i][j] == val)
                    return false;
            }
        return true;
    }

}
