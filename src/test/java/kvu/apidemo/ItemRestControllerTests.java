package kvu.apidemo;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemRestControllerTests {

    private static final int NUM_ITEMS = 20000;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testA_Get() throws Exception {
        this.mvc.perform(get("/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ================== Create 1 Item ==================
    @Test
    public void testB_PostOne() throws Exception {
        this.mvc.perform(post("/items")).andExpect(status().isCreated())
                .andExpect(jsonPath("id", equalTo(1)))
                .andExpect(jsonPath("timestamp", notNullValue()));
    }

    // ================== Create New Items ==================
    @Test
    public void testC_PostMulti() throws Exception {

        for (int i = 1; i < NUM_ITEMS; i++) {
            this.mvc.perform(post("/items")).andExpect(status().isCreated());
        }
    }

    // ================== Get All Items ==================
    @Test
    public void testD_Get() throws Exception {
        this.mvc.perform(get("/items")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(NUM_ITEMS)));
    }
    
    //@Test
    public void testE_getItems() throws Exception {
        List<Item>aList = new ArrayList<>();
        ItemRepository ir = new ItemRepository();
        for (int i = 0; i < NUM_ITEMS; i++) {
             ir.create(new Item());
        }
        aList.addAll(ir.getItems());
        assertEquals(NUM_ITEMS, aList.size());
    }
    
    @Test
    public void test01() throws InterruptedException, ExecutionException {
        test(1);
    }
    
    
    private void test(final int threadCount) throws InterruptedException, ExecutionException {
        final UniqueIdGenerator domainObject = new UniqueIdGenerator();
        Callable<Long> task = domainObject::nextId;
        List<Callable<Long>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Long>> futures = executorService.invokeAll(tasks);
        List<Long> resultList = new ArrayList<>(futures.size());
        // Check for exceptions
        for (Future<Long> future : futures) {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        List<Long> expectedList = new ArrayList<>(threadCount);
        for (long i = 1; i <= threadCount; i++) {
            expectedList.add(i);
        }
        Collections.sort(resultList);
        Assert.assertEquals(expectedList, resultList);
    }
    
    static class UniqueIdGenerator {
        private final AtomicLong counter = new AtomicLong();

        public long nextId() {
            return counter.incrementAndGet();
        }
    }

    
//
//    // ================== CORS HEADER ==================
//    @Test
//    public void test_cors_headers() throws Exception {
//        this.mvc.perform(get("/items"))
////                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
//                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET"))
//                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
//                .andExpect(header().string("Access-Control-Max-Age", "3600"));
//    }
}
