package kvu.apidemo;

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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiDemoApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }


    @Test
    public void testCreatedA() throws Exception {
        this.mvc.perform(post("/items")).andExpect(status().isCreated())
                .andExpect(jsonPath("id", equalTo(1)));
    }

    @Test
    public void testCreatedB() throws Exception {
        for (int i = 0; i < 4999; i++) {
            this.mvc.perform(post("/items")).andExpect(status().isCreated());
        }
    }


    @Test
    public void testGet() throws Exception {
        this.mvc.perform(get("/items")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5000)));
    }


}
