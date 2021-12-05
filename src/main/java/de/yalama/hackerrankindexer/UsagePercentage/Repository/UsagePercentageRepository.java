package de.yalama.hackerrankindexer.UsagePercentage.Repository;

import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsagePercentageRepository extends JpaRepository<UsagePercentage, Long> {
}
