package br.com.aplrm.aplrm.controllers;

import br.com.aplrm.aplrm.dto.EnderecoDTO;
import br.com.aplrm.aplrm.dto.UpdateSenha;
import br.com.aplrm.aplrm.dto.UserCadastroDTO;
import br.com.aplrm.aplrm.dto.UserDTO;
import br.com.aplrm.aplrm.entities.Endereco;
import br.com.aplrm.aplrm.services.UserService;
import br.com.aplrm.aplrm.services.exceptions.ResourceNotFoundExceptions;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {

    @Autowired
    private UserService service;



    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
       service.delete(id);
    }

    @GetMapping
    public ResponseEntity<Page<UserCadastroDTO>> findAll(Pageable pageable) {
        Page<UserCadastroDTO> dto = service.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UserCadastroDTO> inserirCadastro(@Valid @RequestBody UserCadastroDTO userCadastroDTO) {
        UserCadastroDTO user = service.Cadastro(userCadastroDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO user = service.update(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<String> updateSenha(@PathVariable UUID id, @RequestBody @Valid UpdateSenha dto) {
        try {
            service.alterarSenha(id, dto);
            return ResponseEntity.ok("Senha Atualizada com sucesso");
        } catch (ResourceNotFoundExceptions e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a senha");
        }
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<String> updateEmail(@PathVariable UUID id, @Valid @RequestBody UserDTO dto){
        UserDTO user=service.alterarEmail(id,dto);
        return ResponseEntity.ok("Seu email foi Atualizado com sucesso Seu novo Email é  "+ user.getEmail());
    }
   @PatchMapping("/{id}/dataNascimento")
    public ResponseEntity<LocalDate> alterarDataNascimento(@PathVariable UUID id, @Valid @RequestBody UserDTO dto){
        UserDTO user= service.alterarDataNascimento(id,dto);
        return ResponseEntity.ok().body(dto.getDataNascimento());
   }
    @PostMapping("/{id}/endereco")
    public ResponseEntity<EnderecoDTO> alterarEndereco(@PathVariable UUID id, @Valid @RequestBody EnderecoDTO dto){
        System.out.println("dentro do controller");
        EnderecoDTO user = service.alterarEndereco(id,dto);
        return ResponseEntity.ok().body(user);
    }
    @DeleteMapping("/{id}/deletar")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }


}


