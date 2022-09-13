package br.com.api.services.friendship_service;

import java.util.List;

import br.com.api.models.FriendshipEntity;

public interface FriendshipService {
    
    public FriendshipEntity createFriendship(FriendshipEntity newFriendship);

    public List<FriendshipEntity> findAll();

    public FriendshipEntity removeFriendship(Long friendship_id);

}
