package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.models.FollowerEntity;

@Repository
public interface FollowerRepository extends JpaRepository<FollowerEntity, Long> {
    
}
