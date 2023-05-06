package hu.bme.aut.webshop.alf2023javant.repository;

import hu.bme.aut.webshop.alf2023javant.entity.Order;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
