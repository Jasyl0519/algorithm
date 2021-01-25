package leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2020-11-29 21:00
 */
public class Leetcode150 {


    public static void main(String[] args) {
        String[] str = {"2", "1", "+", "3", "*"};
        String[] str1 = {"4", "13", "5", "/", "+"};
        String[] str2 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};

        System.out.println(evalRPN(str));
        System.out.println(evalRPN(str1));
        System.out.println(evalRPN(str2));


    }


    public static int evalRPN(String[] tokens) {

        Set<String> operations = new HashSet<>();
        operations.add("+");
        operations.add("-");
        operations.add("*");
        operations.add("/");

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            if (!operations.contains(token)) {
                stack.add(Integer.valueOf(token));

            } else {
                int val1 = stack.pop();
                int val2 = stack.pop();
                if (token.equals("+")) {
                    stack.add(val2 + val1);
                } else if (token.equals("-")) {
                    stack.add(val2 - val1);
                } else if (token.equals("*")) {
                    stack.add(val2 * val1);
                } else if (token.equals("/")) {
                    stack.add(val2 / val1);
                }
            }

        }

        return stack.pop();

    }

    public static int evalRPN_1(String[] tokens) {

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];

            int val2, val1;
            switch (token) {

                case "+" :
                    val1 = stack.pop();
                    val2 = stack.pop();
                    stack.add(val2 + val1);
                    break;
                case "-" :
                    val1 = stack.pop();
                    val2 = stack.pop();
                    stack.add(val2 - val1);
                    break;
                case "*" :
                    val1 = stack.pop();
                    val2 = stack.pop();
                    stack.add(val2 * val1);
                    break;
                case "/" :
                    val1 = stack.pop();
                    val2 = stack.pop();
                    stack.add(val2 / val1);
                    break;
                default:
                    stack.add(Integer.parseInt(token));
                    break;

            }

        }

        return stack.pop();

    }
}
