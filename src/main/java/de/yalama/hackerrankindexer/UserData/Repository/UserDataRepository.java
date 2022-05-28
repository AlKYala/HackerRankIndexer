package de.yalama.hackerrankindexer.UserData.Repository;

import de.yalama.hackerrankindexer.UserData.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

    @Query(value = "select ud FROM UserData ud where ud.user.id in (:userId)")
    public List<UserData> getByUser(Long userId);

    @Query(value = "select ud FROM UserData ud where ud.token = :token")
    public UserData getByUserDataToken(String token);
}
