package br.com.api.services.user_service;

import br.com.api.models.ChatEntity;
import br.com.api.models.CommentEntity;
import br.com.api.models.PostEntity;
import br.com.api.models.UserEntity;

import java.util.List;

public interface UserService {

    public UserEntity createUser(UserEntity newUser);

    public List<UserEntity> findAllUsers();

    public void deleteUser(Long user_id);

    public UserEntity editUser(UserEntity editedUser, Long user_id);

    public void follow(Long yourUser_id, Long friend_id);

    public UserEntity makePost(Long user_id, PostEntity newPost);

    public UserEntity findUserByUsername(String username);

    public List<UserEntity> findAllFollowedUsers(Long user_id);

   public PostEntity makeComment(Long user_id, Long post_id , CommentEntity newComment);

   public ChatEntity startChat(Long user_id, Long other_person_id);

   public ChatEntity sendMessage(String message, Long chat_id);
}
