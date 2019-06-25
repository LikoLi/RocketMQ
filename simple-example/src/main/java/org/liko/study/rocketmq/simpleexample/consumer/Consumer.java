package org.liko.study.rocketmq.simpleexample.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author liko
 * @Date 2019/6/25
 * @Version 1.0
 * @Description Consumer
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {

        // Instantiate with specified consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cons");

        // Specify name server addressed.
        consumer.setNamesrvAddr("localhost:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.printf("%s Receiver New Message: %s %n", Thread.currentThread().getName(), list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // Launch the consumer instance
        consumer.start();

        System.out.printf("Consumer started.%n");
    }
}
