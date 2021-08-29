package concurrentJava.threadpremessage;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-08-15 下午12:37
 */
public class ThreadPreMessageClient {
    public void send(Child child) {
        new Thread(new Teacher(child)).start();
    }
    class Teacher implements Runnable {

        private Child child;

        public Teacher(Child child) {
            this.child = child;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "传授" + child.name + "知识");

        }
    }
    static class Child {
        String name;
        Child(String name) {
            this.name = name;
        }
    }
    public static void main(String[] args) {
        ThreadPreMessageClient client = new ThreadPreMessageClient();
        client.send(new Child("张三"));
        client.send(new Child("李四"));
    }
}
