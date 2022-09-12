package br.com.api.services.friendship_service;

import java.util.List;

import br.com.api.models.Friendship;

public interface FriendshipService {
    
    public Friendship createFriendship(Friendship newFriendship);

    public List<Friendship> findAll();

    public Friendship removeFriendship(Long friendship_id);

}
