package test;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-05 下午11:54
 */
public class MedianFinder {

    PriorityQueue<Integer> minQueue = new PriorityQueue<>(new Comparator<Integer>(){
        @Override
        public int compare(Integer num1, Integer num2) {
            return num1- num2;

        }
    });

    PriorityQueue<Integer> maxQueue = new PriorityQueue<>(new Comparator<Integer>(){
        @Override
        public int compare(Integer num1, Integer num2) {
            return num2- num1;

        }
    });

    /** initialize your data structure here. */
    public MedianFinder() {

    }

    public void addNum(int num) {
        if (maxQueue.isEmpty()) {
            maxQueue.add(num);
        } else {
            if (maxQueue.peek() >= num) {
                maxQueue.add(num);
            } else {
                minQueue.add(num);
            }
        }

    }

    public double findMedian() {
        if (Math.abs(minQueue.size()-maxQueue.size()) > 1) {
            if (minQueue.size() > maxQueue.size()) {
                while (Math.abs(minQueue.size()-maxQueue.size()) != 1) {
                    maxQueue.add(minQueue.poll());
                }
                return findMedian();
            } else {
                while (Math.abs(maxQueue.size()-minQueue.size()) != 1) {
                    minQueue.add(maxQueue.poll());
                }
                return findMedian();
            }
        } else if (minQueue.size() == maxQueue.size()) {
            return (double) (minQueue.peek() + maxQueue.peek()) / 2;
        } else {
            if (minQueue.size() > maxQueue.size()) {
                return minQueue.peek();

            } else {
                return maxQueue.peek();
            }

        }


    }

    public static void main(String[] args) {

        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(0);
        medianFinder.addNum(0);
        System.out.println(medianFinder.findMedian());

    }
}
