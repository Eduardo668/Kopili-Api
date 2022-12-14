package br.com.kopili.services.user_service;

import br.com.kopili.file_management.post.PostFileSystemRepo;
import br.com.kopili.file_management.user.UserFileSystemRepo;
import br.com.kopili.models.*;
import br.com.kopili.repository.UserRepository;
import br.com.kopili.services.chat_service.ChatServiceImpl;
import br.com.kopili.services.follower_service.FollowerServiceImpl;
import br.com.kopili.services.image_service.ImageServiceImpl;
import br.com.kopili.services.message_service.MessageServiceImpl;
import br.com.kopili.services.post_service.PostServiceImpl;
import br.com.kopili.services.role_service.RoleService;
import br.com.kopili.services.user_service.user_delete_strategy.*;
import br.com.kopili.services.comment_service.CommentServiceImpl;

import br.com.kopili.utility.AlgorithmUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final FollowerServiceImpl followerService;
    private final MessageServiceImpl messageService;
    private final ChatServiceImpl chatService;
    private final UserFileSystemRepo userFileSystem;
    private final PostFileSystemRepo postFileSystem;
    private final ImageServiceImpl imageService;

    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AlgorithmUtil algorithmUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user_data = userRepository.findByEmail(email);
        if(user_data == null){
            log.error("Este usuario n??o existe");
            throw new UsernameNotFoundException("User Not found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user_data.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getNome()));

        });

        return new User(
                user_data.getUsername(), user_data.getPassword(), authorities
        );

    }


    // Cadastra um usuario no Sistema
    @Override
    public UserEntity createUser(UserEntity newUser) {
        try {

            UserEntity user_data  = userRepository.findByUsername(newUser.getUsername());

            if (user_data != null){
                throw new IllegalStateException("Este Username j?? est?? sendo usado.");

            }

            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

            List<RoleEntity> roles = new ArrayList<>();
            roles.add(roleService.findRoleByName("ROLE_USER"));

           newUser.setRoles(roles);


            return userRepository.save(newUser);

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEntity createUserAdmin(UserEntity newUserAdmin) {
        try {

            UserEntity user_data  = userRepository.findByUsername(newUserAdmin.getUsername());

            if (user_data != null){
                throw new IllegalStateException("Este Username j?? est?? sendo usado.");

            }

            newUserAdmin.setPassword(passwordEncoder.encode(newUserAdmin.getPassword()));




            ArrayList<RoleEntity> roles = new ArrayList<>();
            roles.add(roleService.findRoleByName("ROLE_USER"));
            roles.add(roleService.findRoleByName("ROLE_ADMIN"));

            newUserAdmin.setRoles(roles);


            return userRepository.save(newUserAdmin);

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

    @Override
    public UserEntity findUserByPostId(Long post_id) {
        try {
            PostEntity post_data = postService.findPostById(post_id);
            if (post_data == null){
                throw new RuntimeException("Este post n??o existe");
            }
            return post_data.getUserPost();
        } catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao procurar um user pelo post id");
        }
    }


    // Deleta Usuario a partir do id
    @Override
    public void deleteUser(Long user_id) {
        try {

            Optional<UserEntity> user_data = userRepository.findById(user_id);

            if (user_data.isEmpty()){
                throw new IllegalStateException("Este Usu??rio n??o existe.");
            }

             userRepository.delete(user_data.get());
        
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao deletar o usuario",e);
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
                user.setImage_url(editedUser.getImage_url());
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
    public UserEntity findUserById(Long user_id) {
        try {
            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if (user_data.isEmpty()){
                throw new RuntimeException("Este usuario n??o existe");
            }

            return user_data.get();

        }catch (Exception e){
            throw new RuntimeException("Erro ao procurar o usuario pelo id", e);
        }
    }

    // Se tornar seguidor de outro usuario
    @Override
    public void follow(String your_username, String follow_username) {
          try {

              UserEntity user1_data = userRepository.findByUsername(your_username);
              UserEntity user2_data = userRepository.findByUsername(follow_username);

              if (user1_data == null){
                  throw new RuntimeException("Error com user 1");
              } else if (user2_data == null) {
                  throw new RuntimeException("Error com user 2");
              }

              FollowerEntity followerObject = new FollowerEntity();

              followerObject.setUser1(user2_data.getId());

              List<UserEntity> userArrayForSave = new ArrayList<>();

              userArrayForSave.add(user1_data);

              followerObject.setUserFollowed(userArrayForSave);

              followerService.createFollowEntity(followerObject);

          }catch (Exception e){
              throw new RuntimeException("Deu ruim ao makar a friend", e);
          }
    }

    // Fazer uma publica????o
    @Override
    public UserEntity makePost(String username, PostEntity newPost) {
        try {
            UserEntity user_data = userRepository.findByUsername(username);
            if(user_data == null){
                throw new RuntimeException("Deu ruim");
            }

            List<PostEntity> postEntities = new ArrayList<>();

            postEntities.add(newPost);

            newPost.setUserPost(user_data);

            postService.createPost(newPost);

            return user_data;

        }catch (Exception e){
            throw new RuntimeException("Falhou na ao fazer o post",e);
        }
    }

    // Encontrar um usuario a partir do seu username
    @Override
    public UserEntity findUserByUsername(String username) {

        UserEntity user = userRepository.findByUsernameContains(username);

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

    // Faz um comentario em uma publica????o
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
           throw new RuntimeException("Falhou ao fazer o coment??rio",e);
       }
	}

    // Inicia um chat com outro usuario
    @Override
    public ChatEntity startChat(Long user_id, Long other_person_id) {
        try {
            Optional<UserEntity> user1 = userRepository.findById(user_id);
            Optional<UserEntity> user2 = userRepository.findById(other_person_id);

            if (user1.isEmpty()){
                throw new RuntimeException("Este usuario n??o existe");
            }
            if (user2.isEmpty()){
                throw new RuntimeException("Este usuario n??o existe");
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

    @Deprecated
    @Override
    public UserEntity saveUserImage(byte[] imageBytes, String imageName, Long user_id) {

        try{

            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if (user_data.isEmpty()){
                throw new RuntimeException("Este usuario n??o existe");
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

    @Deprecated
    @Override
    public FileSystemResource findUserImage(String username) {
        UserEntity user_data = userRepository.findByUsername(username);
        if (user_data == null){
            throw new RuntimeException("Este usuario n??o existe");
        }

        ImageEntity userImage = user_data.getUserImage();

        return userFileSystem.findInUserImages(userImage.getLocation());

    }

    @Deprecated
    @Override
    public UserEntity savePostImageMadeByUser(byte[] imageBytes, String imageName,
     Long user_id, Long post_id) {
        
        try{
            String imageLocation = postFileSystem.save(imageBytes, imageName);
            
            ImageEntity newPostImage = new ImageEntity();
            newPostImage.setLocation(imageLocation);
            newPostImage.setNome(imageName);

            

            Optional<UserEntity> user_data = userRepository.findById(user_id);
            if (user_data.isEmpty()){
                throw new RuntimeException("Este usuario n??o existe");
            }
            
            PostEntity post_data = postService.findPostById(post_id);

            if (post_data == null){
                throw new RuntimeException("Este post n??o existe");
            }
           
                for (PostEntity userPost: user_data.get().getUser_posts()) {
                    Boolean postExistsInUser = false;
                    if (userPost.getId().equals(post_data.getId())){
                        post_data.setImage(imageService.saveImage(newPostImage));
                        postService.editPost(post_data, post_data.getId());
                        postExistsInUser = true;
                    }
                    if (postExistsInUser == false){
                        throw new RuntimeException("Este post n??o pertence a este usuario");
                    }


                }
            
            return user_data.get();
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao salvar a imagem do post",e);
        }
        
    }

//    @Override
//    public FileSystemResource findUserPostImage(Long post_id, Long user_id) {
//        PostEntity post_data = postService.findPostById(post_id);
//        if (post_data == null){
//            throw new RuntimeException("Este post n??o existe");
//        }
//
//        Optional<UserEntity> user_data = userRepository.findById(user_id);
//        if (user_data.isEmpty()){
//            throw new RuntimeException("Este usuario n??o existe");
//        }
//
//        ImageEntity postImage = post_data.getImage();
//
//        Boolean postExistsInUser = false;
//        for (PostEntity userPost: user_data.get().getUser_posts()) {
//            if (userPost.getId().equals(post_data.getId())){
//                postExistsInUser = true;
//                break;
//            }
//        }
//        if (postExistsInUser == false){
//            throw new RuntimeException("Este post n??o pertence a este usuario");
//        }
//        return postFileSystem.findInPostImages(postImage.getLocation());
//    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            try{

                String pure_jwt_token = authorizationHeader.substring("Bearer ".length());

                JWTVerifier verifier = JWT.require(algorithmUtil.defineAlgorithm()).build();

                DecodedJWT decodedJWT = verifier.verify(pure_jwt_token);

                String username = decodedJWT.getSubject();

                UserEntity user_data = userRepository.findByUsername(username);
                String newAccessToken = JWT.create()
                        .withSubject(user_data.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 80 * 60 * 1000))
                        .withIssuer("Kopili Enterprise")
                        .withClaim("roles", user_data.getRoles().stream().map(RoleEntity::getNome).collect(Collectors.toList()))
                        .sign(algorithmUtil.defineAlgorithm());


                Map<String, String> access_token = new HashMap<>();
                access_token.put("access_token", newAccessToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), access_token);


            }catch (Exception e){

                response.setHeader("error", e.getMessage());
                Map<String, String> error_message = new HashMap<>();
                error_message.put("error", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                try {
                    new ObjectMapper().writeValue(response.getOutputStream(), error_message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            throw  new RuntimeException("Esta faltando o refresh_token");
        }

    }


    @Override
    public List<UserEntity> findUsersByUsername(String username) {
        try{
            return userRepository.findByUsernameStartsWith(username);
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao procurar usuarios que come??am com x valor", e);
        }
    }


}
