package com.spring.cvgenerator.services;

import com.spring.cvgenerator.dto.DataObject;
import com.spring.cvgenerator.dto.filter.UserFilter;
import com.spring.cvgenerator.model.AppUser;
import com.spring.cvgenerator.model.Role;
import com.spring.cvgenerator.model.util.UserDetails;
import com.spring.cvgenerator.model.util.UserSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@PropertySource(value = "classpath:messages.properties")
public class UserService extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Long ROLE_USER = 2L;

    public AppUser saveUser(AppUser user) {
        logger.info("-------------Trying to saveUser: {}", user);

        try {
            UserDetails userDetails = user.getUserDetails();
            UserSecurity userSecurity = user.getUserSecurity();

            if (userSecurity.getRole() != null) {
                userSecurity.setRoles(Collections.singletonList(
                        roleRepo.findById(userSecurity.getRole().getId()).orElseThrow(() ->
                                new IllegalStateException("Role not found!"))
                ));
            } else {
                Role roleUser = roleRepo.findById(ROLE_USER).orElseThrow(() ->
                        new IllegalStateException("Cannot find user role with id = " + ROLE_USER));
                userSecurity.setRoles(Collections.singletonList(roleUser));
            }

            userDetails.setGender(genderRepo.find(userDetails.getGender().getId()));

            this.userRepository.persist(user);
            AppUser saved = this.userRepository.findByUsernameOrEmail(userSecurity.getUsername())
                    .orElseThrow(() -> new IllegalStateException("Cannot find user!"));
            logger.info("------------------Successfully saved user: {}", saved);
            return saved;
        } catch (Exception e) {
            logger.error("---------------------Failed to save user: {}, exception: {}", user, e.getMessage());
            throw new IllegalStateException("Unable to save user! " + e);
        }

    }

    public DataObject findAll(UserFilter userFilter) {
        if (userFilter.getOrder() == null || userFilter.getOrder().trim().length() == 0) {
            userFilter.setOrder("username");

            if ("dob".equals(userFilter.getOrder())) {
                userFilter.setIsAsc(!userFilter.getIsAsc());
            }
        }


        final long total = this.userRepository.countAll(userFilter);
        final int pageSize = userFilter.getPageSize();
        final int maxPage = (int) Math.ceil(total / (double) pageSize);
        if (userFilter.getPage() > maxPage) {
            userFilter.setPage(maxPage);
        }
        final int currentPage = userFilter.getPage();
        final List<AppUser> list = this.userRepository.findAll(userFilter);


        return DataObject.builder()
                .totalRecords((long) list.size())
                .list(list)
                .recordsPerPage(pageSize)
                .dataSize(list.size())
                .maxPage(maxPage)
                .currentPage(currentPage)
                .build();
    }

    public AppUser findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(()
                -> new IllegalStateException(String.format("Failed to find user with id %d", id)));
    }

    public AppUser editUser(AppUser actualUser) {
        logger.info("------------------editUser with param: actualUser = {}", actualUser);

        try {

            AppUser oldUser = this.userRepository.findById(actualUser.getId()).orElseThrow(() ->
                    new IllegalStateException(MessageFormat.format("Cannot find user with id {0}", actualUser.getId())));

            updateUserSecurity(oldUser.getUserSecurity(), actualUser.getUserSecurity());
            updateUserDetails(oldUser.getUserDetails(), actualUser.getUserDetails());

            return this.userRepository.merge(oldUser);
        } catch (Exception e) {
            logger.error("---------------------Cannot update user with id {}, errorMessage: {}", actualUser.getId(), e.getMessage());
            throw new IllegalStateException(String.format("Cannot update user with id: %d! Error: %S", actualUser.getId(), e));
        }
    }

    private void updateUserSecurity(UserSecurity old, UserSecurity actual) {
        logger.info("----------------------updateUserSecurity with params: old = {}; actual = {}", old, actual);

        try {
            if (actual.getEmail() != null) {
                old.setEmail(actual.getEmail());
            }
            if (actual.getUsername() != null && !actual.getUsername().equals(old.getUsername())) {
                old.setUsername(actual.getUsername());
            }
            if (actual.getPassword() != null) {
                old.setPassword(actual.getPassword());
            }
            if (actual.getRoles() != null && !actual.getRoles().isEmpty()) {
                old.setRoles(actual.getRoles().stream()
                        .map(r -> roleRepo.findById(r.getId()).orElseThrow(() -> new IllegalStateException("Role not found!")))
                        .collect(Collectors.toList()));
            }
            if (actual.getRole() != null) {
                List<Role> roles = new LinkedList<>();
                roles.add(roleRepo.findById(actual.getRole().getId()).orElseThrow(() ->
                        new IllegalStateException(MessageFormat
                                .format("Cannot find role with id {0}", actual.getRole().getId()))));
                old.setRoles(roles);
            }
            if (actual.getIsEnabled() != null) {
                old.setIsEnabled(actual.getIsEnabled());
            }
        } catch (Exception e) {
            logger.error("---------------------Unable to updateUserSecurity! errorMessage = {}", e.getMessage());
            throw new IllegalStateException("----------------------------Unable to update userSecurity " + e);
        }
    }

    private void updateUserDetails(UserDetails old, UserDetails actual) {
        logger.info("----------------------updateUserDetails with params: old = {}; actual = {}", old, actual);

        try {
            if (actual.getFirstName() != null) {
                old.setFirstName(actual.getFirstName());
            }
            if (actual.getLastName() != null) {
                old.setLastName(actual.getLastName());
            }
            if (actual.getGender() != null) {
                old.setGender(this.genderRepo.findById(actual.getGender().getId()).orElseThrow(() -> new IllegalStateException("Gender not found!")));
            }
            if (actual.getDob() != null) {
                old.setDob(actual.getDob());
            }
        } catch (Exception e) {
            logger.error("-----------------------Unable to updateUserDetails, errorMessage = {}", e.getMessage());
            throw new IllegalStateException("Unable to updateUserDetails " + e);
        }
    }

    public void removeUser(long id) {
        Optional<AppUser> foundUser = this.userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot find user with id %d", id));
        }
        this.userRepository.delete(foundUser.get());
    }

    public void removeAllInactive(){
        this.userRepository.deleteAllInactive();
    }

    public AppUser toggleEnabled(Long userId) {
        AppUser foundUser = this.findById(userId);
        foundUser.getUserSecurity().setIsEnabled(!foundUser.getUserSecurity().getIsEnabled());
        return this.userRepository.merge(foundUser);
    }

}


