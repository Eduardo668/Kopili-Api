package br.com.kopili.component;

import br.com.kopili.models.RoleEntity;
import br.com.kopili.services.role_service.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CreateRole implements CommandLineRunner {

    private final RoleServiceImpl roleService;

    @Override
    public void run(String... args) throws Exception {

//        RoleEntity role_user =  new RoleEntity();
//        role_user.setNome("ROLE_USER");
//
//        RoleEntity role_admin = new RoleEntity();
//        role_admin.setNome("ROLE_ADMIN");
//
//        roleService.createRole(role_user);
//        roleService.createRole(role_admin);



    }
}
