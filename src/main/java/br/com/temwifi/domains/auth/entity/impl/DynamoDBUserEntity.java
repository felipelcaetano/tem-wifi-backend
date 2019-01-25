package br.com.temwifi.domains.auth.entity.impl;

import br.com.temwifi.annotations.Entity;
import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.model.User;
import br.com.temwifi.domains.infra.entity.DynamoDBAbstractEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Optional;

@Entity(tableName = "TemWiFiUser", indexes = {"email-index"})
public class DynamoDBUserEntity extends DynamoDBAbstractEntity implements UserEntity{

    private String emailIndex = "email-index";

    @Override
    public void createUser(User user) {
        super.save(user);
    }

    @Override
    public Optional<User> readUserById(String id) {
        return super.load(id, User.class);
    }

    @Override
    public Optional<User> readUserByEmail(String email) {

        User user = new User();
        user.setEmail(email);

        DynamoDBQueryExpression<User> expression = new DynamoDBQueryExpression<User>()
                .withIndexName(emailIndex)
                .withHashKeyValues(user)
                .withConsistentRead(false);

        List<User> users = mapper.query(User.class, expression);

        if(users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }
}
