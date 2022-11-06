package br.com.kopili.services.page_service;

import br.com.kopili.models.PostEntity;
import br.com.kopili.models.UserEntity;

import java.util.List;

public interface PageService {

    public List<PostEntity> feedPage(Long user_id);

    public List<PostEntity> explorarPage();

    public UserEntity perfilPage(Long user_id);


}
