package kvu.apidemo;

import java.time.Instant;
import java.time.temporal.Temporal;


/**
 * @author VuKD
 */
public class Item {

    private Long id;
    private Instant timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp){
        this.timestamp = timestamp;
    }

}
