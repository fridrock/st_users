package ru.evanemo.st_user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String surname;
  @Column(nullable = false, name = "third_name")
  private String thirdName;
  @Column(name = "password_hash",  nullable = false)
  private String passwordHash;
  @ManyToOne
  private Role role;
  @ManyToOne
  private Group group;
  @OneToMany(mappedBy = "teacher")
  private Set<Group> groups;
}