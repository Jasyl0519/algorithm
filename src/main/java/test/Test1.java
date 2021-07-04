package test;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-05-05 下午8:35
 */
public class Test1 {

    public static void main(String[] nums) {
        //StringBuilder sb = new StringBuilder();
        //sb.substring();
        //
        //String s = "hello  world! ";
        //String[] ss = s.split(" ");
        //
        //for (String s1 : ss) {
        //
        //    System.out.println(s1.equals(""));
        //}
        
           //reverseWords("  hello world!  ");
        //isPalindrome("A man, a plan, a canal: Panama") ;


        //int a = 2147483641;
        //int b = 214748;
        //System.out.println('0' - 0);
        //System.out.println('9' - 0);
        //System.out.println('a' - 0);
        //System.out.println('A' - 0);
        //System.out.println('Z' - 0);
        //System.out.println('z' - 0);
        //
        //if (a > b && a - b > 1) {
        //    System.out.println(111);
        //}
        //
        //
        //
        //
        //char a1 = 'a';
        //String aa = String.valueOf(a1);
        //
        //String bb = "1";
        //String bb1 = "a";
        //System.out.println(a1 - '0');

        //String[] a = new String[]{"O X" , " XO","X O"} ;
        //tictactoe(a);


        //int[] arr = new int[]{2,3,1,1,4};
        //canJump(arr);
        //
        //Stack<Integer> stack = new Stack();
        //stack.isEmpty() ;
        //stack.pop()  ;
        //stack.push(1);
        //stack.peek();
        //
        //Queue<Integer> queue1 = new LinkedList<Integer>();
        //queue1.poll();
        //queue1.isEmpty();
        //queue1.size();
        //queue1.poll() ;
        //queue1.peek()
        //        ;
        //
        //String s = "123";
        //s.toCharArray();
        //Stack<Character> stac1k = new Stack();
        //
        //for (char c : s.toCharArray()) {
        //
        //
        //}


        int[] A = new int[]{1,2,3,0,0,0};
        int[] B = new int[]{2,5,6};
        merge(A,3,B,3);



    }

    public static void merge(int[] A, int m, int[] B, int n) {

        int p = 0;
        int q = 0;

        int[] ret = new int[m+n];
        while (p <= m || q <= n) {
            if (A[p] <= B[q]) {
                ret[p] = A[p];
                p++;
            } else if (A[p] > B[q]) {
                ret[p + q] = B[q];
                q++;
            }
        }

        for (int i = 0; i < m + n; ++i) {
            A[i] = ret[i];
        }

    }

    public static int calculate(String s) {
        Stack<Integer> stack = new Stack();
        Stack<Character> opStack = new Stack();

        int i = 0;
        int n = s.length();
        while (i < n) {
            //空格跳过
            char c = s.charAt(i);
            if (c == ' ') {
                i++;
                continue;
            }
            if (Character.isDigit(c)) {
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }

                if (!opStack.isEmpty()) {
                    char op = opStack.peek();
                    if (op == '*' || op == '/') {
                        int ret = operate(opStack.pop(), stack.pop(), num);
                        stack.push(ret);
                        continue;
                    }
                }
                stack.push(num);
            } else {
                opStack.push(c);
                i++;

            }




        }

        if (opStack.isEmpty()) {
            return stack.pop();
        }
        int result = 0;
        while (!opStack.isEmpty()) {
            int b = stack.pop();
            int a = stack.pop();
            result += operate(opStack.pop(), a, b);
        }
        return result;





    }

    private static int operate(char op, int a, int b) {
        if (op == '+') {
            return a + b;
        } else if (op == '-') {
            return a - b;
        } else if (op == '*') {
            return a * b;
        } else {
            return a / b;
        }


    }

    public static boolean canJump(int[] nums) {

        int k = 0;
        for (int i = 0; i < nums.length; i++){
            if (i > k) return false;
            k = Math.max(k, i + nums[i]);
        }
        return true;
    }


    public static String tictactoe(String[] board) {
        //生成二维数组
        int first = board.length;
        String[][] newBoard = new String[first][first];
        for (int i = 0; i < first; i++) {
            char[] arr = board[i].toCharArray();
            for (int j = 0; j < first; j++) {
                newBoard[i][j] = String.valueOf(arr[j]);
            }
        }



        //全部填满
        Map<Integer, Integer> map = new HashMap();
        //行处理和列处理
        for (int i = 0; i < first; i++) {
            int countCol = 0;
            int countRow = 0;
            for (int j = 0; j < first - 1; j++) {
                if (newBoard[i][j] == newBoard[i][j+1]) {
                    countRow++;
                }
                if (countRow + 1 == first) {
                    return newBoard[i][0];
                }
                if (newBoard[j][i] == newBoard[j+1][i]) {
                    countCol++;
                }
                if (countCol + 1 == first) {
                    return newBoard[0][j];
                }
            }
        }

        int count = 0;
        int count1 = 0;
        //斜对角处理
        for (int i = 0; i < first - 1; i++) {
            if (newBoard[i][i] == newBoard[i+1][i+1]) {
                count++;
            }
            if (count + 1 == first) {
                return newBoard[0][0];
            }

            if (newBoard[i][first-1-i] == newBoard[i+1][first-2-i]) {
                count1++;
            }
            if (count1 + 1 == first) {
                return newBoard[0][first - 1];
            }
        }
        return "Draw";




    }




    public static String reverseWords(String s) {
        String[] original = s.split(" ");
        int nums = original.length - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = nums; i >= 0; i--) {
            if (!original[i].equals("")) {
                sb.append(original[i]).append(" ");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static boolean isPalindrome(String s) {

        int nums = s.length();

            StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums; i++) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                sb.append(s.charAt(i));
            }
        }
        int left = 0;
        int right = sb.length() - 1;
        
        while (left < right) {
            if (Character.toLowerCase(sb.charAt(left)) == Character.toLowerCase(sb.charAt(right))) {
                left++;
                right--;

            } else {
                return false;
            }
        }
        return true;

    }

    public static boolean oneEditAway(String first, String second) {

        //长度大的去掉一个字符与长度小的比较是否相等

        //first和second长度只能相差1
        int flen = first.length();
        int slen = second.length();
        if (flen > slen && flen - slen > 1) {
            return false;
        }
        if (slen > flen && slen - flen > 1) {
            return false;
        }

        int count = 0;
        int n = 0;
        boolean flag = false;

        for (int i = 0; i < flen; i++) {
            for (int j = n; j < slen; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    if (i == j) {
                        n = i + 1;
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                flag = false;
                count++;
                if (count >= 2) {
                    return false;
                }
            }



        }

        return true;






    }
}
