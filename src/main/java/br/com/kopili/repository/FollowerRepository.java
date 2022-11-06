package br.com.kopili.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kopili.models.FollowerEntity;

@Repository
public interface FollowerRepository extends JpaRepository<FollowerEntity, Long> {
    
}
