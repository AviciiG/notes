package com.example.note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    // Путь для сохранения файлов в корневую папку проекта
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    // Список заметок и фильтрация
    @GetMapping
    public String listNotes(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String title,
                            Model model) {
        // Если параметры пустые, возвращаем все заметки
        List<Note> notes = noteService.filterNotes(
                category == null ? "" : category,
                title == null ? "" : title
        );
        model.addAttribute("notes", notes);
        return "note-list";
    }

    // Форма добавления новой заметки
    @GetMapping("/new")
    public String newNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note-form";
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note,
                           @RequestParam("photo") MultipartFile photo) throws IOException {
        // Создаём директорию, если её нет
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Сохраняем файл
        if (!photo.isEmpty()) {
            String fileName = photo.getOriginalFilename();
            String photoPath = UPLOAD_DIR + fileName;
            photo.transferTo(new File(photoPath));
            note.setPhotoPath(fileName);
        }

        noteService.saveNote(note);
        return "redirect:/notes";
    }

    @PostMapping("/deleteAll")
    public String deleteAllNotes() {
        noteService.deleteAllNotes();
        return "redirect:/notes";
    }

}
