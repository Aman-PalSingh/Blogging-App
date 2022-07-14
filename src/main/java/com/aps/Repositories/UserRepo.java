package com.aps.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aps.Entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface UserRepo extends JpaRepository<User, Integer> {

}
