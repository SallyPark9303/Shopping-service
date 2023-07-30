package shoppingproject.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import shoppingproject.shop.domain.Member;

@Transactional(readOnly = true)
public interface MemberFormRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByLoginId(String email);

}
