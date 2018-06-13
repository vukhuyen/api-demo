package kvu.apidemo;

import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 *
 * @author VuKD
 */


@Repository
public class ItemRepository {

    private final AtomicLong counter = new AtomicLong();
    private final List<Item> items = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_DURATION = 2;  //within 2 the last seconds
    private static final int MAX_LAST_POSTED = 100; //the last 100 POSTed items


    /**
     * Create a new item object
     * @param element
     * @return an item object
     */
    public Item create(Item element) {
        items.add(element);
        element.setId(counter.incrementAndGet());
        element.setTimestamp(Instant.now());
        return element;
    }

    /**
     * 
     * @return the list of items POSTed in the last 2 seconds or the list of last 100 POSTed items, whichever greater
     */
    public List<Item> getItems() {
        if (items.isEmpty()) return items;
        Duration twoSeconds = Duration.of(1, ChronoUnit.SECONDS);
        List<Item> collect = new ArrayList<>();
        for (int i=0; i<items.size(); i++){
            Item item = items.get(i);
            long diffAsSeconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
            System.out.println(item.getId() + " timestamp: "+ item.getTimestamp() + " -->"+ Duration.between(item.getTimestamp(), Instant.now()).getSeconds());
            if (diffAsSeconds<=MAX_DURATION) {
                collect.add(i, item);
            }
        }
        /*
        items.forEach((item) -> {
            //long seconds = Instant.now().minusSeconds(item.getTimestamp().getEpochSecond()).getEpochSecond();
            long diffAsSeconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
            System.out.println(item.getId() + " timestamp: "+ item.getTimestamp() + " -->"+ Duration.between(item.getTimestamp(), Instant.now()).getSeconds());
            if (diffAsSeconds<=MAX_DURATION) {
                collect.add(item);
            }
        });*/

        System.out.println("size "+collect.size() + "---" + items.size());
        if (collect.size()>items.size()){
            return collect;
        }else{
            if (items.size()>MAX_LAST_POSTED) {
                return items.subList(0, MAX_LAST_POSTED);
            }
            return items;
        }
    }

    public int getCount() {
        return items.size();
    }
}
