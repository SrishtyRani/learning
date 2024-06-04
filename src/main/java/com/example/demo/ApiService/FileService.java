package com.example.demo.ApiService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.FormDataSave;
import com.example.demo.Model.MyUser;
import com.example.demo.Repository.FormDataSaveRepository;
import com.example.demo.Repository.MyUserRepository;



@Service
public class FileService {

  
	private static String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "profilepic";

    @Autowired
    private MyUserRepository userRepository;
    
    @Autowired
	  private  FormDataSaveRepository  formDataSaveRepository ;

  

    public void saveformFile(MultipartFile file, String userId) throws IOException {
        if (!Files.exists(Paths.get(uploadDir))) {
            Files.createDirectories(Paths.get(uploadDir));
        }

        String fileName = userId + "_" + file.getOriginalFilename();

        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

      
        String relativePath =  "profilepic" + "/" + fileName;

        MyUser user = userRepository.findById(Long.parseLong(userId)).orElse(null);
        if (user != null) {
            user.setPath(relativePath);
            userRepository.save(user);
        } else {
          
        }
    }

    
    




    @Transactional
    public void saveformfile(List<MultipartFile> files, Long formdatasaveId) throws IOException {
        FormDataSave formDataSave = formDataSaveRepository.findById(formdatasaveId).orElseThrow(() -> new RuntimeException("FormDataSave entity with id " + formdatasaveId + " not found."));

        if (files.size() < 1 || files.size() > 10) {
            throw new RuntimeException("The number of files must be between 1 and 10.");
        }

        List<String> newPaths = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            String fileName = formdatasaveId + "_" + originalFileName;
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            String relativePath = "profilepic" + "/" + fileName;
            newPaths.add(relativePath);
        }

        List<String> existingPaths = formDataSave.getPathsList(); 
        existingPaths.addAll(newPaths);

        formDataSave.setPathsList(existingPaths); 
        System.out.println("Saving FormDataSave entity: " + formDataSave);
        formDataSaveRepository.save(formDataSave);
        System.out.println("Entity saved successfully.");
    }
}






