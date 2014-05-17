package com.company;

public class Main {
    private static int N = 10;

    public static void main(String[] args) {
        Board.setSizeLimit(N);
        Board board = new Board();
        board.solve(0);
        board.printSolutions();
        //dafaq
    }
}
