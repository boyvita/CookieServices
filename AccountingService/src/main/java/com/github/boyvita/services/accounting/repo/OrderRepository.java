package com.github.boyvita.services.accounting.repo;

import com.github.boyvita.services.accounting.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
