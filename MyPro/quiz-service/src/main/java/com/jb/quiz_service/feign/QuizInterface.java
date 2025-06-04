package com.jb.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jb.quiz_service.entity.QuestionWrapper;
import com.jb.quiz_service.entity.Response;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface 
{
    @GetMapping("question/generate")
	public ResponseEntity<List<Long>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);

	@PostMapping("question/getQuestion")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Long> questionIds);


	@PostMapping("question/getScore")
    public Integer getScore(@RequestBody List<Response> responses);

    

}
