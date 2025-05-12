package soict.hedspi.itss2.gyatto.moneysavior.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private String userUuid;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String description;

    private BigDecimal amount;

    @Builder.Default
    private String currency = "VND";

    @Builder.Default
    private LocalDate date = LocalDate.now();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;
}
