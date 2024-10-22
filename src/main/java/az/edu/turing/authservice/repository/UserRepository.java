package az.edu.turing.authservice.repository;

import az.edu.turing.authservice.domain.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity,Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
