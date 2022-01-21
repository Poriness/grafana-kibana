package com.agh.bulkazbananem.usersservice.service;

import com.agh.bulkazbananem.usersservice.dto.UserDto;
import com.agh.bulkazbananem.usersservice.model.Client;
import com.agh.bulkazbananem.usersservice.model.Dietitian;
import com.agh.bulkazbananem.usersservice.model.RoleOfUser;
import com.agh.bulkazbananem.usersservice.model.StateOfUser;
import com.agh.bulkazbananem.usersservice.model.User;
import com.agh.bulkazbananem.usersservice.repository.ClientRepository;
import com.agh.bulkazbananem.usersservice.repository.DietitianRepository;
import com.agh.bulkazbananem.usersservice.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DietitianRepository dietitianRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public void activateClient(String username) {
        Optional<User> userToActivate = userRepository.findByUsername(username);
        if (userToActivate.isPresent()) {
            userToActivate.get().setState(StateOfUser.ACTIVE);
            userRepository.save(userToActivate.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
    }

    public void register(UserDto userDto) throws Exception {
        switch (userDto.getRole()) {
            case CLIENT:
                registerClient(userDto, StateOfUser.DRAFT);
                break;
            case DIETITIAN:
                registerDietitian(userDto);
                break;
            case CUSTOMERSERVICE:
            case ADMIN:
                registerUser(userDto);
                break;
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    private void registerUser(UserDto userDto) throws Exception {
        if (existsByUsername(userDto.getUsername())) throw new Exception("The user exists");
        User user = new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                encodePassword(userDto.getPassword()),
                userDto.getRole(),
                StateOfUser.ACTIVE
        );
        userRepository.save(user);
    }

    public void registerDietitian(UserDto userDto) throws Exception {
        if (existsByUsername(userDto.getUsername())) throw new Exception("The user exists");
        Dietitian dietitian = new Dietitian(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                encodePassword(userDto.getPassword()),
                RoleOfUser.DIETITIAN,
                StateOfUser.ACTIVE
        );
        dietitianRepository.save(dietitian);
    }

    public void registerClient(UserDto userDto, StateOfUser state) throws Exception {
        if (existsByUsername(userDto.getUsername())) throw new Exception("The user exists");
        Client client = new Client(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getUsername(),
                encodePassword(userDto.getPassword()),
                RoleOfUser.CLIENT,
                state
        );
        clientRepository.save(client);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public boolean isClientAssignToDietitian(int clientId, int dietitianId) {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            return false;
        }
        Optional<Dietitian> dietitian = dietitianRepository.findById(dietitianId);
        if (dietitian.isEmpty()) {
            return false;
        }
        return client.get().getDietitian().equals(dietitian.get());
    }
}
