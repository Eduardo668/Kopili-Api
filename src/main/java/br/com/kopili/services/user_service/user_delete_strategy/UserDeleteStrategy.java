package br.com.kopili.services.user_service.user_delete_strategy;



import br.com.kopili.models.UserEntity;

public interface UserDeleteStrategy {

    public void verifyAndDeleteIfExist(UserEntity user);

}