package contactos.repositories;

import contactos.entities.Usuario;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, String> {

    Optional<Usuario> findByCuenta(String cuenta);

    boolean existsByCuenta(String cuenta);
}
