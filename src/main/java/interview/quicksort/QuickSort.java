package interview.quicksort;

/**
 * @author zhengcheng
 * @Description:
 * @date 2020/9/22
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {8, 3, 6, 1, 2, 9, 4, 5, 7};

        quickSort(arr, 0, arr.length - 1);

        for (int i : arr) {
            System.out.println(i);
        }


    }

    public static void quickSort(int[] arr, int left, int right) {
        if(left > right){
            return;
        }
        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);


    }

    public static int partition(int[] arr, int left, int right) {

        int point = arr[right];
        while (left < right) {
            while (left < right && arr[left] <= point) {
                left++;
            }

            if (left < right) {
                swap(arr, left, right);
                right--;
            }

            while (left < right && arr[right] >= point) {
                right--;
            }

            if (left < right) {
                swap(arr, left, right);
                left++;
            }

        }

        return right;

    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }
}
