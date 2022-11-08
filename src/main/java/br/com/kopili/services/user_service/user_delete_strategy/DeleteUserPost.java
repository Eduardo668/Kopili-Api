package br.com.kopili.services.user_service.user_delete_strategy;

import org.springframework.stereotype.Service;

import br.com.kopili.models.UserEntity;
import br.com.kopili.services.post_service.PostService;

@Deprecated
@Service
public class DeleteUserPost implements UserDeleteStrategy {

    private final PostService postService;

    public DeleteUserPost(PostService postService){
        this.postService = postService;
    }

    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        System.out.println("Antes do if (POST)");
        if (!user.getUser_posts().isEmpty()){
            System.out.println("Dentro do if (POST)");
            user.getUser_posts().forEach(post -> {
                System.out.println("Dentro do foreach (POST)");
                postService.deletePost(post.getId());
            });
        }

    }


}