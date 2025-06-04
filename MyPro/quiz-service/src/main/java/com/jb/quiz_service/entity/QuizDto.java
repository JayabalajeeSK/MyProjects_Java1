package com.jb.quiz_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto 
{
    String categoryName;
    Integer numQuestions;
    String title;

}
