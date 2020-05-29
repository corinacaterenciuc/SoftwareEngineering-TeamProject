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
import theotherhalf.superconference.exceptions.CMSForbidden;
import theotherhalf.superconference.services.ConferenceService;
import theotherhalf.superconference.services.FileService;
import theotherhalf.superconference.services.ProposalService;
import theotherhalf.superconference.services.UserService;

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

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @PostMapping(path = "api/conferences/{confId}/proposals/{proposalId}/upload")
    public UploadFileResponse uploadFile(@RequestHeader(name="Authorization") String token, @PathVariable("confId") Long confId, @PathVariable("proposalId") Long proposalId, @RequestParam("file") MultipartFile file)
    {
        String tokenEmail = this.userController.getEmailFromToken(token);
        if(!this.proposalService.getProposal(confId, proposalId).getAuthor().getEmail().equals(tokenEmail))
        {
            throw new CMSForbidden("[ERROR] No rights to upload file to proposal");
        }

        DBFile dbFile = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/conferences/")
                .path(confId.toString())
                .path("/proposals/")
                .path(proposalId.toString())
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        this.proposalService.setFilePath(confId, proposalId, fileDownloadUri);

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