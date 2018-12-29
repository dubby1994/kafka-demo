package cn.dubby.kafka.demo.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

/**
 * @author dubby
 * @date 2018/12/29 20:33
 */
public class ProducerDemo {

    private final static String TOPIC = "dubby-topic";

    public static void main(String[] args) {
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

        Random random = new Random();
        Producer<String, String> producer = new KafkaProducer<>(props);
        while (true) {
            try {
                Thread.sleep(random.nextInt(100) + 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            producer.send(new ProducerRecord<>(TOPIC, "key:" + random.nextLong(), "value:" + random.nextLong()));
        }

        //producer.close();
    }

}
