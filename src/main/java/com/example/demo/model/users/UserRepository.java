/*
 * (c) 2016 Prismatix, Inc.
 *
 * NOTICE:  All source code, documentation and other information
 * contained herein is, and remains the property of Prismatix, Inc.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Prismatix, Inc.
 */
package com.example.demo.model.users;

import jp.xet.sparwings.spring.data.repository.ChunkableRepository;
import jp.xet.sparwings.spring.data.repository.WritableRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends ChunkableRepository<User, String>, WritableRepository<User, String> {
	Optional<User> findByUserId(@Param("userId") String userId);
}
