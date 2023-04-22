package org.nocontrib.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
@Data
public class Role {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String name;

  private Date created = new Date();

  @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
  private List<Account> accountList;

  @Override
  public String toString() {
    return "Role{" + "id=" + id + ", name='" + name + '\'' + ", created=" + created + '}';
  }
}
