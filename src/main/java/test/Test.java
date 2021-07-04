package test;

import java.util.*;

public class Test {                         //1.第一步，准备加载类

    public static void main(String[] args) {
        //Test test = new Test();
        //Test.run();//4.第四步，new一个类，但在new之前要处理匿名代码块


        int[][] matrix = {{1,3,5,7},{10,11,16,20},{23,30,34,60}};

        //System.out.println(searchMatrix(matrix, 3));
        //ListNode head1 = new ListNode(1);
        //ListNode head2 = new ListNode(2);
        //ListNode head3 = new ListNode(3);
        //ListNode head4 = new ListNode(3);
        //ListNode head5 = new ListNode(2);
        //ListNode head6 = new ListNode(1);
        //head1.next = head2;
        //head2.next = head3;
        //head3.next = head4;
        //head4.next = head5;
        //head5.next = head6;
        //System.out.println(removeDuplicateNodes(head1));
        Map<String, Integer> map = new HashMap();

        Set<Character> map1 = new HashSet<>();
        //map1.contains()
        //map1.remove()



        test(1);


    }

    public static void test(int sum) {
        if (sum == 10) return;

        sum = sum + 1;

        test(sum);
        System.out.println(sum);


    }

    public static ListNode removeDuplicateNodes(ListNode head) {

        Set<ListNode> set = new HashSet();
        ListNode newHead = new ListNode(0);
        ListNode tail = newHead;
        while (head != null) {
            if (set.add(head)) {
                tail.next = head;
                tail = head;
            }
            head = head.next;

        }
        tail.next = null;
        return newHead.next;

    }



    public static boolean searchMatrix(int[][] matrix, int target) {


        //属于哪一行
        int row = matrix.length;
        int col = matrix[0].length;
        //小于最小值，大于最大值 直接false
        if (target < matrix[0][0] || target > matrix[row-1][col-1]) {
            return false;
        }

        int targetRow = 0;
        for (int i = 0; i < row; i++) {
            if (target <= matrix[i][col-1]) {
                targetRow = i;
            }
        }

        int[] ret = new int[col];
        for (int i = 0; i < col; i++) {
            ret[i] = matrix[targetRow][i];
        }

        int low = 0;
        int high = ret.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (ret[mid] == target) {
                return true;

            } else if (ret[mid] > target) {
                high = mid - 1;

            } else {
                low = mid + 1;
            }

        }
        return false;






    }


    public static boolean isPerfectSquare(int num) {
        //在1到 num/2 +1 之间找到一个数的平方= num

        int low = 1;
        int high = num / 2 + 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            long mid2 = mid * mid;
            if (mid2 == num) {
                return true;
            } else if (mid2 > num) {
                high = mid - 1;

            } else {
                low = mid + 1;
            }

        }
        return false;

    }


    public char voiq() {
        return (char) -1;
    }

    static int num = 4;                    //2.第二步，静态变量和静态代码块的加载顺序由编写先后决定

    {
        num += 3;
        System.out.println("b");           //5.第五步，按照顺序加载匿名代码块，代码块中有打印
    }

    int a = 5;                             //6.第六步，按照顺序加载变量

    { // 成员变量第三个
        System.out.println("c");           //7.第七步，按照顺序打印c
    }

    Test() { // 类的构造函数，第四个加载
        System.out.println("d");           //8.第八步，最后加载构造函数，完成对象的建立
    }

    static {                              // 3.第三步，静态块，然后执行静态代码块，因为有输出，故打印a
        System.out.println("a");
    }

    static void run()                    // 静态方法，调用的时候才加载// 注意看，e没有加载
    {
        System.out.println("e");
    }


    public static class ListNode {
    int val;
    ListNode next;
     ListNode(int x) { val = x; }
 }
}