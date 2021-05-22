package org.paysalary.mapper;

public interface ResultMapper<T,R> {
    R map(T entity);
}
