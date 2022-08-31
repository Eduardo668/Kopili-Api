package br.com.api.services.user_service;

import br.com.api.models.UserEntity;

import java.util.List;

public interface UserService {

    public UserEntity createUser(UserEntity newUser);

    public List<UserEntity> findAllUsers();

    public void deleteUser(Long user_id);

    public UserEntity editUser(UserEntity editedUser, Long user_id);

    public void makeFriend(Long yourUser_id, Long friend_id);

    public void makePost(Long user_id);


}
