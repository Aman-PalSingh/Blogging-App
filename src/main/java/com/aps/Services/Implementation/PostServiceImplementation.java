package com.aps.Services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aps.Entities.Category;
import com.aps.Entities.Post;
import com.aps.Entities.User;
import com.aps.Exceptions.ResourceNotFoundException;
import com.aps.Payloads.PostDto;
import com.aps.Payloads.PostResponse;
import com.aps.Repositories.CategoryRepo;
import com.aps.Repositories.PostRepo;
import com.aps.Repositories.UserRepo;
import com.aps.Services.PostService;

import net.bytebuddy.asm.Advice.This;

@Service
public class PostServiceImplementation implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setAddDate(new Date());
		post.setImageName("Default.png");
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(Integer postId, PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
//		post.setAddDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);

	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getAPost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDirection) {
		// TODO Auto-generated method stub
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable page = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(page);
		List<Post> allPost = pagePost.getContent();
		List<PostDto> allpostDto = allPost.stream().map((p) -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());

//		List<Post> allPost = this.postRepo.findAll();
//		List<PostDto> allpostDto = allPost.stream().map((p) -> this.modelMapper.map(p, PostDto.class))
//				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allpostDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public List<PostDto> getPostByUserId(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((p) -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((p) -> this.modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

}
