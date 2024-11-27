package hongik.cartoonforblindbackend.domain.user.entity;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false,unique = true)
  private String username;

  @Column(nullable = true)
  private String password;

  private String profileImage;

  @Column(name = "created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @OneToMany(mappedBy = "user")
  private List<Book> books;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRoleEnum role;

  //  @OneToMany(mappedBy = "user")
//  private List<UserSocial> userSocials;
}
