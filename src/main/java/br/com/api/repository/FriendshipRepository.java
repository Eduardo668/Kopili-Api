package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.models.FriendshipEntity;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipEntity, Long> {
    
}
