package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService)

    {
        this.noteService = noteService;
        this.userService = userService;
    }

//  Trang chủ
    @GetMapping
    public String getHomePage( Authentication authentication, @ModelAttribute("newFile") File newFile, @ModelAttribute("newNote") Note newNote,
                               @ModelAttribute("newCredential") Credential newCredential, Model model)
    {
        Integer userId = getUserId(authentication);
        model.addAttribute("note", this.noteService.getNoteListings(userId));

        return "home";
    }

    private Integer getUserId(Authentication authentication)
    {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        return user.getUserId();
    }

//  thêm mới ghi chú
    @PostMapping("add-note")
    public String newNote( Authentication authentication, @ModelAttribute("newFile") File newFile,
                           @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential, Model model)
    {
        String userName = authentication.getName();
        String newTitle = newNote.getNoteTitle();
        String noteIdStr = String.valueOf(newNote.getNoteId());
        String newDescription = newNote.getNoteDescription();
        if (noteIdStr.isEmpty()) {
            noteService.addNote(newTitle, newDescription, userName);
        } else {
            Note existingNote = getNote(Integer.parseInt(noteIdStr));
            noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
        }
        Integer userId = getUserId(authentication);
        model.addAttribute("note", noteService.getNoteListings(userId));
        model.addAttribute("result", "Thêm mới ghi chú thành công");

        return "result";
    }

//  Lấy ghi chú
    @GetMapping(value = "/get-note/{noteId}")
    public Note getNote(@PathVariable Integer noteId)
    {
        return noteService.getNote(noteId);
    }

//  Xóa dòng ghi chú
    @GetMapping(value = "/delete-note/{noteId}")
    public String deleteNote(    Authentication authentication, @PathVariable Integer noteId, @ModelAttribute("newNote") Note newNote,
                                 @ModelAttribute("newFile") File newFile, @ModelAttribute("newCredential") Credential newCredential, Model model)
    {
        noteService.deleteNote(noteId);
        Integer userId = getUserId(authentication);
        model.addAttribute("note", noteService.getNoteListings(userId));
        model.addAttribute("result", "Xóa ghi chú thành công");

        return "result";
    }
}
