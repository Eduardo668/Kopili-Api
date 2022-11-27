package br.com.kopili.services.page_service;

import br.com.kopili.models.PostEntity;
import br.com.kopili.models.UserEntity;
import br.com.kopili.services.post_service.PostServiceImpl;
import br.com.kopili.services.user_service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl implements PageService {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    @Override
    public List<PostEntity> feedPage(String username) {
        try{
            UserEntity user_data = userService.findUserByUsername(username);
//            System.out.println(user_data);

            List<PostEntity> feed_posts = new ArrayList<>();
            List<UserEntity> user_followers = new ArrayList<>();

            user_data.getFollow_list().forEach(follower -> {
                user_followers.add(userService.findUserById(follower.getUser1()));
            });
//            log.info("user_follower" + user_followers.get(0).getUsername());
            user_followers.forEach(user ->{
                user.getUser_posts().forEach(post ->{
                    feed_posts.add(post);
                });
            });


            return feed_posts;


        }
        catch (Exception e){
            throw new RuntimeException("Erro ao gerar os dados da pagina home");
        }
    }

    @Override
    public List<PostEntity> explorarPage() {
        try{
            return postService.findAllPost();
        } catch (Exception e){
            throw new RuntimeException("Erro ao retornar os posts para o explorar", e);
        }
    }

    @Override
    public UserEntity perfilPage(String username) {
        try{
            UserEntity user_data = userService.findUserByUsername(username);
            if (user_data == null){
                throw new RuntimeException("Este usuario não existe");
            }

            return user_data;
        }catch (Exception e){
            throw new RuntimeException("Erro ao retornar o usuario", e);
        }
    }
}
