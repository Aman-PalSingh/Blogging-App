package com.aps.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aps.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
