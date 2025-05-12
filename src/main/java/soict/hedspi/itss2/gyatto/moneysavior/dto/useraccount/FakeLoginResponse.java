package soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FakeLoginResponse {
    private String userUuid;
    private String fullName;
}
