package br.com.kopili.utility;

import java.util.ArrayList;
import java.util.List;

public final class AuthorizeAlwaysRoutesUtil {

    private static List<String> authorizedRoutes = new ArrayList<>();

    private Boolean isInAuthorizedRoutes = false;

    public void defineAuthorizedRoutes(){

        authorizedRoutes.add("/kopili/login");
        authorizedRoutes.add("/user/create");
        authorizedRoutes.add("/user/token/refresh");
        authorizedRoutes.add("/user/delete");
        authorizedRoutes.add("/user/create/admin");


    }

    public Boolean verifyRoute(String route){

        authorizedRoutes.forEach(rota -> {
            if (route.equals(rota)){
                this.isInAuthorizedRoutes = true;
            }
        });

        return this.isInAuthorizedRoutes;

    }


}
