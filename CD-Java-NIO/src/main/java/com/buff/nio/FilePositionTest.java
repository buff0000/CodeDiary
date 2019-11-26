package com.buff.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-26 23:24
 */
public class FilePositionTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\Users\\vicya\\AppData\\Local\\Temp\\holy8091112849738718351.tmp", "r");
        // Set the file position
        randomAccessFile.seek(1000);

        //This will print "0"
        RandomAccessFile randomAccessFile1 = new RandomAccessFile("C:\\Users\\vicya\\AppData\\Local\\Temp\\holy8091112849738718351.tmp", "r");
        System.out.println("file pos: " + randomAccessFile1.getFilePointer());


        // Create a channel from the file
        FileChannel fileChannel = randomAccessFile.getChannel();
        // This will print "1000"
        System.out.println("file pos: " + fileChannel.position());
        // Change the position using the RandomAccessFile object
        randomAccessFile.seek(500);
        // This will print "500"
        System.out.println("file pos: " + fileChannel.position());
        // Change the position using the FileChannel object
        fileChannel.position(200);
        // This will print "200"
        System.out.println("file pos: " + randomAccessFile.getFilePointer());
    }
}
