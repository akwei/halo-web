package halo.util;

public class P {

    private P() {//
    }

    public static void println(Object obj) {
        System.out.println(obj.toString());
    }

    public static void print(Object obj) {
        System.out.print(obj.toString());
    }

    public static void printlnRunTime(Object obj) {
        System.out.println("runtime : [ " + obj.toString() + " ]");
    }
}