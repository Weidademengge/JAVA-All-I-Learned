package com.duym.nio.c3;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author duym
 * @version $ Id: TestFilesWalkFileTree, v 0.1 2023/03/23 15:56 duym Exp $
 */
public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {

    }
    private static void m2() throws Exception{
        AtomicInteger pdfCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\document"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".pdf")){
                    pdfCount.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(pdfCount);
    }

    private static void m1() throws Exception{
        //这里不能使用int，因为匿名类引用外部的局部变量，是final的
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\document"),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("========>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dir_count:"+dirCount);
        System.out.println("file_count:"+fileCount);
    }
    // 千万别用，删了就找不回来了
    private void delete() throws Exception{
        Files.walkFileTree(Paths.get("D:\\document\\myself\\temp\\1"),new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
