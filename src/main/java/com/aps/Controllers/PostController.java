package com.aps.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aps.Config.AppConstants;
import com.aps.Payloads.ApiResponse;
import com.aps.Payloads.PostDto;
import com.aps.Payloads.PostResponse;
import com.aps.Services.FileService;
import com.aps.Services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

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
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
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

	// updateAPost
	@PutMapping("/user/{userId}/category/{categoryId}/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @PathVariable Integer categoryId,
			@PathVariable Integer userId, @RequestBody PostDto postDto) {
		PostDto updatedUser = this.postService.updatePost(postId, postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(updatedUser, HttpStatus.OK);
	}

	// searchPostsbyTitle
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
		List<PostDto> searchedPosts = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchedPosts, HttpStatus.OK);
	}

	// uploadImage
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {
		PostDto postDto = this.postService.getAPost(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		Integer userId = postDto.getUser().getId();
		Integer categoryId = postDto.getCategory().getId();
		PostDto updatedPost = this.postService.updatePost( postId, postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
	}
	
	//serveImage
	@GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResources(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}

}
