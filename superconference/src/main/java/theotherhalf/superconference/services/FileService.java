package theotherhalf.superconference.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import theotherhalf.superconference.domain.DBFile;
import theotherhalf.superconference.exceptions.ValidationException;
import theotherhalf.superconference.repository.FileRepository;

import java.io.IOException;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public DBFile storeFile(MultipartFile file)
    {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try
        {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new ValidationException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return fileRepository.save(dbFile);
        }
        catch (IOException ex)
        {
            throw new ValidationException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId)
    {
        return fileRepository.findById(fileId).orElseThrow(() -> new ValidationException("File not found with id " + fileId));
    }
}