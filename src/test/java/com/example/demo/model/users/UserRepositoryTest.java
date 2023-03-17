package com.example.demo.model.users;

import com.example.demo.model.RepositoryUnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryUnitTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

	@Autowired
	UserRepository sut;

	@Test
	void testCreate() {
		// setup
		var user = new User("name1", "value1");

		// exercise
		var actual = sut.create(user);

		// verify
		assertThat(actual).isEqualTo(sut.findByUserId(user.getUserId()).orElseThrow(()->new AssertionError("error!")));
	}

}
