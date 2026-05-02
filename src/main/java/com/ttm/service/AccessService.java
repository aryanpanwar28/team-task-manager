package com.ttm.service;

import com.ttm.entity.Project;
import com.ttm.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AccessService {

    public boolean isAdmin(User u) { return u.getRole() == User.Role.ADMIN; }

    public boolean isMember(Project p, User u) {
        if (p.getOwner().getId().equals(u.getId())) return true;
        return p.getMembers().stream().anyMatch(m -> m.getId().equals(u.getId()));
    }

    public void requireMember(Project p, User u) {
        if (!isAdmin(u) && !isMember(p, u)) throw new AccessDeniedException("Not a project member");
    }

    public void requireOwnerOrAdmin(Project p, User u) {
        if (!isAdmin(u) && !p.getOwner().getId().equals(u.getId()))
            throw new AccessDeniedException("Owner or admin only");
    }
}
