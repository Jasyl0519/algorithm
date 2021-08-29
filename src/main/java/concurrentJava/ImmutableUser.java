package concurrentJava;

import java.lang.reflect.Field;

/**
 * Description:
 * Author: weiwei.xie
 * date: 2021-07-25 下午4:34
 */
public class ImmutableUser {

    private final String name;
    private final Integer gender;

    public ImmutableUser(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }

    public final String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ImmutableUser user = new ImmutableUser("张三", 1);

        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        char[] value = (char[]) field.get(user.getName());
        value[0] = '李';
        value[1] = '四'; 
        System.out.println(user.getName());
    }
}
