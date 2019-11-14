package com.buff.mq;

/**
 * @Author: Buff
 * @Description:
 * @Date: Created in 2019-11-14 11:28
 */
public class ConstantMQInfo {
    public static final String MQ_HOST = "39.97.182.195";
    public static final int MQ_PORT = 20190;
    public static final String MQ_USERNAME = "admin";
    public static final String MQ_PASSWORD = "admin";
    public static final String MQ_VHOST = "buff_vhost";


    public static final String QUEUE_NAME_1 = "q_helloworld";
    public static final String QUEUE_NAME_2 = "q_workqueues";
    public static final String QUEUE_NAME_3 = "q_publishsubscribe_1";
    public static final String QUEUE_NAME_4 = "q_publishsubscribe_2";

    public static final String EXCHANGE_NAME_1 = "ex_fanout";
    public static final String EXCHANGE_NAME_2 = "ex_direct";
    public static final String EXCHANGE_NAME_3 = "ex_topic";

}
