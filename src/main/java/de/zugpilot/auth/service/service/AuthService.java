package de.zugpilot.auth.service.service;

import de.zugpilot.auth.service.model.User;
import de.zugpilot.auth.service.model.UserDto;
import de.zugpilot.auth.service.repository.UserRepository;
import de.zugpilot.auth.service.resource.request.AuthLoginBasicRequest;
import de.zugpilot.auth.service.resource.request.AuthLoginTokenRequest;
import de.zugpilot.auth.service.resource.response.AuthLoginResponse;
import de.zugpilot.service.core.auth.AuthResult;
import de.zugpilot.service.core.auth.quarkus.QuarkusAuthBootstrap;
import de.zugpilot.service.core.exception.ServiceException;
import io.quarkus.runtime.util.HashUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository repository;

    @Inject
    QuarkusAuthBootstrap bootstrap;

    public AuthLoginResponse validateBasicRequest(AuthLoginBasicRequest request) throws ServiceException {
        Optional<User> optional = repository.findByIdOptional(request.getId().toLowerCase());
        if(optional.isEmpty()){
            throw new ServiceException(401, "Bad credentials");
        }
        User user = optional.get();
        if(!HashUtil.sha256(request.getPassword()).equals(user.getPassword())){
            throw new ServiceException(401, "Bad credentials");
        }
        return new AuthLoginResponse(new UserDto(user), generateToken(user.getId(), user.getRole()));
    }

    public AuthLoginResponse validateTokenRequest(AuthLoginTokenRequest request) throws ServiceException {
        AuthResult result = bootstrap.getProvider().auth(request.getToken());
        if(!result.isSuccess()){
            throw new ServiceException(401, "Bad credentials");
        }
        Optional<User> optional = repository.findByIdOptional(result.getSubject());
        if(optional.isEmpty()){
            throw new ServiceException(401, "Bad credentials");
        }
        User user = optional.get();
        return new AuthLoginResponse(new UserDto(user), request.getToken());
    }

    public String generateToken(String id, String role){
        return bootstrap.getProvider().generateToken(id, 60 * 60 * 24, role);
    }

}
