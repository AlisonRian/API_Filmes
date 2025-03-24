package com.github.alisonrian.api_filmes.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public abstract class GenericCrud<T, ID, REPO extends JpaRepository<T,ID>> implements ICrudService<T, ID>{
    private final REPO repository;
    public GenericCrud(REPO repository) {
        this.repository = repository;
    }
    @Override
    public T create(T entity){
        return repository.save(entity);
    }
    @Override
    public void delete(ID id){
        repository.deleteById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
    @Override
    public T update(T entity, ID id) {
        Optional<T> exists = repository.findById(id);
        if (exists.isPresent()) {
            return repository.saveAndFlush(entity);
        } else {
            throw new EntityNotFoundException("Entity not found");
        }
    }
//    @Override
//    public T update(T entity, ID id){
//        return repository.saveAndFlush(entity);
//    }
    @Override
    public T findById(ID id){
        Optional<T> entity = repository.findById(id);
        if(entity.isPresent()){
            return entity.get();
        }
        throw new EntityNotFoundException("Entity not found");
    }
}
