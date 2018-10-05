package com.jjcosare.isr.service;

import com.jjcosare.isr.model.BaseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Created by jjcosare on 10/3/18.
 */
public abstract class BaseServiceImpl <R extends ReactiveMongoRepository, M extends BaseModel, ID extends Serializable> implements BaseService<R, M, ID> {

    private R repository;

    public BaseServiceImpl(R repository){
        this.repository = repository;
    }

    @Override
    public R getRepository(){
        return this.repository;
    }

    @Override
    public Flux<M> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<M> save(M model) {
        return repository.save(model);
    }

    @Override
    public Mono<ResponseEntity<M>> getById(ID id) {
        return repository.findById(id)
                .map(dbModel -> ResponseEntity.ok(dbModel))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<M>> update(ID id, M model) {
        return repository.findById(id)
                .flatMap(dbModel -> {
                    BeanUtils.copyProperties(model, dbModel, "id", "createdAt");
                    return repository.save(dbModel);
                })
                .map(updatedModel -> new ResponseEntity<>(updatedModel, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(ID id) {
        return repository.findById(id)
                .flatMap(dbModel ->
                        repository.delete(dbModel)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
