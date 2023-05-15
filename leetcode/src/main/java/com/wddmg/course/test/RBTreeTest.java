package com.wddmg.course.test;

import com.wddmg.course.tree.impl.RBTree;

import java.util.Scanner;

/******************************
 *
 * 码炫课堂技术交流Q群：963060292
 * 主讲：smart哥
 *
 ******************************/
public class RBTreeTest {
    public static void main(String[] args) {
        //新增节点
        insertOpt();
        //删除节点
//        deleteOpt();
    }

    /**
     * 插入操作
     */
    public static void insertOpt(){
        Scanner scanner=new Scanner(System.in);
        RBTree<String,Object> rbt=new RBTree<>();
        while (true){
            System.out.println("请输入你要插入的节点:");
            String key=scanner.next();
            System.out.println();
            //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
            if(key.length()==1){
                key="00"+key;
            }else if(key.length()==2){
                key="0"+key;
            }
            rbt.put(key,null);
            TreeOperation.show(rbt.getRoot());
        }
    }

    /**
     * 删除操作
     */
    public static void deleteOpt(){
        RBTree<String,Object> rbt=new RBTree<>();
        //测试1：预先造10个节点（1-10）
//        String keyA=null;
//        for (int i = 1; i <11 ; i++) {
//            if((i+"").length()==1){
//                keyA="00"+i;
//            }else if((i+"").length()==2){
//                keyA="0"+i;
//            }
//            rbt.put(keyA,null);
//        }

        //测试2：包含2位数和3位数的测试代码 1 2 3 4 5 66 77 88 99 100 101
        rbt.put("001",null);
        rbt.put("002",null);
        rbt.put("003",null);
        rbt.put("004",null);
        rbt.put("005",null);
        rbt.put("066",null);
        rbt.put("077",null);
        rbt.put("088",null);
        rbt.put("099",null);
        rbt.put("100",null);
        rbt.put("101",null);

        TreeOperation.show(rbt.getRoot());
        //以下开始删除
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("请输入你要删除的节点:");
            String key=scanner.next();
            System.out.println();
            //这里代码最多支持3位数，3位以上的话红黑树显示太错位了，这里就不重构代码了,大家可自行重构
            if(key.length()==1){
                key="00"+key;
            }else if(key.length()==2){
                key="0"+key;
            }
            //1 2 3 88 66 77 100 5 4 101
            rbt.remove(key);
            TreeOperation.show(rbt.getRoot());
        }
    }
}
