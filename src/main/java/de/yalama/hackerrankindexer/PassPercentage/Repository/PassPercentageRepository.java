package de.yalama.hackerrankindexer.PassPercentage.Repository;

import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassPercentageRepository extends JpaRepository<PassPercentage, Long> {
}
