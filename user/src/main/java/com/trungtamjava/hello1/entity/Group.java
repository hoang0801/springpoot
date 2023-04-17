package com.trungtamjava.hello1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "groups_of_user")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany
    @JoinTable(name ="user_group", joinColumns = @JoinColumn(name="group_id"), inverseJoinColumns = @JoinColumn(name = ("category_id")))
    List<User> users;
}
