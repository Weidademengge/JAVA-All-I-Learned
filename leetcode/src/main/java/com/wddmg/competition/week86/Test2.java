package com.wddmg.competition.week86;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/04/21 21:52 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(3);
        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(0);
        list2.add(1);
        List<Integer> list3 = new ArrayList<>();
        list3.add(2);
        List<Integer> list4 = new ArrayList<>();
        list4.add(0);
        List<List<Integer>> list = new ArrayList<>();
        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);
        boolean res = canVisitAllRooms(list);
        System.out.println(res);
    }
    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int len = rooms.size();
        int[] bit = new int[len];
        bit[0] = 1;
        Stack<Integer> stack = new Stack<>();
        rooms.get(0).forEach(e->stack.add(e));
        while(!stack.isEmpty()){
            int curIndex = stack.peek();
            if(bit[curIndex] != 1){
                bit[curIndex] = 1;
                stack.pop();
                rooms.get(curIndex).forEach(e->stack.add(e));
            }else{
                stack.pop();
            }
        }
        for (int i = 0; i < bit.length; i++) {
            if(bit[i] == 0){
                return false;
            }
        }
        return true;
    }
}
