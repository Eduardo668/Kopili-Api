package br.com.api.services.follower_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.api.models.FollowerEntity;
import br.com.api.repository.FollowerRepository;

@Service
public class FollowerServiceImpl implements FollowerService {
    
    private final FollowerRepository followerRepository;

    public FollowerServiceImpl(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }

    @Override
    public FollowerEntity createFollowEntity(FollowerEntity newFriendship) {
        try{
            return followerRepository.save(newFriendship);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao criar a friendship", e);
        }
    }

    @Override
    public List<FollowerEntity> findAll() {
        return followerRepository.findAll();
    }

    @Override
    public FollowerEntity removeFollow(Long friendship_id) {
        try {
            Optional<FollowerEntity> friendship_opt = followerRepository.findById(friendship_id);
            if(friendship_opt.isEmpty()){
                  throw new RuntimeException("Deu Ruim ao remover a friendship");
            }
            followerRepository.delete(friendship_opt.get());
            return friendship_opt.get();
        }catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }
    
}
