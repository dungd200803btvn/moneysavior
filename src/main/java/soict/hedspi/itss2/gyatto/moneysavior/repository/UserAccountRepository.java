package soict.hedspi.itss2.gyatto.moneysavior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soict.hedspi.itss2.gyatto.moneysavior.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findFirstByEmail(String email);
}
