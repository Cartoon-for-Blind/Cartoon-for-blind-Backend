package hongik.cartoonforblindbackend.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User_social")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userSocialId;

  @Column(nullable = false)
  private String provider;

  @Column(name = "provider_id", nullable = false, unique = true)
  private String providerId;

  private String accessToken;

  private String refreshToken;

  @Column(name = "token_expires_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date tokenExpiresAt;

  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

//  @ManyToOne
//  @JoinColumn(name = "user_id", nullable = false)
//  private User user;
}
