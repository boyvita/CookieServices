package com.github.boyvita.services.catalog.repo;

import com.github.boyvita.services.catalog.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
