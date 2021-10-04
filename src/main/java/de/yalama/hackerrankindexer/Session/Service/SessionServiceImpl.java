package de.yalama.hackerrankindexer.Session.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

//TODO das verteilen der sessionIDs klappt nicht

@Service
@Slf4j
public class SessionServiceImpl extends SessionService {

    private Set<Long> usedSessionIds;
    private long currentId;

    public SessionServiceImpl() {
        this.usedSessionIds = new HashSet<Long>();
        this.usedSessionIds.add(0l);
        this.currentId = 1l;
    }

    public Long getCurrentSessionId(HttpServletRequest request) {
        log.info("next SessionId: {}\nusedIds: {}", this.currentId, this.usedSessionIds.toString());
        log.info("Request: {}", request.getSession().getAttribute("sessionId"));
        return (this.checkIsSessionIdBad(request)) ?
                (Long) request.getSession().getAttribute("sessionId") :
                this.getFreeSessionId(request);
    }

    //TODO this is never fired!
    @Override
    public long getFreeSessionId(HttpServletRequest request) {

        this.cycleCurrentIdUntilVacant();
        log.info("setting sessionId to: {}", this.currentId);
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
        this.usedSessionIds.add(this.currentId);
    }

    private boolean checkIsSessionIdBad(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("sessionId") == null ||
                ((Long)httpServletRequest.getSession().getAttribute("sessionId")) == 0l;
    }
}
