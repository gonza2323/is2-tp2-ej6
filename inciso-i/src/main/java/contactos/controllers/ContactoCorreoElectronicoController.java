package contactos.controllers;

import contactos.entities.ContactoCorreoElectronico;
import contactos.services.ContactoCorreoElectronicoService;
import contactos.services.EmpresaService;
import contactos.services.ErrorServiceException;
import contactos.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactoCorreo")
public class ContactoCorreoElectronicoController extends BaseController<ContactoCorreoElectronico, String> {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PersonaService personaService;

    public ContactoCorreoElectronicoController(ContactoCorreoElectronicoService service) {
        super(service);
        initController(
                new ContactoCorreoElectronico(),
                "Listado de Contactos por Correo",
                "ContactoCorreo"
        );
        this.nameEntityLower = "contactoCorreo";
        this.redirectList = "redirect:/contacto/listar";
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        model.addAttribute("item", new ContactoCorreoElectronico());
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        model.addAttribute("empresas", empresaService.listarActivos());
        model.addAttribute("personas", personaService.listarActivos());
    }
}
