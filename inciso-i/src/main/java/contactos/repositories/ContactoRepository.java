package contactos.repositories;

import contactos.entities.Contacto;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends BaseRepository<Contacto, String> {

}
