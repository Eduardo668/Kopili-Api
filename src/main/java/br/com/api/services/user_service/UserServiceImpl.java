package br.com.api.services.user_service;

import br.com.api.models.*;
import br.com.api.repository.UserRepository;
import br.com.api.services.chat_service.ChatServiceImpl;
import br.com.api.services.follower_service.FollowerServiceImpl;
import br.com.api.services.post_service.PostServiceImpl;
import br.com.api.services.comment_service.CommentServiceImpl;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final FollowerServiceImpl friendshipService;


    private final ChatServiceImpl chatService;

    public UserServiceImpl(UserRepository userRepository, PostServiceImpl postService,
                           CommentServiceImpl commentService, FollowerServiceImpl friendshipService,
                           ChatServiceImpl chatService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.friendshipService = friendshipService;
        this.chatService = chatService;
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
    public void follow(Long yourUser_id, Long friend_id) {
          try {

              Optional<UserEntity> user1_data = userRepository.findById(yourUser_id);
              Optional<UserEntity> user2_data = userRepository.findById(friend_id);

              if (user1_data.isEmpty()){
                  throw new RuntimeException("Error com user 1");
              } else if (user2_data.isEmpty()) {
                  throw new RuntimeException("Error com user 2");
              }

              FollowerEntity friendshipObject = new FollowerEntity();

              friendshipObject.setUser1(friend_id);

              Set<UserEntity> userArrayForSave = new HashSet<>();

              userArrayForSave.add(user1_data.get());

              friendshipObject.setUserFriend(userArrayForSave);

              friendshipService.createFollowEntity(friendshipObject);

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

    @Override
    public List<UserEntity> findAllFollowedUsers(Long user_id) {
        Optional<UserEntity> user_data = userRepository.findById(user_id);

        List<FollowerEntity> friendship_list = user_data.get().getFriends_list();

        ArrayList<UserEntity> user_friends = new ArrayList<>();

        for (FollowerEntity friendship: friendship_list) {
            Optional<UserEntity> user1 = userRepository.findById(friendship.getUser1());

            user_friends.add(user1.get());
        }

        return user_friends;

    }

	@Override
	public UserEntity makeComment(Long user_id, CommentEntity newComment) {
		try {
           Optional<UserEntity> user_data = userRepository.findById(user_id);

           if(user_data.isEmpty()){
               throw new RuntimeException("Erro");
           }

           Set<CommentEntity> commentEntities = new HashSet<>();

           commentEntities.add(newComment);

           newComment.setUser_comment(user_data.get());

           commentService.createComment(newComment);
           return user_data.get();

       }catch (Exception e){
           throw new RuntimeException("Falhou ao fazer o comentário",e);
       }
	}

    @Override
    public ChatEntity startChat(Long user_id, Long other_person_id) {
        try {
            Optional<UserEntity> user1 = userRepository.findById(user_id);
            Optional<UserEntity> user2 = userRepository.findById(other_person_id);

            if (user1.isEmpty()){
                throw new RuntimeException("Este usuario não existe");
            }
            else if (user2.isEmpty()){
                throw new RuntimeException("Este usuario não existe");
            }

            ChatEntity chat = new ChatEntity();

            chat.setPerson1_username(user1.get().getUsername());
            chat.setPerson2_username(user2.get().getUsername());

           return chatService.createChat(chat);

        }
        catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao criar o chat", e);
        }
    }
}
