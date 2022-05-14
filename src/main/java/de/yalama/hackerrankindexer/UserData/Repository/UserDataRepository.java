package de.yalama.hackerrankindexer.UserData.Repository;

import de.yalama.hackerrankindexer.UserData.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
