package com.trungtamjava.hello1.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String avatar; //URl

    @Column(unique = true)
    private String username;
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

//    @Transient //field is not persistent
//    private MultipartFile file;

    @CreatedDate //tu gen
//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;

//    @ElementCollection
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id" ))
//    @Column(name = "role")
//	private List<String> roles;

    // khong bat buoc
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Group> groups;






}
