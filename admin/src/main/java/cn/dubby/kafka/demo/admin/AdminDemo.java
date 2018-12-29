package cn.dubby.kafka.demo.admin;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author dubby
 * @date 2018/12/29 21:13
 */
public class AdminDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "daoxuan.pin.com:9092");
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30000);
        props.put("request.timeout.ms", 10000);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        AdminClient adminClient = AdminClient.create(props);
        adminClient.listTopics().listings().get().forEach(System.out::println);
        System.out.println();

        adminClient.listConsumerGroups().all().get().forEach(System.out::println);
        System.out.println();
    }

}
