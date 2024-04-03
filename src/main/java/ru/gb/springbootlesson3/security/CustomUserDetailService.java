package ru.gb.springbootlesson3.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.springbootlesson3.entity.Person;
import ru.gb.springbootlesson3.repository.PersonRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final PersonRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("ищем " + username);
        Person person = repository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("Пользователь " + username + " не найден"));
        System.out.println("нашли " + person);
        return new User(person.getLogin(), person.getPassword(), List.of(
                new SimpleGrantedAuthority(person.getRole())
        ));
    }
}