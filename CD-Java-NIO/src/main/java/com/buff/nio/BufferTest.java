package com.buff.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-24 17:26
 */
public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.toString());

        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
        System.out.println(buffer.toString());

        buffer.put(0, (byte) 'M').put((byte) 'w');
        System.out.println(buffer.toString());

        buffer.flip();// buffer.limit(buffer.position()).position(0);
        System.out.println(buffer.toString());

        buffer.position(2);// buffer.get(new byte[10], 0,2);
        System.out.println(buffer.toString());

        buffer.compact();
        System.out.println(buffer.toString());

        buffer.position(2).mark().position(4);
        System.out.println(buffer.toString());

        buffer.reset();
        System.out.println(buffer.toString());

        /**
         * 测试equals方法
         */
        ByteBuffer bufferS1 = ByteBuffer.allocate(7);
        bufferS1.put(new byte[]{(byte) 'a', (byte) 'l', (byte) 'i', (byte) 'b', (byte) 'a', (byte) 'b', (byte) 'a'});
        bufferS1.position(3);

        ByteBuffer bufferT1 = ByteBuffer.allocate(10);
        bufferT1.put(new byte[]{(byte) 'b', (byte) 'a', (byte) 'b', (byte) 'a', (byte) 'l', (byte) 'o', (byte) 'v', (byte) 'e', (byte) 'u'});
        bufferT1.position(0);
        bufferT1.limit(4);
        System.out.println(bufferS1.equals(bufferT1));

        /**
         * 测试compareTo方法
         */
        ByteBuffer bufferS2 = ByteBuffer.allocate(4);
        bufferS2.put(new byte[]{(byte) 'g', (byte) 'b', (byte) 'c', (byte) 'd'});//bcd
        bufferS2.position(1);
        //printBufferEffectiveData(bufferS2);
        ByteBuffer bufferT2 = ByteBuffer.allocate(20);
        bufferT2.put(new byte[]{(byte) 'a', (byte) 'b', (byte) 'b', (byte) 'a', (byte) 'a', (byte) 'f'});//ba
        bufferT2.position(2).limit(4);
        //printBufferEffectiveData(bufferT2);
        System.out.println(bufferS2.compareTo(bufferT2));

        /**
         * 测试warp方法
         */
        char[] chars = new char[]{'a','b','c','d'};
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        System.out.println(charBuffer);
        charBuffer.put(3,'f');
        System.out.println(charBuffer);
        System.out.println(chars);


        /**
         * 测试duplicate
         */
        charBuffer = CharBuffer.allocate(8);
        charBuffer.put(new char[]{'a','b','c','d','e','f','g','h'});
        charBuffer.position(3).limit(6);
        System.out.println(charBuffer);

        CharBuffer charBufferCopy = charBuffer.duplicate();
        System.out.println(charBufferCopy);
        charBufferCopy.position(0).limit(8);
        System.out.println(charBufferCopy);

        charBuffer.position(3).limit(6);
        System.out.println(charBuffer);
        charBufferCopy = charBuffer.slice();
        System.out.println(charBufferCopy);
        charBufferCopy.put(1,'G');
        System.out.println(charBufferCopy);
        System.out.println(charBuffer);

    }

    /**
     * 打印ByteBuffer有效数据
     * @param buffer
     */
    public static void printBufferEffectiveData(ByteBuffer buffer) {
        buffer.mark();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get() + ",");
        }
        buffer.reset();
        System.out.println();
    }
}
