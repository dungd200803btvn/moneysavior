package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import soict.hedspi.itss2.gyatto.moneysavior.common.enums.TransactionType;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.CategorySummaryResult;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class CommentOnTransactionPrompt extends Prompt {
    public CommentOnTransactionPrompt(Transaction transaction, BigDecimal totalIncome, List<CategorySummaryResult> categorySummaryResults) {
        super("""
                Hãy đưa ra nhận xét / lời khuyên / cảnh cáo / lời khen động viên đối với giao dịch %s mới nhất của người dùng với nội dung '%s' và số tiền là %s VND.
                Hãy dựa vào tổng thu nhập và tình hình các danh mục chi tiêu hiện tại để đưa ra nhận xét:
                - Tổng thu nhập %.0f VND.
                - Chi tiêu:
                %s
                Hãy trả lời với phong cách thân thiện, có thể pha chút hài hước, cà khịa.
                Trả về duy nhất nội dung nhận xét dành cho người dùng, không gồm bất cứ thứ gì khác.
                """.formatted(
                transaction.getType() == TransactionType.EXPENSE ? "chi tiêu" : "thu nhập",
                transaction.getDescription(),
                transaction.getAmount(),
                totalIncome,
                categorySummaryResults.stream()
                        .map(categorySummaryResult -> """
                                 + Danh mục '%s': tổng %.0f VND
                                """.formatted(
                                categorySummaryResult.getCategoryName(),
                                categorySummaryResult.getTotalAmount().doubleValue()
                        ))
                        .reduce("", String::concat)
        ));
    }
}
