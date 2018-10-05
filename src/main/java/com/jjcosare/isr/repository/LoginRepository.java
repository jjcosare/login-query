package com.jjcosare.isr.repository;

import com.jjcosare.isr.model.Login;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jjcosare on 10/3/18.
 */
@Repository
public interface LoginRepository extends ReactiveMongoRepository<Login, String>, LoginRepositoryCustom {

}
