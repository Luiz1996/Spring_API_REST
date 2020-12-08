package com.example.carros.api.carros;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarroData extends CrudRepository<Carro, Long> {

    @Query(value =  "SELECT \n" +
                    "    c.id, \n" +
                    "    c.nome, \n" +
                    "    c.tipo\n" +
                    "FROM\n" +
                    "    carro c\n" +
                    "WHERE\n" +
                    "    c.tipo like %:tipo% and" +
                    "    c.nome like %:nome%", nativeQuery = true)
    List<Carro> findByTipoNomeWithLike(@Param("tipo") String tipo, @Param("nome") String nome);
}
