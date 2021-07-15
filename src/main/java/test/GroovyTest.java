package test;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

/**
 * @author zhengcheng
 * @date 2021/3/22 6:04 下午
 */
public class GroovyTest {


    public static void main(String args[]) {
        Binding binding = new Binding();
        binding.setVariable("x", 10);
        binding.setVariable("a", 2);
        binding.setVariable("b", 3);
        binding.setVariable("language", "Groovy");

        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("y = x * a; z = y * b;");

        System.err.println(value +", " + value.equals(10));
        System.err.println(binding.getVariable("y") +", " + binding.getVariable("y").equals(20));
        System.err.println(binding.getVariable("z") +", " + binding.getVariable("z").equals(30));
    }



}
interface IFoo {
    Object run(Object foo);
}