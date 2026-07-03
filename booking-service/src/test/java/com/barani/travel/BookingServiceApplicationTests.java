package com.barani.travel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest(
		properties = {
				"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
		}
)

class BookingServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
