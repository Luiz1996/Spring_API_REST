package com.example.carros.api.carros;

import com.example.carros.api.infra.Exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroReposiroty carroReposiroty;

    @Autowired
    private CarroData carroData;

    public List<CarroDTO> getCarros(Pageable pageable) {
        return carroReposiroty.findAll(pageable).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO getCarroById(Long id) {
        return carroReposiroty.findById(id).map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("O carro com id " + id + " não existe."));
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        return carroReposiroty.findByTipoContainingIgnoreCase(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Está sendo informado um ID de carro na requisição POST.");

        return CarroDTO.create(carroReposiroty.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Por gentileza, informe um Id do registro a ser atualizado!");

        //obtém o registro a ser atualizado da base
        CarroDTO cDTO = getCarroById(id);

        //valida se veio realmente algum registro pra aquele id
        if (cDTO != null) {

            //instancia e atualiza as informações do registro
            Carro c = Carro.create(cDTO); //convertendo CarroDTO em Carro

            c.setNome(carro.getNome());
            c.setTipo(carro.getTipo());

            //persiste a informação na base novamente
            return CarroDTO.create(carroReposiroty.save(c));
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        //remove a informação da base
        carroReposiroty.deleteById(id);
    }

    public List<CarroDTO> getCarrosCustomByTipoNome(String tipo, String nome) {
        return carroData.findByTipoNomeWithLike(tipo, nome).stream().map(CarroDTO::create).collect(Collectors.toList());
    }
}
