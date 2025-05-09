package soict.hedspi.itss2.gyatto.moneysavior.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private String email;

    private String fullName;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
