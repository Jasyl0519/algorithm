package concurrentJava;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-25 下午4:42
 */
public class MutableUser {

    private final String name;
    private final Integer gender;
    private final StringBuilder address;


    public MutableUser(String name, Integer gender, StringBuilder address) {
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public final String getName() {
        return name;
    }

    public final Integer getGender() {
        return gender;
    }

    public final StringBuilder getAddress() {
        return address;
    }

    public static void main(String[] args) {
        StringBuilder address = new StringBuilder("北京市");
        MutableUser user = new  MutableUser("张三",1, address);

        user.getAddress().append("海淀区");

        System.out.println(user.getAddress());


    }
}
