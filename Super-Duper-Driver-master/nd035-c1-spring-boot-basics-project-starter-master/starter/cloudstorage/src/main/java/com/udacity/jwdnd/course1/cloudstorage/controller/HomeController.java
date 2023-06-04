package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController( FileService fileService, UserService userService, NoteService noteService,
                           CredentialService credentialService, EncryptionService encryptionService)
    {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
//  Trang chủ
    @GetMapping
    public String getHomePage( Authentication authentication, @ModelAttribute("newFile") File newFile,
                               @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential, Model model)
    {
        Integer userId = getUserId(authentication);
        model.addAttribute("files", this.fileService.getFileListings(userId));
        model.addAttribute("notes", noteService.getNoteListings(userId));
        model.addAttribute("credentials", credentialService.getCredentialListings(userId));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }

    private Integer getUserId(Authentication authentication)
    {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }
//  Thêm file mới
    @PostMapping
    public String newFile( Authentication authentication, @ModelAttribute("newFile") File newFile,
                           @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential, Model model) throws IOException
    {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        Integer userId = user.getUserId();
        String[] fileListings = fileService.getFileListings(userId);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (String fileListing: fileListings) {
            if (fileListing.equals(fileName)) {
                fileIsDuplicate = true;

                break;
            }
        }
        if (!fileIsDuplicate) {
            fileService.addFile(multipartFile, userName);
            model.addAttribute("result", "Thêm thành công");
        } else {
            model.addAttribute("result", "Thêm lỗi");
            model.addAttribute("message", "File đã tồn tại");
        }
        model.addAttribute("files", fileService.getFileListings(userId));
        return "result";
    }
//  Lấy file
    @GetMapping( value = "/get-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public @ResponseBody byte[] getFile(@PathVariable String fileName)
    {
        return fileService.getFile(fileName).getFileData();
    }
//  Xóa file
    @GetMapping(value = "/delete-file/{fileName}")
    public String deleteFile( Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") File newFile,
            @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential, Model model)
    {
        fileService.deleteFile(fileName);
        Integer userId = getUserId(authentication);
        model.addAttribute("files", fileService.getFileListings(userId));
        model.addAttribute("result", "Xóa thành công");
        return "result";
    }
}
