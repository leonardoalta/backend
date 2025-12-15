package com.blacksystem.system.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email_user"}),
        @UniqueConstraint(columnNames = {"phone_user"})
})
public class User implements Serializable, UserDetails {

    @Id
    @Column(name = "id_user")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank(message = "User name is required")
    @Size(min = 2, max = 50, message = "User name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "User name must contain only letters and spaces")
    @Column(name = "name_user", nullable = false, length = 50)
    private String nameUser;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Last name must contain only letters and spaces")
    @Column(name = "last_name_user", nullable = false, length = 50)
    private String lastNameUser;

    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "middle name must contain only letters and spaces")
    @Column(name = "middle_name_user", nullable = false, length = 50)
    private String middleNameUser;

    @Size(max = 100, message = "Email cannot exceed 100 characters")

    @Column(name = "email_user", unique = true, length = 100)
    private String emailUser;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")

    @Column(name = "phone_user", unique = true, length = 10)
    private String phoneUser;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#.])(?=\\S+$).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character (@$!%*?&#.) and no spaces"
    )
    @Column(name = "password_user", nullable = false, length = 255)
    private String passwordUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "address_id_user")
    private Address addressUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    @JsonIgnore
    private Set<Role> rolesUser = new HashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesUser.stream().map(role -> new SimpleGrantedAuthority(role.getNameRole())).collect(Collectors.toList());
    }

    private boolean enabledUser = false;

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabledUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    public String getMiddleNameUser() {
        return middleNameUser;
    }

    public void setMiddleNameUser(String middleNameUser) {
        this.middleNameUser = middleNameUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public Address getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(Address addressUser) {
        this.addressUser = addressUser;
    }

    public Set<Role> getRolesUser() {
        return rolesUser;
    }

    public void setRolesUser(Set<Role> rolesUser) {
        this.rolesUser = rolesUser;
    }

    public boolean isEnabledUser() { return enabledUser; }
    public void setEnabledUser(boolean enabledUser) { this.enabledUser = enabledUser; }

    @Override
    @JsonIgnore
    public String getPassword() {
        return  passwordUser;
    }

    @Override @JsonIgnore
    public String getUsername() {
        return emailUser;
    }

    @Override @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nameUser='" + nameUser + '\'' +
                ", lastNameUser='" + lastNameUser + '\'' +
                ", middleNameUser='" + middleNameUser + '\'' +
                ", emailUser='" + emailUser + '\'' +
                ", phoneUser='" + phoneUser + '\'' +
                ", passwordUser='" + passwordUser + '\'' +
                ", addressUser=" + addressUser +
                ", rolesUser=" + rolesUser +
                ", enabledUser=" + enabledUser +
                '}';
    }
}
