package com.kios.app.agw.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name="KiosUser")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String username;
	@JsonProperty(access=Access.WRITE_ONLY)
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private boolean enabled;
    private boolean tokenExpired;
// 
//    @ManyToMany
//    @JoinTable( 
//        name = "users_roles", 
//        joinColumns = @JoinColumn(
//          name = "user_id", referencedColumnName = "id"), 
//        inverseJoinColumns = @JoinColumn(
//          name = "role_id", referencedColumnName = "id")) 
//    private Collection<Role> roles;
//    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getId() {
		return id;
	}
    public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isTokenExpired() {
		return tokenExpired;
	}
	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}
//	public Collection<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(Collection<Role> roles) {
//		this.roles = roles;
//	}
}
