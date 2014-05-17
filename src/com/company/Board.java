package com.company;

import java.util.Arrays;

/**
 * Created by andero on 8.05.2014.
 */
public class Board {
    private static int getMaxCallCount = 0;
    private static int sizeLimit;
    private static Board[] solutions;
    private static int[] scores;
    private int size;
    private int[] fields;

    public Board newInstance() {
        Board newInst = new Board();
        newInst.fields = this.fields.clone();
        return newInst;
    }

    public Board() {
        size = 2;
        fields = new int[size];
        fields[0] = 0;
        fields[1] = 1;
    }

    public Board(int[] fields) {
        size = fields.length + 1;
        this.fields = new int[size];
        System.arraycopy(fields, 0, this.fields, 0, fields.length);
    }

    private int getCalculatedLimit() {
        return fields[size - 1] * 3 + 1;
    }

    public static void setSizeLimit(int sizeLimit) {
        Board.sizeLimit = sizeLimit;
        solutions = new Board[sizeLimit];
        scores = new int[sizeLimit];
    }

    public void solve(int limit) {
        if (0 == limit) {
            limit = getCalculatedLimit();
        }
        for (int i = fields[size - 2] + 1; i <= limit; i++) {
            fields[size - 1] = i;
            int max = getMax();
            if (max > scores[size - 2]) {
                scores[size - 2] = max;
                solutions[size - 2] = this.newInstance();
                System.out.println((size - 1) + "\t" + max + "\t" + Arrays.toString(Arrays.copyOfRange(fields, 1, fields.length)));
            }
            if (size < sizeLimit + 1) {
                Board board = new Board(fields);
                board.solve(max);
            }
        }
    }

    private int getMax() {
        // Kuna me teame N-1 data mappi, siis uue mapi tegemiseks tuleks n+1 puhul ainult viimane lõik üle käia???
        getMaxCallCount++;
        int limit = this.getCalculatedLimit();
        if (scores[size - 2] > limit) {
            return 0;
        }
        int max = 0;

        int[] previousResults = new int[limit + 1];
        for (int j = 0; j < size; j++) {
            previousResults[fields[j]] = 1;
        }
        for (int target = 1; target <= limit; target++) {
            boolean matchFound = false;
            for (int fieldCtr = 1; fieldCtr < size; fieldCtr++) {
//                System.out.println(size + "FC" + fieldCtr);
                int toFind = target - fields[fieldCtr];
                if (toFind == 0) {
                    matchFound = true;
                    break;
                }
                if (toFind < 0) {
                    break;
                }
                int dartCount = previousResults[toFind];
                if (dartCount > 0 && dartCount < 3) {
                    matchFound = true;
                    if (previousResults[target] == 0) {
                        previousResults[target] = dartCount + 1;
                    } else if (previousResults[target] > dartCount + 1) {
                        previousResults[target] = dartCount + 1;
                    }
                }
            }

            if (matchFound) {
                if (max < target) {
                    max = target;
                }
            } else {
                break;
            }
        }
//        System.out.println((size - 1) + "\t" + (max + 1) + "\t" + Arrays.toString(fields));
//        System.out.println(Arrays.toString(previousResults));
        return max + 1;
    }

    public void printSolutions() {
        for (int i = 0; i < sizeLimit; i++) {
            int[] res = solutions[i].getFields();
            System.out.println((i + 1) + "\t" + scores[i] + "\t" + Arrays.toString(Arrays.copyOfRange(res, 1, res.length)));
        }
        System.out.println(getMaxCallCount);
    }

    public int[] getFields() {
        return fields;
    }
}
