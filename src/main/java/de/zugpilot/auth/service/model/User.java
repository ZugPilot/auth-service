package de.zugpilot.auth.service.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "users")
@Data
public class User {

    @BsonId
    private String id;
    private String name;
    private String password;
    private String role;

}
