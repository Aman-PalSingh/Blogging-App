package com.aps.Controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aps.Payloads.ApiResponse;
import com.aps.Payloads.PostDto;
import com.aps.Payloads.PostResponse;
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
		return new ResponseEntity<List<PostDto>>(allPostsByUser, HttpStatus.OK);
	}

	// getPostsByCategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostInACategory(@PathVariable Integer categoryId) {
		List<PostDto> allPostsInACategory = this.postService.getPostByCategoryId(categoryId);
		return new ResponseEntity<List<PostDto>>(allPostsInACategory, HttpStatus.OK);
	}

	// getAllPost
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// getAPost
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getApost(@PathVariable Integer postId) {
		PostDto post = this.postService.getAPost(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	// deleteAPost
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deleteAPost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Delete Successfully", true), HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/category/{categoryId}/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @PathVariable Integer categoryId,
			@PathVariable Integer userId, @RequestBody PostDto postDto) {
		PostDto updatedUser = this.postService.updatePost(postId, postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(updatedUser, HttpStatus.OK);
	}

}
