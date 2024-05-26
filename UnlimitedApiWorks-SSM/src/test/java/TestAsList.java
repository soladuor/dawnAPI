import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestAsList {

    // Arrays.asList 有一些问题，这里做一些测试
    // 结论：不推荐使用，建议自己写一个数组转 ArrayList 的工具类

    @Test
    public void test1_Int() {
        // Arrays.asList 问题1: 将基本数据类型数组转换为List时，会将整个数组作为一个单独的元素
        // int[] arr = {1, 2, 3};
        // List<int[]> ints = Arrays.asList(arr);
        // 解决这个问题只需要使用包装类即可
        Integer[] arr = {1, 2, 3};
        List<Integer> ints = Arrays.asList(arr);

        System.out.println(ints);
    }

    @Test
    public void test2_Add() {
        Integer[] arr = {1, 2, 3};
        // Arrays.asList 问题2: 返回的 List 不支持增删操作

        // 通过 Arrays.asList() 方法返回的 List 调用的 add() 方法是 AbstractList 的 add() 方法
        // java.util.AbstractList#add(int, java.lang.Object)
        // 这个方法的实现是抛出 UnsupportedOperationException 异常
        // List<Integer> ints = Arrays.asList(arr);
        // ints.add(4);

        // 解决这个问题只需要直接用构造方法转成 ArrayList 即可
        List<Integer> ints = new ArrayList<>(Arrays.asList(arr));
        ints.add(4);

        System.out.println(ints);
    }

    @Test
    public void test3_arr() {
        // Arrays.asList 问题3: 对数组的修改会影响到 List
        Integer[] arr = {1, 2, 3};
        // List<Integer> ints = Arrays.asList(arr);

        // 解决这方法与问题2一样，直接用构造方法转成 ArrayList 即可
        List<Integer> ints = new ArrayList<>(Arrays.asList(arr));

        arr[0] = 4;
        System.out.println(ints);
    }

}
