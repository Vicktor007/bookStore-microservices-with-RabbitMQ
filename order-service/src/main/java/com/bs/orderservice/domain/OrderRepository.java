package com.bs.orderservice.domain;

import com.bs.orderservice.domain.records.OrderStatus;
import com.bs.orderservice.domain.records.OrderSummary;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByStatus(OrderStatus status);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus status) {
        OrderEntity orderEntity =
                this.findByOrderNumber(orderNumber).orElseThrow(() -> new OrderNotFoundException(orderNumber));
        orderEntity.setStatus(status);
        this.save(orderEntity);
    }

    @Query(
            """
select new com.bs.orderservice.domain.records.OrderSummary(o.orderNumber, o.status)
from OrderEntity o
where o.userName = :userName
""")
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
        select distinct o
        from OrderEntity o left join fetch o.items
        where o.userName = :userName and o.orderNumber = :orderNumber
""")
    Optional<OrderEntity> findOrderByUserNameAndOrderNumber(String userName, String orderNumber);
}
