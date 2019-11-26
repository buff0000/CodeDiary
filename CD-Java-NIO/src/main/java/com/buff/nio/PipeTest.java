package com.buff.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-27 0:58
 */
public class PipeTest {
    public static void main(String[] args) throws IOException {
        // Wrap a channel around stdout
        WritableByteChannel out = Channels.newChannel(System.out);
        // Start worker and get read end of channel
        //workerChannel其实是Pipe的source方法返回的管道通道
        ReadableByteChannel workerChannel = startWorker(10);
        ByteBuffer buffer = ByteBuffer.allocate(100);
        while (workerChannel.read(buffer) >= 0) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }


    // This method could return a SocketChannel or
    // FileChannel instance just as easily
    private static ReadableByteChannel startWorker(int reps) throws IOException {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        //Worker线程将要或正在向Pipe的sink取得的通道写数据，那么我们可以通过source获取的通道读数据，
        //此处将source通道返回，交由调用者读数据
        return (pipe.source());
    }

    /**
     * A worker thread object which writes data down a channel.
     * * Note: this object knows nothing about Pipe, uses only a
     * * generic WritableByteChannel.
     */
    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;

        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }

        // Thread execution begins here
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            try {
                for (int i = 0; i < this.reps; i++) {

                    //把数据写到buffer中
                    doSomeWork(buffer);
                    // channel may not take it all at once
                    //把数据写入通道中，该通道是Pipe的sink操作获取的，相当于output
                    while (channel.write(buffer) > 0) {
                        // empty
                    }
                }
                this.channel.close();
            } catch (Exception e) {
                // easy way out; this is demo code
                e.printStackTrace();
            }
        }

        private String[] products = {
                "No good deed goes unpunished",
                "To be, or what?",
                "No matter where you go, there you are",
                "Just say \"Yo\"",
                "My karma ran over my dogma"
        };

        private Random rand = new Random();

        /**
         * 从products数组中随机获取一个字符串，写到参数ByteBuffer中
         * @param buffer
         */
        private void doSomeWork(ByteBuffer buffer) {
            int product = rand.nextInt(products.length);
            buffer.clear();
            buffer.put(products[product].getBytes());
            buffer.put("\r\n".getBytes());
            buffer.flip();
        }
    }


}
