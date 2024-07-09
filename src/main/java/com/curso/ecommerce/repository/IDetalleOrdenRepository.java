package com.curso.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> {
	 List<DetalleOrden> findByOrdenId(int id);
	
}
