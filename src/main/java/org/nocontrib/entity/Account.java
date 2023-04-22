package org.nocontrib.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "account")
@Data
public class Account {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String username;
  private String password;

  private String name;
  private String email;

  private Date created = new Date();

  @Enumerated(EnumType.STRING)
  private Status status = Status.ENABLED;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "account_role",
      joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
  private List<Role> roleList;
}
