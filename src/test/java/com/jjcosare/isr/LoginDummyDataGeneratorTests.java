package com.jjcosare.isr;

import com.jjcosare.isr.model.Login;
import com.jjcosare.isr.repository.LoginRepository;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.LocalDateRangeRandomizer;
import io.github.benas.randombeans.randomizers.time.LocalTimeRandomizer;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginDummyDataGeneratorTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDummyDataGeneratorTests.class);

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
    MongoTemplate mongoTemplate;

	@Autowired
    LoginRepository loginRepository;

	@Test
    //@Ignore
	public void generateDummyData100k() {
		int numRecords = 100000;
		int numUniqueUsers = 100;
		int numAttribute1Types = 5;
		int numAttribute2Types = 5;
		int numAttribute3Types = 5;
		int numAttribute4Types = 5;
        LocalDate startDate = LocalDate.now().minusMonths(2);
        LocalDate endDate = LocalDate.now().minusMonths(1);

		generateDummyData(numRecords, numUniqueUsers, numAttribute1Types, numAttribute2Types, numAttribute3Types, numAttribute4Types,
				startDate, endDate);
	}

	@Test
	@Ignore
	public void generateDummyData1m() {
		int numRecords = 1000000;
		int numUniqueUsers = 1000;
        int numAttribute1Types = 10;
        int numAttribute2Types = 10;
        int numAttribute3Types = 10;
        int numAttribute4Types = 10;
        LocalDate startDate = LocalDate.now().minusMonths(2);
        LocalDate endDate = LocalDate.now().minusMonths(1);

		generateDummyData(numRecords, numUniqueUsers, numAttribute1Types, numAttribute2Types, numAttribute3Types, numAttribute4Types,
				startDate, endDate);
	}

	@Test
	@Ignore
	public void generateDummyData10m() {
		int numRecords = 10000000;
        int numUniqueUsers = 10000;
        int numAttribute1Types = 20;
        int numAttribute2Types = 20;
        int numAttribute3Types = 20;
        int numAttribute4Types = 20;
        LocalDate startDate = LocalDate.now().minusMonths(2);
        LocalDate endDate = LocalDate.now().minusMonths(1);

		generateDummyData(numRecords, numUniqueUsers, numAttribute1Types, numAttribute2Types, numAttribute3Types, numAttribute4Types,
				startDate, endDate);
	}

	private void generateDummyData(int numRecords, int numUniqueUsers, int numAttribute1Types, int numAttribute2Types,
								   int numAttribute3Types, int numAttribute4Types, LocalDate startDate, LocalDate endDate){
        LOGGER.info(startDate.toString());
        LOGGER.info(endDate.toString());
        mongoTemplate.getDb().drop();

	    // add 1 as randomizer generator not inclusive on max value
        endDate = endDate.plusDays(1);
        numUniqueUsers++;
        numAttribute1Types++;
        numAttribute2Types++;
        numAttribute3Types++;
        numAttribute4Types++;

		for (int i = 0; i < numRecords; i++) {
			LocalDate randomWithinRangeDate = LocalDateRangeRandomizer.aNewLocalDateRangeRandomizer(startDate, endDate).getRandomValue();
			LocalTime randomTime = LocalTimeRandomizer.aNewLocalTimeRandomizer().getRandomValue();

			LocalDateTime loginTime = LocalDateTime.of(randomWithinRangeDate, randomTime);
			String user = "user_"+ IntegerRangeRandomizer.aNewIntegerRangeRandomizer(1, numUniqueUsers).getRandomValue();
			String attribute1 = "attribute1_"+ IntegerRangeRandomizer.aNewIntegerRangeRandomizer(1, numAttribute1Types).getRandomValue();
			String attribute2 = "attribute2_"+ IntegerRangeRandomizer.aNewIntegerRangeRandomizer(1, numAttribute2Types).getRandomValue();
			String attribute3 = "attribute3_"+ IntegerRangeRandomizer.aNewIntegerRangeRandomizer(1, numAttribute3Types).getRandomValue();
			String attribute4 = "attribute4_"+ IntegerRangeRandomizer.aNewIntegerRangeRandomizer(1, numAttribute4Types).getRandomValue();

			Login login = new Login();
			login.setLoginTime(loginTime);
			login.setUser(user);
			login.setAttribute1(attribute1);
			login.setAttribute2(attribute2);
			login.setAttribute3(attribute3);
			login.setAttribute4(attribute4);

			webTestClient.post().uri("/test")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaType.APPLICATION_JSON_UTF8)
					.body(Mono.just(login), Login.class)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
					.expectBody();

			LOGGER.info(login.toString());
		}

	}

}
