package soict.hedspi.itss2.gyatto.moneysavior.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private String userUuid;

    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Column(length = 2000)
    private String message;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public enum Sender {
        USER,
        BOT
    }
}
