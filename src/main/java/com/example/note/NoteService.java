package com.example.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Сохранение заметки
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    // Фильтрация по категории и названию
    public List<Note> filterNotes(String category, String title) {
        return noteRepository.findByCategoryContainingIgnoreCaseAndTitleContainingIgnoreCase(category, title);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }
}
