package br.com.kopili.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kopili.models.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
}
