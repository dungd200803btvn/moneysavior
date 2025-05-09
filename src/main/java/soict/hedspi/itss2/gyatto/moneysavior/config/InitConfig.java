package soict.hedspi.itss2.gyatto.moneysavior.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import soict.hedspi.itss2.gyatto.moneysavior.entity.ExpenseCategory;
import soict.hedspi.itss2.gyatto.moneysavior.repository.ExpenseCategoryRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitConfig {
    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            initExpenseCategories();
        };
    }

    private void initExpenseCategories() {
        if (expenseCategoryRepository.count() == 0) {
            List<String> categoryNames = List.of("Nhà ở", "Đi lại", "Ăn uống", "Mua sắm", "Giải trí", "Giáo dục", "Sức khỏe", "Khác");
            var result = expenseCategoryRepository.saveAll(
                    categoryNames.stream()
                            .map(name -> new ExpenseCategory(null, name))
                            .toList()
            );
            log.info("Initialized {} expense categories", result.size());
        }
    }
}
