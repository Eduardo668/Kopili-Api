package br.com.api.file_management.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UserImagesUploadUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException{

        Path uploadPath = Paths.get("Users-Images");

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphabetic(8);

        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException ioe){
            throw new IOException("Não foi possivel salvar a imagem:"+ fileName,ioe);
        }

        return fileCode;

    }


}
