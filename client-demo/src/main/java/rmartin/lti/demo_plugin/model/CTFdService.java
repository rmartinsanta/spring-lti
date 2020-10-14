package rmartin.lti.demo_plugin.model;

import org.springframework.stereotype.Service;
import rmartin.lti.api.service.SecretService;

@Service
public class CTFdService {

    private final CTFdUserRepository repository;
    private final SecretService secretService;
    private final CTFdAPI api;

    public CTFdService(CTFdUserRepository repository, SecretService secretService, CTFdAPI api) {
        this.repository = repository;
        this.secretService = secretService;
        this.api = api;
    }

    /**
     * Si el usuario existe devuelve el usuario, si no lo registra y devuelve el usuario
     * @param email
     * @return
     */
    public CTFdUser searchOrRegister(String email, String name){
        var potentialUser = this.repository.findByEmail(email);
        if(potentialUser.isPresent()){
            return potentialUser.get();
        }

        // Usuario no registrado
        String password = secretService.generateSecret(16);
        return registerUserOrFail(email, name, password);
    }

    public CTFdUser registerUserOrFail(String email, String name, String password){
        if (api.registerUser(email, name, password)) {
            var newUser = new CTFdUser(email, name, password);
            newUser = repository.save(newUser);
            return newUser;
        } else {
            throw new RuntimeException("Failed to create user in CTFd, investigate cause in logs");
        }
    }
}
