package br.com.api.file_management.user;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class UserFileSystemRepo {
    
    public String UsersImagesDir = "/home/kopili/kopili-project/api/FileSystem/UserImages"; 

    public File pathAsFile = new File(UsersImagesDir);

    public String save(byte[] content, String imageName) throws Exception{
        
        if (!Files.exists(Paths.get(UsersImagesDir))){
            pathAsFile.mkdir();
        }


        Path newImage = Paths.get(UsersImagesDir, new Date().getTime() + "-" + imageName);
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


}
