package com.example.books.service.user;

import com.example.books.config.SecurityConfig;
import com.example.books.dto.user.UserRegistrationRequestDto;
import com.example.books.dto.user.UserResponseDto;
import com.example.books.exception.RegistrationException;
import com.example.books.mapper.UserMapper;
import com.example.books.model.Role;
import com.example.books.model.RoleName;
import com.example.books.model.ShoppingCart;
import com.example.books.model.User;
import com.example.books.repository.role.RoleRepository;
import com.example.books.repository.shoppingcart.ShoppingCartRepository;
import com.example.books.repository.user.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final SecurityConfig config;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists!");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(config.getPasswordEncoder().encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RegistrationException("There is no role for this user!"));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toUserResponse(savedUser);
    }
}
