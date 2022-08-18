package br.com.api.services.user_service;

import br.com.api.models.UserEntity;
import br.com.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity newUser) {
        try {

            UserEntity user_data  = userRepository.findByUsername(newUser.getUsername());

            if (user_data != null){
                throw new IllegalStateException("Este username já existe");
            }

            userRepository.save(user_data);

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UserEntity deletedUser) {
        try {
            UserEntity user_data = userRepository.findByUsername(deletedUser.getUsername());

            if (user_data == null){
                throw new IllegalStateException("Este usuario não existe");
            }

            userRepository.delete(deletedUser);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity editUser(UserEntity editedUser, Long user_id) {
        try{
            Optional<UserEntity> user_opt = userRepository.findById(user_id);
            if (user_opt.)


        }
    }

    @Override
    public void makeFriend(Long yourUser_id, Long friend_id) {

    }

    @Override
    public void makePost(Long user_id) {

    }
}
