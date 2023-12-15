package com.example.mongo_db.Security;


import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ClientDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private ClientsService clientsService;

    public ClientDetailsService(ClientsService clientsService) {
        this.clientsService = clientsService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client clientByUserName = clientsService.findClientByUserName(username);
        if (clientByUserName == null) {
            throw new UsernameNotFoundException("user with " + username + " not found");
        } else
            return new User(clientByUserName.getClient_user_name(), clientByUserName.getClient_password(), grantedAuthority(clientByUserName.getRole().toString()));
    }

    public Set<GrantedAuthority> grantedAuthority(String role) {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return grantedAuthorities;
    }


    public void authenticateClient(Client client,HttpServletRequest request) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(client, client.getClient_user_name(), grantedAuthority(client.getRole().toString()));
        securityContext.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }

}
