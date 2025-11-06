package contactos.controllers;

import contactos.entities.Persona;
import contactos.services.PersonaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persona")
public class PersonaController extends BaseController<Persona, String> {

    public PersonaController(PersonaService service) {
        super(service);
        initController(
                new Persona(),
                "Listado de Personas",
                "Persona"
        );
    }

    @Override
    protected void preAlta() {
        // Se asegura de que siempre se use una nueva instancia vacía
        model.addAttribute("item", new Persona());
    }

}
