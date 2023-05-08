package com.infowise.demo.dto;

import com.infowise.demo.Enum.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record InfoWisePrincipal(
        Long idx,
        String email,
        String pw,
        String name,
        String team,
        String hp,
        Collection<? extends GrantedAuthority> authorities,
        RoleType roleType
) implements UserDetails {

    public static  InfoWisePrincipal ofUser(Long idx,
                                        String email,
                                        String pw,
                                        String name,
                                        String team,
                                        String hp,
                                        RoleType roleType){
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        return new InfoWisePrincipal(idx, email, pw, name, team, hp,
                roleTypes.stream().map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()), roleType
        );
    }
    public static  InfoWisePrincipal ofManager(Long idx,
                                            String email,
                                            String pw,
                                            String name,
                                            String team,
                                            String hp,
                                            RoleType roleType){
        Set<RoleType> roleTypes = Set.of(RoleType.USER,RoleType.MANAGER);
        return new InfoWisePrincipal(idx, email, pw, name, team, hp,
                roleTypes.stream().map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()), roleType
        );
    }
    public static InfoWisePrincipal fromDTO(MemberDTO dto){
        if(dto.roleType()==RoleType.USER) {
            return InfoWisePrincipal.ofUser(
                    dto.idx(), dto.email(), dto.pw(),
                    dto.name(), dto.team(), dto.hp(), dto.roleType());
        }else{
            return  InfoWisePrincipal.ofManager(
                    dto.idx(), dto.email(), dto.pw(),
                    dto.name(), dto.team(), dto.hp(), dto.roleType());
        }
    }

    public MemberDTO toDTO(){
        return MemberDTO.of(idx, email, pw, name,team, hp, roleType);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
