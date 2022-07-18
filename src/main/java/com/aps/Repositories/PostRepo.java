package com.aps.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.Entities.Category;
import com.aps.Entities.Post;
import com.aps.Entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);
}
