package com.wimpy.examples.db.hilo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCrudDao extends CrudRepository<Item,Long> {
}
