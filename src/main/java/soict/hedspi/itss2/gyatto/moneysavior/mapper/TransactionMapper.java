package soict.hedspi.itss2.gyatto.moneysavior.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import soict.hedspi.itss2.gyatto.moneysavior.dto.transaction.TransactionResponse;
import soict.hedspi.itss2.gyatto.moneysavior.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "category", expression = "java(transaction.getCategory() != null ? transaction.getCategory().getName() : null)")
    TransactionResponse toTransactionResponse(Transaction transaction);
}
