package soict.hedspi.itss2.gyatto.moneysavior.service;

import soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount.FakeLoginRequest;
import soict.hedspi.itss2.gyatto.moneysavior.dto.useraccount.FakeLoginResponse;

public interface UserAccountService {
    FakeLoginResponse fakeLogin(FakeLoginRequest request);
}
