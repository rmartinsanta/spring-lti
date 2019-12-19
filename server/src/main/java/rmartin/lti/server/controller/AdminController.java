package rmartin.lti.server.controller;

import rmartin.lti.server.controller.dto.ConsumerRequestDTO;
import rmartin.lti.server.controller.dto.ConsumerResponseDTO;
import rmartin.lti.api.model.Consumer;
import rmartin.lti.api.service.SecretService;
import rmartin.lti.server.service.repos.ConsumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private SecretService secretService;

    @GetMapping("/consumers")
    public ResponseEntity<List<ConsumerResponseDTO>> getAll(){
        return ResponseEntity.ok(this.consumerRepository
                .findAll()
                .stream()
                .filter(c -> !c.isAdmin())
                .map(ConsumerResponseDTO::new)
                .collect(Collectors.toList()));
    }

    @GetMapping("/consumers/{id}")
    public ResponseEntity<ConsumerResponseDTO> get(@PathVariable long id){

        var customer = this.consumerRepository.findById(id);
        return customer.map(consumer -> ResponseEntity.ok(new ConsumerResponseDTO(consumer))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/consumers")
    public ResponseEntity<String> register(@RequestBody ConsumerRequestDTO dto){
        if(!dto.isValid()){
            throw new IllegalArgumentException("Invalid request, make sure you are sending a valid username, email and password");
        }
        String secret = secretService.generateSecret();
        Consumer c = consumerRepository.save(new Consumer(dto.getUsername(), dto.getPassword(), secret));
        return ResponseEntity
                .created(URI.create("/admin/consumers/"+Long.toString(c.getId())))
                .body(secret);
    }

    @DeleteMapping(path = "/consumers/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){

        if(!consumerRepository.existsById(id))
            return ResponseEntity.notFound().build();
        consumerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
