package com.image.save.com.image.save.repo;

import com.image.save.com.image.save.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    public User findByName(String name);
}
