package br.com.kopili.services.user_service.user_delete_strategy;



import br.com.kopili.models.UserEntity;

// Não esta mais em utilização
@Deprecated
public interface UserDeleteStrategy {

    public void verifyAndDeleteIfExist(UserEntity user);

}