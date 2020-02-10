package rmartin.lti.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rmartin.lti.api.model.RegisterActivityRequest;
import rmartin.lti.server.service.ActivityProviderService;

@RestController
@RequestMapping("/activity")
public class ActivitiesController {

    @Autowired
    ActivityProviderService activityProviderService;

    /**
     * Register a new activity in the LTI Proxy
     * @param dto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<?> registerActivityProvider(RegisterActivityRequest dto){

        // TODO Registro activity Providers, tienen que poder actualizar sus datos de actividades.
        // TODO Modificar ActivityProvider, ponerle ruta etc. Metodo update() del controlador
        if(!dto.isValid()){
            return ResponseEntity.badRequest().build();
        }

        activityProviderService.updateActivityProvider(dto);
        return ResponseEntity.ok().build();
    }

}
