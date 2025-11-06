package contactos.services;

import contactos.entities.ContactoCorreoElectronico;
import contactos.entities.ContactoTelefonico;
import contactos.entities.enums.TipoContacto;
import contactos.entities.enums.TipoTelefono;
import contactos.entities.Empresa;
import contactos.entities.Persona;
import org.springframework.stereotype.Service;

@Service
public class AgendaFacade {

    private final PersonaService personaSrv;
    private final EmpresaService empresaSrv;
    private final ContactoCorreoElectronicoService correoSrv;
    private final ContactoTelefonicoService telefonoSrv;

    public AgendaFacade(PersonaService personaSrv,
                        EmpresaService empresaSrv,
                        ContactoCorreoElectronicoService correoSrv,
                        ContactoTelefonicoService telefonoSrv) {
        this.personaSrv = personaSrv;
        this.empresaSrv = empresaSrv;
        this.correoSrv = correoSrv;
        this.telefonoSrv = telefonoSrv;
    }

    // Registra una persona junto con su correo y teléfono asociados
    public void registrarPersonaConContactos(String nombre,
                                             String apellido,
                                             Empresa empresa,
                                             String correo,
                                             String numTelefono,
                                             String tipoTel,
                                             String tipoCont) throws ErrorServiceException {

        // Crear persona
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setApellido(apellido);
        personaSrv.alta(nuevaPersona);

        // Crear contacto de correo electrónico
        ContactoCorreoElectronico nuevoCorreo = new ContactoCorreoElectronico();
        nuevoCorreo.setEmail(correo);
        nuevoCorreo.setPersona(nuevaPersona);
        nuevoCorreo.setEmpresa(empresa);
        nuevoCorreo.setTipoContacto(TipoContacto.valueOf(tipoCont));
        correoSrv.alta(nuevoCorreo);

        // Crear contacto telefónico
        ContactoTelefonico nuevoTelefono = new ContactoTelefonico();
        nuevoTelefono.setTelefono(numTelefono);
        nuevoTelefono.setTipoTelefono(TipoTelefono.valueOf(tipoTel));
        nuevoTelefono.setTipoContacto(TipoContacto.valueOf(tipoCont));
        nuevoTelefono.setPersona(nuevaPersona);
        nuevoTelefono.setEmpresa(empresa);
        telefonoSrv.alta(nuevoTelefono);
    }
}

