package br.com.kopili.services.user_service;

import br.com.kopili.models.ChatEntity;
import br.com.kopili.models.CommentEntity;
import br.com.kopili.models.PostEntity;
import br.com.kopili.models.UserEntity;

import java.util.List;

import org.springframework.core.io.FileSystemResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public UserEntity createUser(UserEntity newUser);

    public UserEntity createUserAdmin(UserEntity newUserAdmin);

    public List<UserEntity> findAllUsers();

    public UserEntity findUserByPostId(Long post_id);


    public void deleteUser(Long user_id);

    public UserEntity editUser(UserEntity editedUser, Long user_id);

    public UserEntity findUserById(Long user_id);

    public void follow(String user_1, String user_2);

    public UserEntity makePost(String username, PostEntity newPost);

    public UserEntity findUserByUsername(String username);

    public List<UserEntity> findUsersByUsername(String username);

    public List<UserEntity> findAllFollowedUsers(Long user_id);

   public PostEntity makeComment(Long user_id, Long post_id , CommentEntity newComment);

   public ChatEntity startChat(Long user_id, Long other_person_id);

   public ChatEntity sendMessage(String message, Long chat_id);

   @Deprecated
   public UserEntity saveUserImage(byte[] imageBytes, String imageName, Long user_id);

    @Deprecated
   public FileSystemResource findUserImage(String username);

    @Deprecated
   public UserEntity savePostImageMadeByUser(byte[] imageBytes, String imageName, Long user_id, Long post_id);

//   public FileSystemResource findUserPostImage(Long post_id, Long user_id);

   public void refreshToken(HttpServletRequest request, HttpServletResponse response);


}
