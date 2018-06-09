package kvu.apidemo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SAPItemRestController {

    private static final String SUCCESS_RESULT = "success";
    private static final String FAILURE_RESULT = "failed";
    private static final int CODE_SUCCESS = 201;
    private final AtomicLong counter = new AtomicLong();

    /*
    private final SAPItemRepository sapItemRepository;

    @Autowired
    SAPItemRestController(SAPItemRepository sapItemRepository) {
        this.sapItemRepository = sapItemRepository;
    }*/

    @RequestMapping("/item")
    public SAPItem greeting(@RequestParam(value = "item", defaultValue = "123") String item) {
        return new SAPItem(counter.incrementAndGet());
    }

    @GetMapping
    Collection<SAPItem> getItems() {
        return null; //todo
    }

    @PostMapping
    public String add() {
        SAPItem item = new SAPItem(counter.incrementAndGet());
        int result = item.add();
        if (result == 1) {
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }

    /*
    @RequestMapping("/items")    
    public HttpEntity<Collection<SAPItem>> listAll() {
        List<SAPItem> items = queryService.getAll().stream().map(this::toResource).collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }*/
}
