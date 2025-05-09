package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

import java.util.List;

public class CommentOnExpensePrompt extends Prompt {
    public CommentOnExpensePrompt(Transaction transaction, List<CategorySummaryResult> categorySummaryResults) {
        super("""
                Hãy đưa ra nhận xét / lời khuyên / cảnh cáo đối với giao dịch mới nhất của người dùng với nội dung '%s' và số tiền là %s VND.
                Hãy dựa vào các tình hình các danh mục chi tiêu hiện tại để đưa ra nhận xét:
                %s
                Hãy trả lời với phong cách thân thiện, có thể pha chút hài hước, cà khịa.
                Trả về duy nhất nội dung nhận xét dành cho người dùng, không gồm bất cứ thứ gì khác.
                """.formatted(
                transaction.getDescription(),
                transaction.getAmount(),
                categorySummaryResults.stream()
                        .map(categorySummaryResult -> """
                                - Danh mục '%s': tổng %.0f VND
                                """.formatted(
                                categorySummaryResult.getCategoryName(),
                                categorySummaryResult.getTotalAmount().doubleValue()
                        ))
                        .reduce("", String::concat)
        ));
    }
}
