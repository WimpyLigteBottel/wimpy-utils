package com.wimpy.examples.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseExampleCrudDao extends CrudRepository<User,String> {


}
