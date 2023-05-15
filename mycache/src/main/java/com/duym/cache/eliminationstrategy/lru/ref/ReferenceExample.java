package com.duym.cache.eliminationstrategy.lru.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: ReferenceExample, v 0.1 2023/04/27 13:20 duym Exp $
 */
public class ReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        // strong Reference
//        int counter = 1;
//        List<Ref> container = new ArrayList<>();
//
//        while (true) {
//            int current = counter++;
//            container.add(new Ref(current));
//            System.out.println(current + "Ref 被插入到集合中");
//            TimeUnit.MILLISECONDS.sleep(500);
//        }

        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));

        // soft reference
//        int counter = 1;
//        List<SoftReference<Ref>> container = new ArrayList<>();
//        while(true){
//            int current = counter++;
//            container.add(new SoftReference<Ref>(new Ref(current)));
//            System.out.println(current + "Ref 被插入到集合中");
//            TimeUnit.SECONDS.sleep(1);
//        }

        // weak reference
        int counter = 1;
        List<WeakReference<Ref>> container = new ArrayList<>();
        while(true){
            int current = counter++;
            container.add(new WeakReference<Ref>(new Ref(current)));
            System.out.println(current + "Ref 被插入到集合中");
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }


    private static class Ref {
        private byte[] data = new byte[1024 * 1024];

        private final int index;

        public Ref(int index) {
            this.index = index;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Index [" + index + "] will be GC.");
        }
    }
}
