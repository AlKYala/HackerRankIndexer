package de.yalama.hackerrankindexer.Session.Service;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TODO das verteilen der sessionIDs klappt nichtmp

@Service
@Slf4j
@NoArgsConstructor
public class SessionServiceImpl extends SessionService {

    @Override
    public String getCurrentSessionId(HttpSession httpSession) {
        log.info(httpSession.getId());
        return httpSession.getId();
    }
}
