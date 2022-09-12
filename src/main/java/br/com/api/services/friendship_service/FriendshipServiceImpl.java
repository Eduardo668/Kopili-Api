package br.com.api.services.friendship_service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.api.models.Friendship;
import br.com.api.repository.FriendshipRepository;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    
    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public Friendship createFriendship(Friendship newFriendship) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Friendship> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Friendship removeFriendship(Long friendship_id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
