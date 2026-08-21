import java.util.Optional;

public interface AccountRepository {
    void save(Account account); // inserts/updates an account

    Optional<Account> findByAccountNumber(String accountNumber);

    Account[] findAll();

    boolean deleteByAccountNumber(String accountNumber);

}
