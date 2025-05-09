package soict.hedspi.itss2.gyatto.moneysavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
            SELECT t.category.name AS categoryName,
                   SUM(t.amount) AS totalAmount,
                   SUM(t.amount) / (SELECT SUM(t2.amount) FROM Transaction t2 WHERE t2.userUuid = ?1 AND t2.type = 'EXPENSE' AND CAST(t2.timestamp AS DATE) BETWEEN ?2 AND ?3) * 100 AS percentage
            FROM Transaction t
            WHERE t.userUuid = ?1 AND t.type = 'EXPENSE' AND CAST(t.timestamp AS DATE) BETWEEN ?2 AND ?3
            GROUP BY t.category.name
            HAVING sum(t.amount) > 0
            """)
    List<CategorySummaryResult> findCategorySummaryByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);

    Transaction findFirstByUserUuidOrderByTimestampDesc(String userUuid);
}
