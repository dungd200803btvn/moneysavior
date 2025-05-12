package soict.hedspi.itss2.gyatto.moneysavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.dto.report.OverviewResult;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByUuid(String uuid);

    Transaction findFirstByUserUuidOrderByCreatedAtDesc(String userUuid);

    @Query("""
            SELECT t.category.name AS categoryName,
                   SUM(t.amount) AS totalAmount,
                   SUM(t.amount) / (SELECT SUM(t2.amount) FROM Transaction t2 WHERE t2.userUuid = :userUuid AND t2.type = 'EXPENSE' AND t2.date BETWEEN :startDate AND :endDate) * 100 AS percentage
            FROM Transaction t
            WHERE t.userUuid = :userUuid AND t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate
            GROUP BY t.category.name
            HAVING sum(t.amount) > 0
            """)
    List<CategorySummaryResult> findCategorySummaryByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT SUM(t.amount)
            FROM Transaction t
            WHERE t.userUuid = :userUuid AND t.type = 'INCOME' AND t.date BETWEEN :startDate AND :endDate
            """)
    BigDecimal findTotalIncomeByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT
                IFNULL((SELECT SUM(t.amount) FROM Transaction t
                WHERE t.userUuid = :userUuid
                    AND t.type = 'INCOME'
                    AND t.date BETWEEN :startDate AND :endDate), 0)
                AS totalIncomes,
                IFNULL((SELECT SUM(t.amount) FROM Transaction t
                WHERE t.userUuid = :userUuid
                    AND t.type = 'EXPENSE'
                    AND t.date BETWEEN :startDate AND :endDate), 0)
                AS totalExpenses
            """)
    OverviewResult findOverviewByUserUuid(String userUuid, LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT t
            FROM Transaction t JOIN t.category c
            WHERE t.userUuid = :userUuid
                AND (:type IS NULL OR t.type = :type)
                AND (:categoryName IS NULL OR c.name = :categoryName)
                AND EXTRACT(YEAR FROM t.date) = :year
                AND EXTRACT(MONTH FROM t.date) = :month
            ORDER BY t.date DESC, t.createdAt DESC
            """)
    List<Transaction> findTransactionHistoryByUser(String userUuid, TransactionType type, String categoryName, int year, int month);
}
