package com.geektext.geektext_api.controller;

import com.geektext.geektext_api.model.User;
import com.geektext.geektext_api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.geektext.geektext_api.model.CreditCard;
import com.geektext.geektext_api.repository.CreditCardRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final CreditCardRepository creditCardRepository;

    public UserController(UserRepository userRepository, CreditCardRepository creditCardRepository) {

        this.userRepository = userRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable String username, @RequestBody User updatedUser) {

        return userRepository.findByUsername(username)
                .map(user -> {
                    if (updatedUser.getName() != null) {
                        user.setName(updatedUser.getName());
                    }

                    if (updatedUser.getPassword() != null) {
                        user.setPassword(updatedUser.getPassword());
                    }

                    if (updatedUser.getHomeAddress() != null) {
                        user.setHomeAddress(updatedUser.getHomeAddress());
                    }

                    userRepository.save(user);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{username}/credit-cards")
    public ResponseEntity<Void> createCreditCard(
            @PathVariable String username,
            @RequestBody CreditCard creditCard) {

        return userRepository.findByUsername(username)
                .map(user -> {
                    creditCard.setUser(user);
                    creditCardRepository.save(creditCard);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
