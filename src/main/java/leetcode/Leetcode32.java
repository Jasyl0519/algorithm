package leetcode;

import java.util.Stack;

/**
 * @author zhengcheng
 * @Description:
 * @date 2020/9/18
 */
public class Leetcode32 {

    //public static void main(String[] args) {
    //    System.out.println(longestValidParentheses_stack("()(()"));
    //    System.out.println(longestValidParentheses_stack("(()"));
    //    System.out.println(longestValidParentheses_stack("(()()"));
    //    System.out.println(longestValidParentheses_stack("(()())"));
    //
    //}

    /**
     * 栈
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses_stack(String s) {


        int len = s.length();

        int max_length = 0;

        Stack<Integer> stack = new Stack();
        stack.add(-1);
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            if ('(' == ch) {
                stack.add(i);

            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.add(i);

                } else {
                    max_length = Math.max(max_length, i - stack.peek());
                }

            }

        }
        return max_length;

    }


    //public static void main(String[] args) {
    //    System.out.println(longestValidParentheses_2("()(()"));
    //
    //}

    /**
     * 正反向括号
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses_2(String s) {

        int left = 0, right = 0, maxlength = 0;

        int len = s.length();

        for (int i = 0; i < len; i++) {

            char ch = s.charAt(i);
            if ('(' == ch) {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(left * 2, maxlength);

            } else if (right > left) {
                left = right = 0;

            }

        }

        left = right = 0;

        for (int i = len - 1; i >= 0; i--) {

            char ch = s.charAt(i);
            if ('(' == ch) {
                left++;

            } else {
                right++;
            }

            if (left == right) {
                maxlength = Math.max(maxlength, right * 2);

            } else if (left > right) {
                left = right = 0;

            }


        }


        return maxlength;

    }


    public static void main(String[] args) {
        System.out.println(longestValidParentheses_dp("(()())"));
        System.out.println(longestValidParentheses_dp("()"));
        System.out.println(longestValidParentheses_dp("()(("));
        System.out.println(longestValidParentheses_dp("()(()"));
        System.out.println(longestValidParentheses_dp(")()())"));
        System.out.println(longestValidParentheses_stack("()(()"));
        System.out.println(longestValidParentheses_stack("(()"));
        System.out.println(longestValidParentheses_stack("(()()"));
        System.out.println(longestValidParentheses_stack("(()())"));

    }

    /**
     * 动态规划
     * <p>
     * dp[i] = dp[i-2] + 2;
     * dp[i] = dp[i-1] + 2 + dp[i - dp[i-1] - 2];
     *
     * @param s
     * @return
     */
    public static int longestValidParentheses_dp(String s) {


        int len = s.length();
        if (len <= 1) {
            return 0;
        }

        int[] dp = new int[s.length()];
        char[] chars = s.toCharArray();

        if (chars[0] == '(' && chars[1] == ')') {
            dp[1] = 2;
        }

        int max = dp[1];

        for (int i = 2; i < s.length(); i++) {


            if ('(' == s.charAt(i)) {
                dp[i] = 0;

            } else {
                if ('(' == s.charAt(i - 1)) {
                    dp[i] = dp[i - 2] + 2;

                } else {
                    if (i - dp[i - 1] - 1 >= 0 && chars[i - dp[i - 1] - 1] == '(') {
                        if (i - dp[i - 1] - 2 >= 0) {
                            dp[i] = 2 + dp[i - 1] + dp[i - dp[i - 1] - 2];
                        } else {
                            dp[i] = 2 + dp[i - 1];
                        }

                    }

                }


            }
            max = Math.max(max, dp[i]);


        }


        return max;

    }

}
