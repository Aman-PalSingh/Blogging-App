package com.aps.Services;

import java.util.List;

import com.aps.Entities.Post;
import com.aps.Payloads.PostDto;
import com.aps.Payloads.PostResponse;

public interface PostService {
	// create
	public PostDto createPost(PostDto postDto, Integer userId, Integer postId);

	// update
	public PostDto updatePost(Integer postId, PostDto postDto, Integer userId, Integer categoryId);

	// delete
	public void deletePost(Integer postId);

	// getAPost
	public PostDto getAPost(Integer postId);

	// getAllPost
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDirection);

	// getAllPostByUser
	public List<PostDto> getPostByUserId(Integer userId);

	// getAllPostByCategory
	public List<PostDto> getPostByCategoryId(Integer categoryId);
	
	//searching
	public List<PostDto> searchPost(String Keyword);

}
