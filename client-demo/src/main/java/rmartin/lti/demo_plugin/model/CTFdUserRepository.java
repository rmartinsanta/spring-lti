package rmartin.lti.demo_plugin.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CTFdUserRepository extends JpaRepository<CTFdUser, Integer> {
    Optional<CTFdUser> findByEmail(String email);
}
