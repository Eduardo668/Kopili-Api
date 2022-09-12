package br.com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.models.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    
}