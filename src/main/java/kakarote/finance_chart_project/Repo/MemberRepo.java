package kakarote.finance_chart_project.Repo;

import kakarote.finance_chart_project.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<Member,Long> {
    Member findByUsername(String username);
}
