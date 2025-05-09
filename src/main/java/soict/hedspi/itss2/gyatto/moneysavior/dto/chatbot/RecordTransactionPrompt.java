package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

import java.util.List;

public class RecordTransactionPrompt extends Prompt {
    public RecordTransactionPrompt(String message, List<String> categories) {
        super("""
                Hãy phân loại message sau: '%s' vào một trong các danh mục chi tiêu sau: '%s', 
                và trả về duy nhất không gì khác ngoài 1 đối tượng json có dạng: {type:'EXPENSE',category:'tên danh mục',amount:số tiền dưới dạng number}. 
                Chú ý trong nội dung message thường sẽ có mục đích tiêu, số tiền tiêu và đơn vị có thể là k (10^3), lít (10^5), tr/củ (10^6), tỉ/tỏi (10^9), hoặc các đơn vị trong tiếng Việt tương tự. 
                Nếu nội dung không liên quan hoặc không phân loại được thì trả về {type:'UNDEFINED'}
                """.formatted(message, String.join(", ", categories)));
    }
}
