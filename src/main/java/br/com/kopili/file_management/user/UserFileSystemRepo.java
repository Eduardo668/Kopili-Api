package br.com.kopili.file_management.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class UserFileSystemRepo {
    

    public Path currentDir = Paths.get("").toAbsolutePath();
    public String fileSystemDir = currentDir.normalize().toString();
    public String userImageDir = fileSystemDir + "/users_images" ;

    public File pathAsFile = new File(userImageDir);


    public String save(byte[] content, String imageName) throws Exception{
        
        if (!Files.exists(Paths.get(userImageDir))){
            pathAsFile.mkdir();
        }


        Path newImage = Paths.get(userImageDir, new Date().getTime() + "-" + imageName);
        Files.createDirectories(newImage.getParent());

        Files.write(newImage, content);

        return newImage.toAbsolutePath().toString();
    }


    public FileSystemResource findInUserImages(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao retornar a imagem", e);
        }
    }


    public void deleteAnImageInTheFileSystem(String location) throws IOException{

        Path path = Paths.get(location);
        Files.delete(path);
        
    }


}
