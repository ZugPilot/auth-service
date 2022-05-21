package de.zugpilot.auth.service.repository;

import de.zugpilot.auth.service.model.User;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.quarkus.runtime.util.HashUtil;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepositoryBase<User, String> {

    public User create(String name, String password){
        User user = new User();
        user.setId(name.toLowerCase());
        user.setName(name);
        user.setPassword(HashUtil.sha256(password));
        persist(user);
        return user;
    }


}
