package br.com.kopili.services.role_service;

import br.com.kopili.models.RoleEntity;

import java.util.List;

public interface RoleService {

    public RoleEntity createRole(RoleEntity newRole);

    public void deleteRole(Long role_id);

    public RoleEntity findRoleByName(String name);

    public List<RoleEntity> findAllRoles();


}
