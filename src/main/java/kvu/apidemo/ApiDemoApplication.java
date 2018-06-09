package kvu.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Develop a RESTful web service that implement the following 2 APIs
 * <p>
 * 2018-06-07T19:18:53.633Z
 * <p>
 * HTTP POST /items , request body { item:{ id: 123, timestamp: 2016-01-01T23:01:01.000Z } }
 * should return 201 created
 * <p>
 * HTTP GET /items
 * should return the list of items POSTed in the last 2 seconds or the list of last 100 POSTed items, whichever greater.
 * [ {item: {id: 123, timestamp: 2016-01-01T23:01:01.000Z} },  {item: {id: 124, timestamp: 2016-01-01T23:01:01.001Z} },…]
 * <p>
 * <p>
 * <p>
 * Notes:
 * <p>
 * The implementation should maximize throughput and minimize latency
 * <p>
 * The implementation should not rely on an external Database, using heap memory is ok
 * <p>
 * The implementation should run on the JVM and minimize heap footprint
 *
 * @author VuKD
 */

@SpringBootApplication
public class ApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDemoApplication.class, args);
    }
}
