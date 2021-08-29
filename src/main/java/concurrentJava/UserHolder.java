package concurrentJava;


/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-08-08 上午10:51
 */
public class UserHolder {


    private static ThreadLocal<User>  THREAD_LOCAL = new ThreadLocal<>();

    public static User getUser() {
        return THREAD_LOCAL.get();

    }

    public static void setUser(User user) {
        THREAD_LOCAL.set(user);
    }

    public static void remove(User user) {
        THREAD_LOCAL = null;
    }


    public static void main(String[] args) {


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User("张三", 20);
                UserHolder.setUser(user);
                System.out.println(Thread.currentThread().getName() + ":"+ UserHolder.getUser());
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User("李四", 21);
                UserHolder.setUser(user);
                System.out.println(Thread.currentThread().getName() + ":"+ UserHolder.getUser());
            }
        });
        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName() + ":"+ UserHolder.getUser());

    }

    static class User {
        String name;
        int age;


        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + '}';
        }
    }
}
