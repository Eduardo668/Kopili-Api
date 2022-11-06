package br.com.kopili.services.user_service.user_delete_strategy;

import org.springframework.stereotype.Service;

import br.com.kopili.models.UserEntity;
import br.com.kopili.services.follower_service.FollowerServiceImpl;

@Service
public class DeleteUserFollowers implements UserDeleteStrategy {


    private final FollowerServiceImpl followerService;

    public DeleteUserFollowers(FollowerServiceImpl followerService){
        this.followerService = followerService;
    }

    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        if (!user.getFollow_list().isEmpty()){
            user.getFollow_list().forEach(follower -> {
                followerService.removeFollow(follower.getId());
            });
        }

    }


}