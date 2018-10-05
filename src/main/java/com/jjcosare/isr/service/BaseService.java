package com.jjcosare.isr.service;

import com.jjcosare.isr.model.BaseModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Created by jjcosare on 10/4/18.
 */
public interface BaseService<R extends ReactiveMongoRepository, M extends BaseModel, ID extends Serializable> {

    R getRepository();

    Flux<M> getAll();

    Mono<M> save(M model);

    Mono<ResponseEntity<M>> getById(ID id);

    Mono<ResponseEntity<M>> update(ID id, M model);

    Mono<ResponseEntity<Void>> delete(ID id);

}
