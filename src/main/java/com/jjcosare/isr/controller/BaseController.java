package com.jjcosare.isr.controller;

import com.jjcosare.isr.model.BaseModel;
import com.jjcosare.isr.service.BaseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by jjcosare on 10/3/18.
 */
public abstract class BaseController<S extends BaseService, M extends BaseModel, ID extends Serializable> {

    private S service;

    public BaseController(S service){
        this.service = service;
    }

    public S getService(){
        return this.service;
    }

    @GetMapping("")
    public Flux<M> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Mono<M> create(@Valid @RequestBody M model) {
        return service.save(model);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<M>> getById(@PathVariable ID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<M>> update(@PathVariable ID id,
                                          @Valid @RequestBody M model) {
        return service.update(id, model);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable ID id) {
        return service.delete(id);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<M> streamAll() {
        return service.getAll();
    }

}
