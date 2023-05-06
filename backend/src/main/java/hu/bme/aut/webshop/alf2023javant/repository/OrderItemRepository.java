package hu.bme.aut.webshop.alf2023javant.repository;

import hu.bme.aut.webshop.alf2023javant.entity.Order;
import hu.bme.aut.webshop.alf2023javant.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findByOrder(Order order);
}
