package theotherhalf.superconference.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import theotherhalf.superconference.domain.DBFile;
import theotherhalf.superconference.dto.UploadFileResponse;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.FileService;
import theotherhalf.superconference.services.ProposalService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class FileController
{

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private FileService fileService;


    @PostMapping(path = "api/conferences/{confId}/proposals/{proposalId}/upload")
    public UploadFileResponse uploadFile(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestParam("file") MultipartFile file)
    {
        DBFile dbFile = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        this.proposalService.setFilePath(confId, proposalId, dbFile.getId());

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }


    @GetMapping(path = "api/conferences/{confId}/proposals/{proposalId}/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @PathVariable String fileId)
    {
        DBFile dbFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}