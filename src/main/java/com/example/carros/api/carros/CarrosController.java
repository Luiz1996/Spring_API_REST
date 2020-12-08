package com.example.carros.api.carros;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size) {

        //consulta com paginação
        return ResponseEntity.ok(carroService.getCarros(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
           return ResponseEntity.ok(carroService.getCarroById(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity get(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = carroService.getCarroByTipo(tipo);

        return carros.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity post(@RequestBody Carro carro) {
        CarroDTO cDTO = carroService.insert(carro);
        URI location = getUri(cDTO.getId());
        //O location receberá algo parecido com => http://localhost:1234/api/v1/carros/44 , onde o 44 é o cDTO.getId()

        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
        CarroDTO cDTO = carroService.update(carro, id);

        return cDTO != null ?
                ResponseEntity.ok(cDTO) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tipo/{tipo}/nome/{nome}")
    public ResponseEntity getCustom(@PathVariable("tipo") String tipo, @PathVariable("nome") String nome) {
        List<CarroDTO> carro = carroService.getCarrosCustomByTipoNome(tipo, nome);

        return ResponseEntity.ok(carro);
    }
}
