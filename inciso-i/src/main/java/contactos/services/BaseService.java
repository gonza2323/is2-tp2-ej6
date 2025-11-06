package contactos.services;

import contactos.entities.BaseEntity;
import contactos.repositories.BaseRepository;

import java.util.List;
import java.util.Optional;
public abstract class BaseService<T extends BaseEntity<ID>, ID> {

    protected final BaseRepository<T, ID> repo;

    protected BaseService(BaseRepository<T, ID> repo) {
        this.repo = repo;
    }

    protected abstract T createEmpty();

    public T alta(T elemento) throws ErrorServiceException {
        try {
            validar(elemento);
            antesDeAlta(elemento);
            elemento.setEliminado(false);
            T guardado = repo.save(elemento);
            despuesDeAlta(guardado);
            return guardado;
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException("Error al guardar la entidad: " + e.getMessage());
        }
    }

    public Optional<T> modificar(ID id, T actualizado) throws ErrorServiceException {
        try {
            validar(actualizado);
            antesDeModificacion(actualizado);
            return repo.findById(id).map(entidadExistente -> {
                actualizado.setId(id);
                T entidadActualizada = repo.save(actualizado);
                return entidadActualizada;
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public boolean bajaLogica(ID id) throws ErrorServiceException {
        try {
            antesDeBaja(id);
            return repo.findById(id).map(entidad -> {
                entidad.setEliminado(true);
                repo.save(entidad);
                return true;
            }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public Optional<T> obtener(ID id) throws ErrorServiceException {
        try {
            return repo.findById(id)
                    .filter(ent -> !Boolean.TRUE.equals(ent.getEliminado()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public List<T> listarActivos() throws ErrorServiceException {
        try {
            return repo.findAll().stream()
                    .filter(ent -> !Boolean.TRUE.equals(ent.getEliminado()))
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServiceException(e.getMessage());
        }
    }

    public List<T> listarTodo() {
        return repo.findAll();
    }

    protected void validar(T elemento) throws ErrorServiceException {}
    protected void antesDeAlta(T elemento) throws ErrorServiceException {}
    protected void despuesDeAlta(T elemento) throws ErrorServiceException {}
    protected void antesDeModificacion(T elemento) throws ErrorServiceException {}
    protected void antesDeBaja(ID id) throws ErrorServiceException {}
}
