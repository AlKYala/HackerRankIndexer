package de.yalama.hackerrankindexer.GeneralPercentage.Repository;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralPercentageRepository extends JpaRepository<GeneralPercentage, Long> {
}