package soict.hedspi.itss2.gyatto.moneysavior.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity(name = "expense_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
