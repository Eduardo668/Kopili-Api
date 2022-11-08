package br.com.kopili.services.page_service;

import br.com.kopili.models.PostEntity;
import br.com.kopili.models.UserEntity;

import java.util.List;

public interface PageService {

    public List<PostEntity> feedPage(String username);

    public List<PostEntity> explorarPage();

    public UserEntity perfilPage(Long user_id);


}
