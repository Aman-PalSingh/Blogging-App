package com.aps.Services;

import java.util.List;

import com.aps.Entities.Post;
import com.aps.Payloads.PostDto;

public interface PostService {
	//create
	public PostDto createPost(PostDto postDto,Integer userId, Integer postId);
	
	//update
	public PostDto updatePost(Integer postId);
	
	//delete
	public void deletePost(Integer postId);
	
	//getAPost
	public PostDto getpost(Integer postId);
	
	//getAllPost
	public List<PostDto> getAllPosts();
	
	//getAllPostByUser
	public List<PostDto> getPostByUserId(Integer userId);
	
	//getAllPostByCategory
	public List<PostDto> getPostByCategoryId(Integer categoryId);
	
}
