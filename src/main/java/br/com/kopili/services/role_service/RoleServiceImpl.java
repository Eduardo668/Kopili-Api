package br.com.kopili.services.role_service;

import br.com.kopili.models.RoleEntity;
import br.com.kopili.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity createRole(RoleEntity newRole) {
        try {
            return roleRepository.save(newRole);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao criar uma role", e);
        }
    }

    @Override
    public void deleteRole(Long role_id) {
        try {
            Optional<RoleEntity> role_data = roleRepository.findById(role_id);
            if (role_data.isEmpty()){
                throw new RuntimeException("Esta role não existe");
            }

            roleRepository.delete(role_data.get());


        } catch (Exception e){
            throw new RuntimeException("Erro ao deletar uma role", e);
        }
    }

    @Override
    public RoleEntity findRoleByName(String name) {
        try {
            RoleEntity role_data = roleRepository.findByNome(name);
            if (role_data == null){
                throw new RuntimeException("Esta role não existe");
            }

            return role_data;

        }catch (Exception e){
            throw new RuntimeException("Erro ao procurar uma role pelo nome", e);
        }
    }

    @Override
    public List<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

}
