package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.dto.OrderDto;
import hu.bme.aut.webshop.alf2023javant.dto.OrderItemDto;
import hu.bme.aut.webshop.alf2023javant.entity.Order;
import hu.bme.aut.webshop.alf2023javant.entity.OrderItem;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.OrderItemRepository;
import hu.bme.aut.webshop.alf2023javant.repository.OrderRepository;
import hu.bme.aut.webshop.alf2023javant.repository.ProductRepository;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import hu.bme.aut.webshop.alf2023javant.service.EmailSenderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping
    @CrossOrigin
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByEmail(username);

        Order order = new Order();
        order.setShipping_address(orderDto.getShipping_address());
        order.setShipping_city(orderDto.getShipping_city());
        order.setShipping_zip(orderDto.getShipping_zip());
        order.setCreated_at(String.valueOf(new java.sql.Date(System.currentTimeMillis())));
        order.setComment(orderDto.getComment());
        order.setUser(userRepository.getReferenceById(user.get().getId()));
        orderRepository.save(order);

        for (OrderItemDto orderItemDto : orderDto.getProducts()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderRepository.getReferenceById(order.getId()));
            orderItem.setProduct(productRepository.getReferenceById(orderItemDto.getProduct_id()));
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItemRepository.save(orderItem);
        }

        List<String> answer = orderDto.getProducts().stream().map(
                product ->
                        String.join(
                                " ",
                                "Product:",
                                productRepository.getReferenceById(product.getProduct_id()).getName(),
                                "Quantity:",
                                product.getQuantity().toString()
                        )
        ).toList();

        emailSenderService.sendEmail(
                user.get().getEmail(),
                "Order",
                String.join("\n",
                        "You have ordered the following things:",
                        String.join("\n", answer)
                )
        );

        return ResponseEntity.ok(order);
    }

    public class ResponseTransferObject {
        public Long id;
        public String created_at;
        public String shipping_zip;
        public String shipping_city;
        public String shipping_address;
        public String comment;
        public List<OrderItemDto> orderItems;

        public ResponseTransferObject(
                Long id,
                String created_at,
                String shipping_zip,
                String shipping_city,
                String shipping_address,
                String comment,
                List<OrderItem> orderItems
        ) {
            this.id = id;
            this.created_at = created_at;
            this.shipping_zip = shipping_zip;
            this.shipping_city = shipping_city;
            this.shipping_address = shipping_address;
            this.comment = comment;
            this.orderItems = orderItems.stream().map(
                    orderItem -> new OrderItemDto(
                            orderItem.getProduct().getId(),
                            orderItem.getQuantity())
            ).toList();
        }
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders.stream().map(
                        order -> new ResponseTransferObject(
                                order.getId(),
                                order.getCreated_at(),
                                order.getShipping_zip(),
                                order.getShipping_city(),
                                order.getShipping_address(),
                                order.getComment(),
                                order.getOrderItems()
                        )
                )
        );
    }
}