package com.versacomllc.training.message.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Change password request ....
 * 
 * @author Shamim Ahmmed
 * 
 */
public final class ChangePasswordRequest {

	@NotNull(message="User id required")
	private Long Id;

	@NotEmpty(message = "User old password required")
	private String oldPassword;

	@NotEmpty(message = "User new password required")
	private String newPassword;
	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
