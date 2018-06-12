package kvu.apidemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("items")
public class ItemRestController {

    private final ItemRepository repository;

    @Autowired
    ItemRestController(ItemRepository itemRepository) {
        this.repository = itemRepository;
    }

    @PostMapping
    public ResponseEntity<Item> create() {
        return new ResponseEntity<>(repository.create(new Item()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Item>> getItems() {
        return new ResponseEntity<>(repository.getItems(), HttpStatus.OK);
    }

}
