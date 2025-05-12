package soict.hedspi.itss2.gyatto.moneysavior.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount.FakeLoginRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount.FakeLoginResponse;
import soict.hedspi.itss2.gyatto.moneysavior.entity.UserAccount;
import soict.hedspi.itss2.gyatto.moneysavior.repository.UserAccountRepository;
import soict.hedspi.itss2.gyatto.moneysavior.service.UserAccountService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public FakeLoginResponse fakeLogin(FakeLoginRequest request) {
        var user = userAccountRepository.findFirstByEmail(request.getEmail());
        if (user == null) {
            user = UserAccount.builder()
                    .email(request.getEmail())
                    .fullName("Người dùng mới")
                    .build();
            userAccountRepository.save(user);
        }
        return FakeLoginResponse.builder()
                .userUuid(user.getUuid())
                .build();
    }
}
