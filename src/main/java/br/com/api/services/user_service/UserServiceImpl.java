package br.com.api.services.user_service;

import br.com.api.file_management.post.PostFileSystemRepo;
import br.com.api.file_management.user.UserFileSystemRepo;
import br.com.api.models.*;
import br.com.api.repository.UserRepository;
import br.com.api.services.chat_service.ChatServiceImpl;
import br.com.api.services.follower_service.FollowerServiceImpl;
import br.com.api.services.image_service.ImageServiceImpl;
import br.com.api.services.message_service.MessageServiceImpl;
import br.com.api.services.post_service.PostServiceImpl;
import br.com.api.services.comment_service.CommentServiceImpl;

import org.springframework.core.io.FileSystemResource;
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
    private final UserFileSystemRepo userFileSystem;
    private final PostFileSystemRepo postFileSystem;
    private final ImageServiceImpl imageService;
    

    public UserServiceImpl(UserRepository userRepository, PostServiceImpl postService,
                           CommentServiceImpl commentService, FollowerServiceImpl friendshipService,
                           ChatServiceImpl chatService, MessageServiceImpl messageService, 
                           UserFileSystemRepo userFileSystem, ImageServiceImpl imageService, 
                           PostFileSystemRepo postFileSystem) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.friendshipService = friendshipService;
        this.chatService = chatService;
        this.messageService = messageService;
        this.userFileSystem = userFileSystem;
        this.imageService = imageService;
        this.postFileSystem = postFileSystem;
    }


    // Cadastra um usuario no Sistema
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

    // Retorna todos os usuario cadastrados no banco de dados
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    // Deleta Usuario a partir do id
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
    
    // Edita um Usuario 
    @Override
    public UserEntity editUser(UserEntity editedUser, Long id) {
        try {
            return userRepository.findById(id).map(user -> {
                user.setFullname(editedUser.getFullname());
                user.setEmail(editedUser.getEmail());
                user.setBorn(editedUser.getBorn());
                user.setPassword(editedUser.getPassword());
                user.setUsername(editedUser.getUsername());
                user.setUserImage(editedUser.getUserImage());
                return userRepository.save(user);
            }).orElseGet(() -> {
                editedUser.setId(id);
                return userRepository.save(editedUser);
            });
        } catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }

    // Se tornar seguidor de outro usuario
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

              friendshipObject.setUserFollowed(userArrayForSave);

              friendshipService.createFollowEntity(friendshipObject);

          }catch (Exception e){
              throw new RuntimeException("Deu ruim ao makar a friend", e);
          }
    }

    // Fazer uma publicação
    @Override
    public UserEntity makePost(Long user_id, PostEntity newPost) {
        try {
            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if(user_data.isEmpty()){
                throw new RuntimeException("Deu ruim");
            }

            List<PostEntity> postEntities = new ArrayList<>();

            postEntities.add(newPost);

            newPost.setUserPost(user_data.get());

            postService.createPost(newPost);

            return user_data.get();

        }catch (Exception e){
            throw new RuntimeException("Falhou na ao fazer o post",e);
        }
    }

    // Encontrar um usuario a partir do seu username
    @Override
    public UserEntity findUserByUsername(String username) {

        UserEntity user = userRepository.findByUsername(username);

        return user;
    }

    // Retorna todos os usuario que um determinado usuario esta seguindo
    @Override
    public List<UserEntity> findAllFollowedUsers(Long user_id) {
        Optional<UserEntity> user_data = userRepository.findById(user_id);

        List<FollowerEntity> followers_list = user_data.get().getFollow_list();

        ArrayList<UserEntity> user_friends = new ArrayList<>();


        for (FollowerEntity friendship: followers_list) {
            Optional<UserEntity> user1 = userRepository.findById(friendship.getUser1());

            user_friends.add(user1.get());
        }

        return user_friends;

    }

    // Faz um comentario em uma publicação
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
           newComment.setPost_comments(postHashForSave);

           commentService.createComment(newComment);

           return post_data;

       }catch (Exception e){
           throw new RuntimeException("Falhou ao fazer o comentário",e);
       }
	}

    // Inicia um chat com outro usuario
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

    // Envia uma mensagem em um chat
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


    @Override
    public UserEntity saveUserImage(byte[] imageBytes, String imageName, Long user_id) {

        try{

            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if (user_data.isEmpty()){
                throw new RuntimeException("Este usuario não existe");
            }
    
            String location = userFileSystem.save(imageBytes, imageName);
            
            ImageEntity newUserImage = new ImageEntity();
    
            newUserImage.setLocation(location);
            newUserImage.setNome(imageName);
    
            user_data.get().setUserImage(imageService.saveImage(newUserImage));
    
            return editUser(user_data.get(), user_id);
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao salvar a imagem do usuario", e);
        }
 
    }


    @Override
    public FileSystemResource findUserImage(Long user_id) {
        Optional<UserEntity> user_data = userRepository.findById(user_id);
        if (user_data.isEmpty()){
            throw new RuntimeException("Este usuario não existe");
        }

        ImageEntity userImage = user_data.get().getUserImage();

        return userFileSystem.findInUserImages(userImage.getLocation());

    }

    @Override
    public UserEntity savePostImageMadeByUser(byte[] imageBytes, String imageName, Long user_id, Long post_id) {
        
        try{
            
            System.out.println("Antes de salvar a imagem no fileSystem");

            String imageLocation = postFileSystem.save(imageBytes, imageName);

            System.out.println("Depois de salvar a imagem no fileSystem");
            
            ImageEntity newPostImage = new ImageEntity();
            newPostImage.setLocation(imageLocation);
            newPostImage.setNome(imageName);

            

            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if (user_data.isEmpty()){
                throw new RuntimeException("Este usuario não existe");
            }
            
            PostEntity post_data = postService.findPostById(post_id);

            if (post_data == null){
                throw new RuntimeException("Este post não existe");
            }
           
                for (PostEntity userPost: user_data.get().getUser_posts()) {
                    Boolean postExistsInUser = false;
                    if (userPost.getId().equals(post_data.getId())){
                        post_data.setImage(imageService.saveImage(newPostImage));
                        postService.editPost(post_data, post_data.getId());
                        postExistsInUser = true;
                    }
                    if (postExistsInUser == false){
                        throw new RuntimeException("Este post não pertence a este usuario");
                    }


                }
            
            return user_data.get();
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao salvar a imagem do post",e);
        }
        
    }

    @Override
    public FileSystemResource findUserPostImage(Long post_id, Long user_id) {
        PostEntity post_data = postService.findPostById(post_id);
        if (post_data == null){
            throw new RuntimeException("Este post não existe");
        }

        Optional<UserEntity> user_data = userRepository.findById(user_id);
        if (user_data.isEmpty()){
            throw new RuntimeException("Este usuario não existe");
        }

        ImageEntity postImage = post_data.getImage();
        
        Boolean postExistsInUser = false;
        for (PostEntity userPost: user_data.get().getUser_posts()) {
            if (userPost.getId().equals(post_data.getId())){
                postExistsInUser = true;
                break;
            }
        }
        if (postExistsInUser == false){
            throw new RuntimeException("Este post não pertence a este usuario");
        }
        return postFileSystem.findInPostImages(postImage.getLocation());
    }



}
