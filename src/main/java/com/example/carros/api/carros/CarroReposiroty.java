package com.example.carros.api.carros;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroReposiroty extends JpaRepository<Carro, Long> {

    List<Carro> findByTipoContainingIgnoreCase(String tipo);

}
