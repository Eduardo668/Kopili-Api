package br.com.api.services.user_service;

import br.com.api.models.CommentEntity;
import br.com.api.models.FriendshipEntity;
import br.com.api.models.PostEntity;
import br.com.api.models.UserEntity;
import br.com.api.repository.UserRepository;
import br.com.api.services.friendship_service.FriendshipServiceImpl;
import br.com.api.services.post_service.PostServiceImpl;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;

    private final FriendshipServiceImpl friendshipService;

    public UserServiceImpl(UserRepository userRepository, PostServiceImpl postService, FriendshipServiceImpl friendshipService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.friendshipService = friendshipService;
    }


    @Override
    public UserEntity createUser(UserEntity newUser) {
        try {

            UserEntity user_data  = userRepository.findByUsername(newUser.getUsername());

            if (user_data != null){
                throw new IllegalStateException("Este Username já está sendo usado.");

            }

            return userRepository.save(newUser);

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
    public void deleteUser(Long id) {
        try {
            Optional<UserEntity> user_data = userRepository.findById(id);

            if (user_data == null){
                throw new IllegalStateException("Este Usuário não existe.");
            }

            userRepository.delete(user_data.get());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity editUser(UserEntity editedUser, Long id) {
        try {
            return userRepository.findById(id).map(user -> {
                user.setFullname(editedUser.getFullname());
                user.setEmail(editedUser.getEmail());
                user.setBorn(editedUser.getBorn());
                user.setPassword(editedUser.getPassword());
                user.setUsername(editedUser.getUsername());
                user.setPhoto(editedUser.getPhoto());
                return userRepository.save(user);
            }).orElseGet(() -> {
                editedUser.setId(id);
                return userRepository.save(editedUser);
            });
        } catch (Exception e){
            throw new RuntimeException("Erro", e);
        }

    }

    @Override
    public void makeFriend(Long yourUser_id, Long friend_id) {
          try {
              Optional<UserEntity> user1_data = userRepository.findById(yourUser_id);
              Optional<UserEntity> user2_data = userRepository.findById(friend_id);

              if (user1_data.isEmpty()){
                  throw new RuntimeException("Error com user 1");
              } else if (user2_data.isEmpty()) {
                  throw new RuntimeException("Error com user 2");
              }

              FriendshipEntity friendshipObject = new FriendshipEntity();

              Set<UserEntity> userArrayForSave = new HashSet<>();

              userArrayForSave.add(user1_data.get());
              userArrayForSave.add(user2_data.get());

              friendshipObject.setUserFriend(userArrayForSave);

//              friendshipObject.setUser1(user1_data.get().getId());
//              friendshipObject.setUser2(user2_data.get().getId());
//

              FriendshipEntity created_friendship = friendshipService.createFriendship(friendshipObject);


//              UserEntity user1_entity = editUser(user1_data.get(),user1_data.get().getId());
//              UserEntity user2_entity = editUser(user2_data.get(),user2_data.get().getId());




          }catch (Exception e){
              throw new RuntimeException("Deu ruim ao makar a friend", e);
          }
    }

    @Override
    public UserEntity makePost(Long user_id, PostEntity newPost) {
        try {
            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if(user_data.isEmpty()){
                throw new RuntimeException("Deu ruim");
            }

            Set<PostEntity> postEntities = new HashSet<>();
            postEntities.add(newPost);

            newPost.setUserPost(user_data.get());

            postService.createPost(newPost);
            return user_data.get();
          
        }catch (Exception e){
            throw new RuntimeException("Falhou na ao fazer o post",e);
        }
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user;
    }

//	@Override
//	public UserEntity makeComment(Long user_id, CommentEntity newComment) {
//		try {
//            Optional<UserEntity> user_data = userRepository.findById(user_id);
//            if(user_data.isEmpty()){
//                throw new RuntimeException("Erro");
//            }
//
//            Set<PostEntity> commentEntities = new HashSet<>();
//            commentEntities.add(newComment);
//
//            newComment.setUserComment(user_data.get());
//
//            commentService.createComment(newComment);
//            return user_data.get();
//
//        }catch (Exception e){
//            throw new RuntimeException("Falhou ao fazer o comentário",e);
//        }
//	}
}
