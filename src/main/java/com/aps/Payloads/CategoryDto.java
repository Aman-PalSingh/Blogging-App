package com.aps.Payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "Title should aleast be of 4 characters")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 7, message = "Descrition shold be of atleast 7 characters")
	private String categoryDescription;

}
