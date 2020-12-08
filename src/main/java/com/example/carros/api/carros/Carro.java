package com.example.carros.api.carros;

import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Data
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",  length = 255)
    private String nome;

    @Column(name = "tipo",  length = 255)
    private String tipo;

    private String descricao;
    private String urlFoto;
    private String urlVideo;
    private String latitude;
    private String longitude;

    public static Carro create(CarroDTO c){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c, Carro.class);
    }
}
