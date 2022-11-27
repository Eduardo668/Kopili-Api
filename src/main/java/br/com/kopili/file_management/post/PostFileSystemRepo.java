package br.com.kopili.file_management.post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class PostFileSystemRepo {

    public Path currentDir = Paths.get("").toAbsolutePath();
    public String fileSystemDir = currentDir.normalize().toString();
    public String postImageDir = fileSystemDir + "/posts_images" ;

    public File pathAsFile = new File(postImageDir);


    public String save(byte[] content, String imageName) throws Exception{

            if (!Files.exists(Paths.get(postImageDir))){
                pathAsFile.mkdir();
            }

            Path newImage = Paths.get(postImageDir, new Date().getTime() + "-" + imageName);

            Files.createDirectories(newImage.getParent());

            Files.write(newImage, content);

            return newImage.toAbsolutePath().toString();

    }


    public FileSystemResource findInPostImages(String location) {
        try{
            return new FileSystemResource(Paths.get(location));
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao procurar a imagem no PostImages", e);
        }

    }


    public void deleteAnImageInTheFileSystem(String location) throws IOException{

        Path path = Paths.get(location);
        Files.delete(path);
        
    }

    

}
