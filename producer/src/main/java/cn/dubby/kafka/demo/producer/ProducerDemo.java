package cn.dubby.kafka.demo.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author dubby
 * @date 2018/12/29 20:33
 */
public class ProducerDemo {

    private static final Logger logger = LoggerFactory.getLogger("ProducerDemo");

    private final static String TOPIC = "flink-source";

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("delivery.timeout.ms", 30000);
        props.put("request.timeout.ms", 10000);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int j = 0; j < 10000; ++j) {
            for (long i = 0; i < 10000; ++i) {
                Event event = new Event();
                event.setMallId(i);
                event.setTimestamp(System.currentTimeMillis());
                event.setMessageCount(1);

                String json = objectMapper.writeValueAsString(event);
                logger.info(json);
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "key" + event.getTimestamp(), json);

                //同步发送
                RecordMetadata recordMetadata = producer.send(producerRecord).get(10, TimeUnit.SECONDS);
                printMetadata(recordMetadata);
            }
        }

        //异步
//        producer.send(producerRecord, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                printMetadata(recordMetadata);
//            }
//        });

        Thread.sleep(1000);
        producer.close();
    }

    private static void printMetadata(RecordMetadata metadata) {
        logger.info("topic:{}, partition:{}, offset:{}", metadata.topic(), metadata.partition(), metadata.offset());
    }

}
