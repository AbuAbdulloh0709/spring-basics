package com.epam.esm.dto.mapper;

public interface Mapper<E, D> {
    E mapToEntity(D dto);

    D mapToDto(E entity);
}
