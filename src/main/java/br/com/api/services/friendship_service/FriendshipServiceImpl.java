package br.com.api.services.friendship_service;

import java.util.List;
import java.util.Optional;

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
        try{
            return friendshipRepository.save(newFriendship);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao criar a friendship", e);
        }
    }

    @Override
    public List<Friendship> findAll() {
        return friendshipRepository.findAll();
    }

    @Override
    public Friendship removeFriendship(Long friendship_id) {
        try {
            Optional<Friendship> friendship_opt = friendshipRepository.findById(friendship_id);
            if(friendship_opt.isEmpty()){
                  throw new RuntimeException("Deu Ruim ao remover a friendship");
            }
            friendshipRepository.delete(friendship_opt.get());
            return friendship_opt.get();
        }catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }
    
}
