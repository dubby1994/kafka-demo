package cn.dubby.kafka.demo.producer;

/**
 * @author dubby
 * @date 2019/2/1 14:48
 */
public class Event {

    private long timestamp;

    private Long mallId;

    private long messageCount;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }

}
