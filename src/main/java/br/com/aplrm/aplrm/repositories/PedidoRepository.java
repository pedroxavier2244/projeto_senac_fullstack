package br.com.aplrm.aplrm.repositories;

import br.com.aplrm.aplrm.entities.Pedido;
import br.com.aplrm.aplrm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {


}
