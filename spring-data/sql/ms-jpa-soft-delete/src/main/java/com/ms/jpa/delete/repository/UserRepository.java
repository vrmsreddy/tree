package com.ms.jpa.delete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.jpa.delete.entity.User;

/**
 * @author MS
 * 
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query(value = "SELECT count(u.id) FROM t_user u WHERE u.deleted='true'", nativeQuery = true)
  long countDeletedEntries();
}
