package br.com.api.services.follower_service;

import java.util.List;

import br.com.api.models.FollowerEntity;

public interface FollowerService {
    
    public FollowerEntity createFollowEntity(FollowerEntity newFollow);

    public List<FollowerEntity> findAll();

    public FollowerEntity removeFollow(Long follow_id);

}
