package com.aps.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.Payloads.PostDto;
import com.aps.Services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	// create
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.OK);

	}

	// getPostsByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostByAUser(@PathVariable Integer userId) {
		List<PostDto> allPostsByUser = this.postService.getPostByUserId(userId);
		return new ResponseEntity<List<PostDto>>(allPostsByUser,HttpStatus.OK);
	}
	
	// getPostsByCategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostInACategory(@PathVariable Integer categoryId) {
		List<PostDto> allPostsInACategory = this.postService.getPostByCategoryId(categoryId);
		return new ResponseEntity<List<PostDto>>(allPostsInACategory,HttpStatus.OK);
	}

}
