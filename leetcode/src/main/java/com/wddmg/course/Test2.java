package com.wddmg.course;

import java.util.Calendar;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/15 14:53 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        Calendar now = Calendar.getInstance();
        Calendar just = Calendar.getInstance();
        just.set(Calendar.SECOND,0);
        just.set(Calendar.MILLISECOND,0);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        just.add(Calendar.MINUTE,-1);
        System.out.println(now.getTime());
        System.out.println(just.getTime());
    }
}
