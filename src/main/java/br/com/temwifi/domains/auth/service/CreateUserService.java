package br.com.temwifi.domains.auth.service;

import br.com.temwifi.domains.auth.entity.interfaces.UserEntity;
import br.com.temwifi.domains.auth.model.User;
import br.com.temwifi.domains.auth.model.request.PostUserRequest;
import br.com.temwifi.interfaces.Service;
import br.com.temwifi.utils.auth.PasswordUtils;

import java.util.UUID;

@br.com.temwifi.annotations.Service
public class CreateUserService implements Service<PostUserRequest, User> {

    private UserEntity userEntity;

    public CreateUserService() {}

    public CreateUserService(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public User execute(PostUserRequest request) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        String salt = PasswordUtils.getSalt();
        user.setPass(PasswordUtils.generateSecurePassword(request.getPass(), salt));
        user.setSalt(salt);

        userEntity.createUser(user);

        return user;
    }

}
