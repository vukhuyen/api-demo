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
    private List<Item> items = Collections.synchronizedList(new ArrayList<>());

    public Item create(Item element) {
        items.add(element);
        element.setId(counter.incrementAndGet());
        element.setTimestamp(Instant.now());
        return element;
    }

    /**
     * Return the list of items POSTed in the last 2 seconds or the list of last 100 POSTed items, whichever greater
     * @return
     */
    public List<Item> getItems() {
        Duration twoSeconds = Duration.of(1, ChronoUnit.SECONDS);
        //long timeBetween = Duration.between(Instant.now(), item.getTimestamp()).getSeconds();
        //long seconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
        List<Item> collect = new ArrayList<>();

//        List<Item> collect = items.stream()
//                //.filter(item -> Instant.now().minusSeconds(item.getTimestamp().getEpochSecond()).getEpochSecond() <= 3600)
//                .filter(item -> OffsetTime.now().minusSeconds(item.getTimestamp().getEpochSecond()).getSecond()<=3600)
//                .collect(Collectors.toList());

        long MAX_DURATION = SECONDS.convert(2, SECONDS);
        System.out.println("MAX_DURATION " + MAX_DURATION);
        for (Item item: items){
            //long seconds = Instant.now().minusSeconds(item.getTimestamp().getEpochSecond()).getEpochSecond();
            long diffAsSeconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
            System.out.println(item.getId() + " -->"+ Duration.between(item.getTimestamp(), Instant.now()).getSeconds());
            //System.out.println(Instant.now().getEpochSecond() + ": " + item.getId() + ", time " + item.getTimestamp().getEpochSecond() +", gap: " + diffAsSeconds);
            if (diffAsSeconds<=MAX_DURATION){
                collect.add(item);
            }
        }

        System.out.println("size "+collect.size() + "---" + items.size());
        if (collect.size()>items.size()){
            return collect;
        }else{
            return items;
        }
    }

    public int getCount() {
        return items.size();
    }
}
