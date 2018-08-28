package com.bolsaideas.springboot.app.models.daoInterface;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface ClienteDaoInterface extends PagingAndSortingRepository<Cliente, Long> {
}
