package de.yalama.hackerrankindexer.Session.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class SessionServiceImpl extends SessionService {

    private Set<Long> usedSessionIds;
    private long currentId;

    public SessionServiceImpl() {
        this.usedSessionIds = new HashSet<Long>();
        this.currentId = 0l;
    }

    public Long getCurrentSessionId(HttpServletRequest request) {
        return (request.getSession().getAttribute("sessionId") != null) ?
                (Long) request.getSession().getAttribute("sessionId") :
                this.getFreeSessionId(request);
    }

    @Override
    public long getFreeSessionId(HttpServletRequest request) {
        log.info(request.toString());
        this.cycleCurrentIdUntilVacant();
        this.usedSessionIds.add(this.currentId);
        request.getSession().setAttribute("sessionId", this.currentId); //TODO sessions verwalten
        return this.currentId;
    }

    //Mehr zu sessions: https://www.javainuse.com/spring/springboot_session

    @Override
    public Long retireId(long id) {
        this.usedSessionIds.remove(id);
        return id;
    }

    @Override
    public boolean checkIdExists(long id) {
        return this.usedSessionIds.contains(id);
    }

    private void cycleCurrentIdUntilVacant() {
        while(this.usedSessionIds.contains(this.currentId)) {
            this.currentId++;
        }
    }
}
