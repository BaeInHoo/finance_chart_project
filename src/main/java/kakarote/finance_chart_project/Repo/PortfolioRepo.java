package kakarote.finance_chart_project.Repo;

import kakarote.finance_chart_project.Entity.Portfolio;
import kakarote.finance_chart_project.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {
    List<Portfolio> findByMember(Member member);
}
