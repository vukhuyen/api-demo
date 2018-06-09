package kvu.apidemo;

import java.time.Instant;


/**
 * @author VuKD
 */
public class SAPItem {

    private final long id;
    private final String timeStamp;

    public SAPItem(long id) {
        this.id = id;
        this.timeStamp = Instant.now().toString();
    }

    public long getId() {
        return id;
    }

    public String getTime() {
        return timeStamp;
    }

    public int add() {

        return 1; //success
    }


}
