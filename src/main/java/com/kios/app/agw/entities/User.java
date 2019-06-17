package com.kios.app.agw.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private Long orgId;
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	//	@ElementCollection
//	private Set<Long> orgIds = new HashSet<Long>();
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
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
//	public Set<Long> getOrgIds() {
//		return orgIds;
//	}
//	public void setOrgIds(Set<Long> orgIds) {
//		this.orgIds = orgIds;
//	}
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
	@PrePersist()
	public void prePersist() {
		createdDate = LocalDateTime.now();
	}
	@PreUpdate()
	public void preUpdate() {
		modifiedDate = LocalDateTime.now();
	}
//	public Collection<Role> getRoles() {
//		return roles;
//	}
//	public void setRoles(Collection<Role> roles) {
//		this.roles = roles;
//	}
}
