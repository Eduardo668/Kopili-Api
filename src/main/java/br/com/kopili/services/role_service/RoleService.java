package br.com.kopili.services.role_service;

import br.com.kopili.models.RoleEntity;

public interface RoleService {

    public RoleEntity createRole(RoleEntity newRole);

    public void deleteRole(Long role_id);


}
