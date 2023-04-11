package hu.bme.aut.webshop.alf2023javant.repository;

import hu.bme.aut.webshop.alf2023javant.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByEmail(String email);

    boolean existsByEmail(String email);

}
