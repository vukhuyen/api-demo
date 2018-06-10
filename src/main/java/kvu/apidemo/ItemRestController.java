package kvu.apidemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("items")
public class ItemRestController {

    private final ItemRepository repository;

    @Autowired
    ItemRestController(ItemRepository itemRepository) {
        this.repository = itemRepository;
    }

    /*
    @RequestMapping("/items")
    public Item greeting(@RequestParam(value = "items", defaultValue = "123") String item) {
        return new Item(counter.incrementAndGet());
    }*/

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Item> create(@RequestBody (required = false) Item item) {
            item = repository.create(new Item());
        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @GetMapping
    //@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Item>> getItems() {
        List<Item> items = repository.getItems();
        return new ResponseEntity<Collection<Item>>(items, HttpStatus.OK);
    }

}
