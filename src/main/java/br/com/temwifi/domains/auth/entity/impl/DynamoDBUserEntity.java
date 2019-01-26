package br.com.temwifi.domains.auth.entity.impl;

import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.model.dto.UserDTO;
import br.com.temwifi.domains.infra.entity.DynamoDBAbstractEntity;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.List;
import java.util.Optional;

public class DynamoDBUserEntity extends DynamoDBAbstractEntity implements UserEntity {

    private String emailIndex = "email-index";

    @Override
    public void createUser(UserDTO user) {
        super.save(user);
    }

    @Override
    public Optional<UserDTO> readUserById(String id) {
        return super.load(id, UserDTO.class);
    }

    @Override
    public Optional<UserDTO> readUserByEmail(String email) {

        UserDTO user = new UserDTO();
        user.setEmail(email);

        DynamoDBQueryExpression<UserDTO> expression = new DynamoDBQueryExpression<UserDTO>()
                .withIndexName(emailIndex)
                .withHashKeyValues(user)
                .withConsistentRead(false);

        List<UserDTO> users = mapper.query(UserDTO.class, expression);

        if(users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }
}
