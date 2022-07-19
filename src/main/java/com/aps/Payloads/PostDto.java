package com.aps.Payloads;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.aps.Entities.Category;
import com.aps.Entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer postId;

	private String title;

	private String content;

	private String imageName;

	private Date addDate;

	private CategoryDto category;

	private UserDto user;

}
