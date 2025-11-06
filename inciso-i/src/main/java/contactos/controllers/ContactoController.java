package contactos.controllers;

import contactos.entities.ContactoCorreoElectronico;
import contactos.entities.ContactoTelefonico;
import contactos.entities.enums.TipoTelefono;
import contactos.services.ContactoCorreoElectronicoService;
import contactos.services.ContactoTelefonicoService;
import contactos.services.ErrorServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoCorreoElectronicoService correoService;
    private final ContactoTelefonicoService telefonoService;

    public ContactoController(ContactoCorreoElectronicoService correoService, ContactoTelefonicoService telefonoService) {
        this.correoService = correoService;
        this.telefonoService = telefonoService;
    }

    @GetMapping("/listar")
    public String listarContactos(
            @RequestParam(value = "persona", required = false) String idPersona,
            Model model) {
        try {
            List<ContactoCorreoElectronico> correos = correoService.listarActivos();
            List<ContactoTelefonico> telefonos = telefonoService.listarActivos();

            if (idPersona != null && !idPersona.isBlank()) {
                correos = correos.stream()
                        .filter(c -> c.getPersona() != null && idPersona.equals(c.getPersona().getId()))
                        .collect(Collectors.toList());

                telefonos = telefonos.stream()
                        .filter(t -> t.getPersona() != null && idPersona.equals(t.getPersona().getId()))
                        .collect(Collectors.toList());

                model.addAttribute("personaFiltrada", idPersona);
            }

            model.addAttribute("correos", correos);
            model.addAttribute("telefonos", telefonos);
            model.addAttribute("CELULAR", TipoTelefono.CELULAR);

            return "view/lContacto";
        } catch (ErrorServiceException e) {
            model.addAttribute("msgError", e.getMessage());
            return "view/lContacto";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msgError", "Error al listar contactos.");
            return "view/lContacto";
        }
    }
}
