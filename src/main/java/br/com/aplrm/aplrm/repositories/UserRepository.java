package br.com.aplrm.aplrm.repositories;

import br.com.aplrm.aplrm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String username);
}
