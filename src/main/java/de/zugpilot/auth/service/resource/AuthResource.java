package de.zugpilot.auth.service.resource;

import de.zugpilot.auth.service.model.User;
import de.zugpilot.auth.service.model.UserDto;
import de.zugpilot.auth.service.repository.UserRepository;
import de.zugpilot.auth.service.resource.request.AuthLoginBasicRequest;
import de.zugpilot.auth.service.resource.request.AuthLoginTokenRequest;
import de.zugpilot.auth.service.resource.request.AuthRegisterRequest;
import de.zugpilot.auth.service.resource.response.AuthLoginResponse;
import de.zugpilot.auth.service.service.AuthService;
import de.zugpilot.service.core.exception.ServiceException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Optional;

@Path("/auth")
public class AuthResource {

    @Inject
    AuthService service;

    @Inject
    UserRepository repository;

    @POST
    @Path("/register")
    public AuthLoginResponse register(AuthRegisterRequest request) throws ServiceException{
        Optional<User> optional = repository.findByIdOptional(request.getId().toLowerCase());
        if(optional.isPresent()){
            throw new ServiceException(400, "User already exists");
        }
        User user = repository.create(request.getId(), request.getPassword());
        return new AuthLoginResponse(new UserDto(user), service.generateToken(user.getId(), user.getRole()));
    }

    @POST
    @Path("/login/basic")
    public AuthLoginResponse loginBasic(@Valid AuthLoginBasicRequest request) throws ServiceException {
        return service.validateBasicRequest(request);
    }

    @POST
    @Path("/login/token")
    public AuthLoginResponse loginToken(@Valid AuthLoginTokenRequest request) throws ServiceException {
        return service.validateTokenRequest(request);
    }

}
