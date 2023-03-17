package com.example.demo.model.users;

import com.miragesql.miragesql.annotation.Column;
import com.miragesql.miragesql.annotation.PrimaryKey;
import com.miragesql.miragesql.annotation.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

	@Id
	@PrimaryKey(generationType = PrimaryKey.GenerationType.APPLICATION)
	@Setter(AccessLevel.PACKAGE)
	@Column(name = "user_id")
	private String userId;

	@Column(name = "name")
	private String name;

	@Column(name = "foo")
	private String foo;
	
	public User(String name, String foo) {
		this.userId = UUID.randomUUID().toString();
		this.name = name;
		this.foo = foo;
	}
}
