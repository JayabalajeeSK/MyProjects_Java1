package com.jb.question_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.question_service.entity.Question;
import com.jb.question_service.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController 
{
	@Autowired
	private QuestionService questionService;

	@PostMapping("/addQuestion")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question)
	{
		questionService.addQuestion(question);
		return ResponseEntity.ok(question);
	}

	@PutMapping("/updateQuestion/{id}")
	public ResponseEntity<Question> addQuestion(@PathVariable("id") Long id, @RequestBody Question updateQuestion)
	{
		Question updatedQuestion = questionService.updateQuestion(id, updateQuestion);
		return ResponseEntity.ok(updatedQuestion);
	}

	@DeleteMapping("/deleteQuestion/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("id") Long id) 
	{
		questionService.deleteQuestionById(id);
		return new ResponseEntity<>("Id: " + id + " Deleted Successfully", HttpStatus.OK);
		//return ResponseEntity.ok("Id: "+id +" Deleted Successfully");
	}

	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>>  getAllQuestions()
	{
		return ResponseEntity.ok(questionService.getAllQuestions());
		
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Question>>  getQuestionsByCategory(@PathVariable String category)
	{
		return ResponseEntity.ok(questionService.getQuestionsByCategory(category));
	}

	@GetMapping("/difficultyLevel/{difficultyLevel}")
	public ResponseEntity<List<Question>>  getQuestionsByDifficultyLevel(@PathVariable String difficultyLevel)
	{
		return ResponseEntity.ok(questionService.getQuestionsByDifficultyLevel(difficultyLevel));
	}
}