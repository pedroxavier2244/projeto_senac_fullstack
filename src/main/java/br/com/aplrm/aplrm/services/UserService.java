package br.com.aplrm.aplrm.services;

import br.com.aplrm.aplrm.dto.*;

import br.com.aplrm.aplrm.entities.Endereco;
import br.com.aplrm.aplrm.entities.Perfil;
import br.com.aplrm.aplrm.entities.User;
import br.com.aplrm.aplrm.repositories.EnderecoRepository;
import br.com.aplrm.aplrm.repositories.PerfilRepository;
import br.com.aplrm.aplrm.repositories.UserRepository;
import br.com.aplrm.aplrm.services.exceptions.ResourceNotFoundExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.UUID;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserCadastroDTO Cadastro(@Valid UserCadastroDTO userCadastroDTO) {

        if (userCadastroDTO.getSenha() == null || userCadastroDTO.getSenha().length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        String encodedPassword = passwordEncoder.encode(userCadastroDTO.getSenha());

        User user = new User(userCadastroDTO);
        user.setSenha(encodedPassword);

        Endereco endereco = enderecoRepository.save(userCadastroDTO.getEndereco());
        user.setEndereco(endereco);

        Perfil userPerfil = perfilRepository.findByAuthority("CLIENT")
                .orElseThrow(() -> new RuntimeException("Perfil não cadastrado"));
        user.getAuthorities().add(userPerfil);

        userRepository.save(user);

        return new UserCadastroDTO(user);
    }
    @Transactional
    public void delete(UUID id) {
        try {
            User user = userRepository.getReferenceById(id);
            userRepository.delete(user);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Usuário não encontrado para Exclusão");
        }
    }
    public UserDTO findById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
        return new UserDTO(user);
    }
    @Transactional(readOnly = true)
    public Page<UserCadastroDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserCadastroDTO::new);
    }
    @Transactional
    public UserDTO insert(UserDTO dto) {
        User user = new User(dto);
        user.setEndereco(enderecoRepository.save(dto.getEndereco()));
        user = userRepository.save(user);

        return new UserDTO(user);
    }
    @Transactional
    public UserDTO update(UUID id, UserDTO dto) {

        try {
            User user = userRepository.getReferenceById(id);
            user.setNome(dto.getNome());
            user.setEmail(dto.getEmail());
            user.setTelefone(dto.getTelefone());
            user.setDataNascimento(dto.getDataNascimento());

            Endereco endereco = user.getEndereco();
            endereco.setLogradouro(dto.getEndereco().getLogradouro());
            endereco.setBairro(dto.getEndereco().getBairro());
            endereco.setCep(dto.getEndereco().getCep());
            endereco.setCidade(dto.getEndereco().getCidade());
            endereco.setUf(dto.getEndereco().getUf());
            endereco.setNumero(dto.getEndereco().getNumero());

            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Usuário não encontrado para atualização");
        }
    }
    @Transactional
    public void alterarSenha(UUID id, UpdateSenha dto) {
        logger.info("Atualizando senha para o usuário com ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Usuário não encontrado"));
        if ((passwordEncoder.matches(dto.getSenhaAntiga(), user.getSenha()))) {
            user.setSenha(passwordEncoder.encode(dto.getSenhaNova()));
            userRepository.save(user);
            logger.info("Senha atualizada com sucesso para o usuário com ID: {}", id);
        } else {
            throw new ResourceNotFoundExceptions("Erro ao atualizar a senha do usuário: senha antiga incorreta");
        }
    }
    @Transactional
    public UserDTO alterarEmail(UUID id, @Valid UserDTO dto) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setEmail(dto.getEmail());
            userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Usuário não encontrado para atualização");
        }
    }
    @Transactional
    public EnderecoDTO alterarEndereco(UUID id, @Valid EnderecoDTO dto) {
        try {
            User user = userRepository.getReferenceById(id);
            Endereco endereco = user.getEndereco();
                endereco.setLogradouro(dto.getLogradouro());
                endereco.setCep(dto.getCep());
                endereco.setNumero(dto.getNumero());
                endereco.setCidade(dto.getCidade());
                endereco.setBairro(dto.getBairro());
                endereco.setUf(dto.getUf());
                endereco.setComplemento(dto.getComplemento());
            userRepository.save(user);

            return new EnderecoDTO(endereco);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Usuário não encontrado para atualização");
        }
    }
    @Transactional
    public UserDTO alterarDataNascimento(UUID id, @Valid UserDTO dto) {
        try{User user=userRepository.getReferenceById(id);
            user.setDataNascimento(dto.getDataNascimento());
            userRepository.save(user);
            return new UserDTO(user);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Usuário não encontrado para atualização");
        }
    }
    protected User authenticated(){
        try {
            String username= SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        }catch (Exception e){
            throw new UsernameNotFoundException("Usuario Invalido");
        }
    }
    @Transactional(readOnly = true)
    public UserPerfilDTO getMe() {
        User user=authenticated();
        return new UserPerfilDTO(user);
    }
}
