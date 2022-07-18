package com.aps.Payloads;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "Name must be atleast of 4 Characters !")
	private String name;
	
	@Email
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",message = "Passwords must contain:, a minimum of 1 lower case letter [a-z], a minimum of 1 upper case letter [A-Z] and, a minimum of 1 numeric character [0-9] ")
	private String password;
	
	@NotEmpty
	private String about;

}
