package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import java.util.List;

public class RecordTransactionPrompt extends Prompt {
    public RecordTransactionPrompt(String message, List<String> categories) {
        super("""
                Hãy phân tích message ghi nội dung thu/chi sau: '%s' xem là thu nhập hay chi tiêu và trả về duy nhất không gì ngoài 1 đối tượng json gồm các trường:
                type: string ('INCOME' nếu là thu nhập, 'EXPENSE' nếu là chi tiêu hoặc 'UNDEFINED' nếu không liên quan hoặc không phân loại được)
                category: string (nếu là chi tiêu thì phân loại và chọn 1 trong các danh mục '%s', còn nếu là thu nhập thì để null)
                description: string (tóm tắt mục đích chi nếu là chi tiêu, hoặc nguồn thu nếu là thu nhập)
                amount: number (số tiền)
                Chú ý trong nội dung message thường sẽ có mục đích tiêu, số tiền tiêu và đơn vị có thể là k (10^3), lít (10^5), tr/củ (10^6), tỉ/tỏi (10^9), hoặc các đơn vị trong tiếng Việt tương tự. Hãy trả về đúng format json vì nếu không ứng dụng của tôi sẽ bị lỗi."""
                .formatted(message, String.join(", ", categories)));
    }
}
