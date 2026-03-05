package com.example.app_check_java.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "add_date")
    private LocalDateTime addDate;

    @Column(name = "mod_date")
    private LocalDateTime modDate;

    @OneToMany(mappedBy = "category", // Связь двунаправленная, владелец - Topic
            cascade = CascadeType.ALL, // Все операции передаются дочерним темам
            orphanRemoval = true) // Удалять темы, отвязанные от категории
    @Builder.Default // Защита от null при использовании билдера
    private List<Topic> topics = new ArrayList<>();
}
