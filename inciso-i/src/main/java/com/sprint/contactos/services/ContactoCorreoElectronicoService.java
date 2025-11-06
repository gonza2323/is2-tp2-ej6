package com.sprint.contactos.services;

import com.sprint.contactos.entities.ContactoCorreoElectronico;
import com.sprint.contactos.repositories.ContactoCorreoElectronicoRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ContactoCorreoElectronicoService extends BaseService<ContactoCorreoElectronico, String> {

    private final ContactoCorreoElectronicoRepository repoCorreo;

    public ContactoCorreoElectronicoService(ContactoCorreoElectronicoRepository repoCorreo) {
        super(repoCorreo);
        this.repoCorreo = repoCorreo;
    }

    @Override
    protected ContactoCorreoElectronico createEmpty() {
        ContactoCorreoElectronico correo = new ContactoCorreoElectronico();
        correo.setEliminado(false);
        return correo;
    }

    @Override
    protected void validar(ContactoCorreoElectronico entidad) throws ErrorServiceException {
        if (entidad.getEmail() == null || entidad.getEmail().trim().isEmpty()) {
            throw new ErrorServiceException("El campo de correo electrónico no puede estar vacío.");
        }

        // Validación de formato de correo electrónico
        String patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(patronEmail, entidad.getEmail())) {
            throw new ErrorServiceException("Formato de correo electrónico inválido.");
        }

        // Evitar duplicados (ignorando mayúsculas/minúsculas)
        var existente = repoCorreo.findByEmailIgnoreCase(entidad.getEmail());
        if (existente.isPresent()) {
            var encontrado = existente.get();
            if (entidad.getId() == null || !encontrado.getId().equals(entidad.getId())) {
                throw new ErrorServiceException("Ya existe un contacto con este correo electrónico registrado.");
            }
        }
    }
}

