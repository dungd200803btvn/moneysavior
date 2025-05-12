package soict.hedspi.itss2.gyatto.moneysavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findFirstByUserUuidOrderByTimestampDesc(String userUuid);

    @Query("""
            SELECT t.category.name AS categoryName,
                   SUM(t.amount) AS totalAmount,
                   SUM(t.amount) / (SELECT SUM(t2.amount) FROM Transaction t2 WHERE t2.userUuid = :userUuid AND t2.type = 'EXPENSE' AND CAST(t2.timestamp AS DATE) BETWEEN :startDate AND :endDate) * 100 AS percentage
            FROM Transaction t
            WHERE t.userUuid = :userUuid AND t.type = 'EXPENSE' AND CAST(t.timestamp AS DATE) BETWEEN :startDate AND :endDate
            GROUP BY t.category.name
            HAVING sum(t.amount) > 0
            """)
    List<CategorySummaryResult> findCategorySummaryByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT SUM(t.amount)
            FROM Transaction t
            WHERE t.userUuid = :userUuid AND t.type = 'INCOME' AND CAST(t.timestamp AS DATE) BETWEEN :startDate AND :endDate
            """)
    BigDecimal findTotalIncomeByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);
}
