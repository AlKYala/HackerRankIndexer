package de.yalama.hackerrankindexer.Session.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public long getFreeSessionId() {
        this.cycleCurrentIdUntilVacant();
        this.usedSessionIds.add(this.currentId);
        return this.currentId;
    }

    @Override
    public void retireId(long id) {
        this.usedSessionIds.remove(id);
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
