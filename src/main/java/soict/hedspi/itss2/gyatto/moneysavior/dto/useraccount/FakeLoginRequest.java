package soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class FakeLoginRequest {
    @NotEmpty
    @Email
    private String email;
}
