package kvu.apidemo;

import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 *
 * @author VuKD
 */


@Repository
public class ItemRepository {

    private final AtomicLong counter = new AtomicLong();
    private final List<Item> items = Collections.synchronizedList(new ArrayList<>());
    public static final int ELLAPSED_TIME = 2;  //within 2 the last seconds
    public static final int RECENT_POSTED_SIZE = 100; //the last 100 POSTed items


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
        List<Item> listRecent2secs = new ArrayList<>();
        for (int i=0; i<items.size(); i++){
            Item item = items.get(i);
            long diffAsSeconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
            System.out.println(item.getId() + " timestamp: "+ item.getTimestamp() + " -->"+ Duration.between(item.getTimestamp(), Instant.now()).getSeconds());

            if (diffAsSeconds<= ELLAPSED_TIME) {
                //listRecent2secs.add(i, item);

                listRecent2secs.add(item);
            }
        }
        /*
        items.forEach((item) -> {
            //long seconds = Instant.now().minusSeconds(item.getTimestamp().getEpochSecond()).getEpochSecond();
            long diffAsSeconds = ChronoUnit.SECONDS.between(item.getTimestamp(), Instant.now());
            System.out.println(item.getId() + " timestamp: "+ item.getTimestamp() + " -->"+ Duration.between(item.getTimestamp(), Instant.now()).getSeconds());
            if (diffAsSeconds<=ELLAPSED_TIME) {
                listRecent2secs.add(item);
            }
        });*/

        System.out.println("size "+listRecent2secs.size() + "---" + items.size());
        List<Item>listLast100=new ArrayList<>(items.size());
        if (items.size()> RECENT_POSTED_SIZE){
            listLast100 = items.subList(items.size()- RECENT_POSTED_SIZE, items.size());
            System.out.println("list: " + items.size() + ", subList: " + listLast100.size());
            System.out.println("last item in sublist: "+ listLast100.get(listLast100.size()-1).toString());
            System.out.println(Arrays.toString(listLast100.toArray()));
        }

        if (listRecent2secs.size()>listLast100.size()){
            return listRecent2secs;
        }else{
            return listLast100;
        }
    }

    public int getCount() {
        return items.size();
    }
}
