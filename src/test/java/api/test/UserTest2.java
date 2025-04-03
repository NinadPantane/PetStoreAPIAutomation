package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest2 {

	Faker faker = new Faker();
	User userPayload = new User();

	public Logger logger;

	@BeforeClass
	public void setup() {

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		// Code for generating Logs

		logger = LogManager.getLogger(this.getClass());
	}

	@Test(priority = 1)
	public void postTestCreateUser() {

		logger.info("***********Creating User************");
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("*********** User Created ************");

	}

	@Test(priority = 2)
	public void getTestUserByName() throws InterruptedException {

		Thread.sleep(2000);
		logger.info("***********Reading User************");
		Response response = UserEndPoints2.getUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("***********User Info is displayed************");
	}

	@Test(priority = 3)
	public void putTestUpdateUserByName() throws InterruptedException {

		Thread.sleep(2000);
		logger.info("***********Updating User************");

		// Update User by Username
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		// Checking Data after Update

		Response responseAfterUpdate = UserEndPoints2.getUser(this.userPayload.getUsername());
		responseAfterUpdate.then().log().all();

		logger.info("***********User is Updated************");
	}

	@Test(priority = 4)
	public void deleteTestUserByName() throws InterruptedException {

		Thread.sleep(3000);
		logger.info("***********Deleting User************");
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("***********User is Deleted************");

	}
}
