package br.com.api.services.user_service;

import br.com.api.models.UserEntity;
import br.com.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

            return userRepository.save(user_data);

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
    public void deleteUser(Long user_id) {
        try {
            Optional<UserEntity> user_data = userRepository.findById(user_id);

            if (user_data == null){
                throw new IllegalStateException("Este usuario não existe");
            }

            userRepository.delete(user_data.get());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity editUser(UserEntity editedUser, Long user_id) {
        try {
            return userRepository.findById(user_id).map(user -> {
                user.setFullname(editedUser.getFullname());
                user.setEmail(editedUser.getEmail());
                user.setAge(editedUser.getAge());
                user.setPassword(editedUser.getPassword());
                user.setUsername(editedUser.getUsername());
                user.setPhoto(editedUser.getPhoto());
                return userRepository.save(user);
            }).orElseGet(() -> {
                editedUser.setUser_id(user_id);
                return userRepository.save(editedUser);
            });
        } catch (Exception e){
            throw new RuntimeException("Deu Ruim", e);
        }

    }

    @Override
    public void makeFriend(Long yourUser_id, Long friend_id) {

    }

    @Override
    public void makePost(Long user_id) {

    }
}
