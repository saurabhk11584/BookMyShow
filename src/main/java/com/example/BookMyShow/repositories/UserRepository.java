package com.example.BookMyShow.repositories;

import com.example.BookMyShow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Select queries will come
    //Spring Data JPA will be used for this purpose
    //Need a query to find user by Id

    //Auto-Generated method to find user details to find user Id
    @Override
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String email);

    @Override
    User save(User user);
}
