package com.example.note;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;       // Название заметки
    private String category;    // Категория заметки
    @Column(columnDefinition = "TEXT")
    private String content;     // Содержимое заметки

    private String photoPath;   // Путь к фото
}
