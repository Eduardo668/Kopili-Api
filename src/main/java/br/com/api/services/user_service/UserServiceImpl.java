package br.com.api.services.user_service;

import br.com.api.models.*;
import br.com.api.repository.UserRepository;
import br.com.api.services.chat_service.ChatServiceImpl;
import br.com.api.services.follower_service.FollowerServiceImpl;
import br.com.api.services.message_service.MessageServiceImpl;
import br.com.api.services.post_service.PostServiceImpl;
import br.com.api.services.comment_service.CommentServiceImpl;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final FollowerServiceImpl friendshipService;
    private final MessageServiceImpl messageService;
    private final ChatServiceImpl chatService;

    public UserServiceImpl(UserRepository userRepository, PostServiceImpl postService,
                           CommentServiceImpl commentService, FollowerServiceImpl friendshipService,
                           ChatServiceImpl chatService, MessageServiceImpl messageService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.friendshipService = friendshipService;
        this.chatService = chatService;
        this.messageService = messageService;
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

        List<FollowerEntity> followers_list = user_data.get().getFollowed_list();

        ArrayList<UserEntity> user_friends = new ArrayList<>();


        for (FollowerEntity friendship: followers_list) {
            Optional<UserEntity> user1 = userRepository.findById(friendship.getUser1());

            user_friends.add(user1.get());
        }

        return user_friends;

    }

	@Override
	public PostEntity makeComment(Long user_id,Long post_id, CommentEntity newComment) {
		try {
           Optional<UserEntity> user_data = userRepository.findById(user_id);
           PostEntity post_data = postService.findPostById(post_id);

           if(user_data.isEmpty()){
               throw new RuntimeException("Erro");
           }

           Set<CommentEntity> commentEntities = new HashSet<>();
           Set<PostEntity> postHashForSave = new HashSet<>();

           commentEntities.add(newComment);
           postHashForSave.add(post_data);

           newComment.setUser_comment(user_data.get());
           newComment.setUser_commented(user_data.get().getUsername());
           newComment.setPostComments(postHashForSave);

           commentService.createComment(newComment);

           return post_data;

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
            if (user2.isEmpty()){
                throw new RuntimeException("Este usuario não existe");
            }

            ChatEntity chat = new ChatEntity();
            
            List<UserEntity> userChatListForSave = new ArrayList<>();
         
            chat.setPerson1_username(user1.get().getUsername());
            chat.setPerson2_username(user2.get().getUsername());
            
            userChatListForSave.add(user1.get());
            userChatListForSave.add(user2.get());
          
            chat.setUserChat(userChatListForSave);
            
            return chatService.createChat(chat);

        }
        catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao criar o chat", e);
        }
    }

    @Override
    public ChatEntity sendMessage(String message, Long chat_id) {
        try {
            ChatEntity current_chat = chatService.findChatById(chat_id);

//            Set<MessageEntity> messageArray = new HashSet<>();
            MessageEntity messageEntity = new MessageEntity();

            messageEntity.setContent(message);
            messageEntity.setMessage_chat(current_chat);

            messageService.createMessage(messageEntity);

//            messageArray.add(messageEntity);
//
//            current_chat.setMessages(messageArray);
//
            return current_chat;
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao enviar uma mensagem");
        }
    }
}
