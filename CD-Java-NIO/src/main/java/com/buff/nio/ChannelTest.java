package com.buff.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-25 23:15
 */
public class ChannelTest {
    private String filePathForRead = "";
    private String filePathForWrite = "";
    private final String fileNameForRead = "SourceFile.txt";
    private final String fileNameForWrite = "DestFile.txt";

    public ChannelTest() {
        filePathForRead = this.getClass().getResource("/").getPath() + fileNameForRead;
        filePathForWrite = this.getClass().getResource("/").getPath() + fileNameForWrite;
    }

    public static void main(String[] args) throws IOException {
        ChannelTest test = new ChannelTest();

        /**
         * 测试scatter
         */
        FileInputStream fileInputStream = new FileInputStream(new File(test.getFilePathForRead()));
        FileChannel readChannel = fileInputStream.getChannel();
        ByteBuffer header = ByteBuffer.allocateDirect(10);
        ByteBuffer body = ByteBuffer.allocateDirect(80);
        ByteBuffer[] buffers = {header, body};
        long bytesRead = readChannel.read(buffers);
        System.out.println(bytesRead);

        printBufferEffectiveData(header);
        printBufferEffectiveData(body);

        readChannel.close();
        fileInputStream.close();


        /**
         * 测试gather
         */

        FileOutputStream fileOutputStream = new FileOutputStream(new File(test.getFilePathForWrite()),false);
        FileChannel writeChannel = fileOutputStream.getChannel();

        ByteBuffer header1 = ByteBuffer.allocate(10);
        header1.put("0abcdef1".getBytes()).flip();
        header1.position(1).limit(7);

        ByteBuffer body1 = ByteBuffer.allocate(10);
        body1.put("ghijk".getBytes()).flip();

        ByteBuffer[] buffers1 = {header1, body1};
        long bytesWritten = writeChannel.write(buffers1);
        System.out.println(bytesWritten);
        writeChannel.close();
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    /**
     * 打印ByteBuffer有效数据
     *
     * @param buffer
     */
    public static void printBufferEffectiveData(ByteBuffer buffer) {
        buffer.flip();
        buffer.mark();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get() + ",");
        }
        buffer.reset();
        System.out.println();
    }

    public String getFilePathForRead() {
        return filePathForRead;
    }

    public String getFilePathForWrite() {
        return filePathForWrite;
    }
}
