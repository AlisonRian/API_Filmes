package com.github.alisonrian.api_filmes.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudService<T, ID>{
    public T create(T entity);
    public T update(T entity, ID id);
    public void delete(ID id);
    public Page<T> findAll(Pageable pageable);
    public T findById(ID id);
}
