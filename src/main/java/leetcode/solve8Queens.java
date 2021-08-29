package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 8皇后
 *
 * Author: jasonz
 * date: 2021-07-15 下午11:38
 */
public class solve8Queens {


    public static void main(String[] args) {
        System.out.println(solve8Queens(4));
    }

    static List<List<String>> result = new ArrayList<>();
    public static List<List<String>> solve8Queens(int n) {

        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';

            }

        }

        backtrace(0, board, n);
        return result;

    }

    private static void backtrace(int row, char[][] board, int n) {
        if (row == n) {
            List<String> snapshot = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                snapshot.add(new String(board[i]));


            }
            result.add(snapshot);
            return;

        }

        for (int col = 0; col < n; col++) {
            if (isOk(board, n, row, col)) {
                board[row][col] = 'Q';
                backtrace(row+1, board, n);
                board[row][col] = '.';

            }

        }

    }

    private static boolean isOk(char[][] board, int n, int row, int col) {
        //同一列
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q'){
                return false;

            }

        }

        //左斜上方
        int i = row - 1;
        int j = col - 1;

        while (i >= 0 && j >= 0) {
            if (board[i][j] == 'Q') {
                return false;

            }
            i--;
            j--;

        }

        //右斜上方
        i = row - 1;
        j = col + 1;

        while (i >= 0 && j < n) {
            if (board[i][j] == 'Q') {
                return false;

            }
            i--;
            j++;

        }

        return true;
    }
}
