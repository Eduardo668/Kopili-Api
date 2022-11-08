package br.com.kopili.services.user_service.user_delete_strategy;

import br.com.kopili.models.UserEntity;
import br.com.kopili.services.role_service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Deprecated
public class DeleteUserRoles implements UserDeleteStrategy {

    private final RoleServiceImpl roleService;
    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        if (!user.getRoles().isEmpty()){
            user.getRoles().forEach(role ->{
                roleService.deleteRole(role.getId());
            });


        }

    }
}
