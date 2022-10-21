package br.com.api.services.user_service.user_delete_strategy;



import br.com.api.models.UserEntity;

public interface UserDeleteStrategy {

    public void verifyAndDeleteIfExist(UserEntity user);

}