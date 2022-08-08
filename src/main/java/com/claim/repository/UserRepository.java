package com.claim.repository;

import com.claim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
	@Query("SELECT s FROM User s WHERE s.username=?1 AND s.password=?2")
	User login(String username, String password);

	@Query("SELECT s FROM User s INNER JOIN UserSpotify u on u.id = s.userSpotify WHERE u.state=?1")
	User findByState(String state);
}