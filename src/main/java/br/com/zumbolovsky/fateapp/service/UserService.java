package br.com.zumbolovsky.fateapp.service;

import br.com.zumbolovsky.fateapp.domain.postgres.UserInfo;
import br.com.zumbolovsky.fateapp.domain.postgres.UserInfoRepository;
import jakarta.transaction.Transactional;
import liquibase.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.zumbolovsky.fateapp.config.error.ErrorDebugMessages.USER_ALREADY_SIGNED_IN;
import static br.com.zumbolovsky.fateapp.domain.DefaultRoles.USER;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserInfoRepository userInfoRepository;
    private final MessageSourceAccessor messageSourceAccessor;

    public UserService(UserInfoRepository userInfoRepository, MessageSourceAccessor messageSourceAccessor) {
        this.userInfoRepository = userInfoRepository;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public List<UserInfo> findAllByRole(String role) {
        logger.info("Finding all users by role name: {}...", role);
        return userInfoRepository.findAllByRole(role);
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("Finding user info by user {}...", username);
        return findByUserAndPassword(username);
    }

    public UserDetails findByUserAndPassword(String username) {
        return findByUserAndPassword(username, null);
    }

    @Cacheable("user")
    private UserDetails findByUserAndPassword(String username, String password) {
        return (password != null ?
            userInfoRepository.findOneByUserAndPassword(username, MD5Util.computeMD5(password)) :
            userInfoRepository.findOneByUser(username))
                .orElseThrow(() -> new EntityNotFoundException(List.of(USER, username)));
    }

    @Transactional
    public void signUp(UserInfo userInfo) {
        userInfo.setPassword(MD5Util.computeMD5(userInfo.getPassword()));
        userInfoRepository.findOne(Example.of(userInfo))
            .ifPresentOrElse(existingUser -> {
                throw new EntityExistsException(List.of(USER, messageSourceAccessor.getMessage(USER_ALREADY_SIGNED_IN, new Object[]{existingUser.getUser()})));
            }, () -> userInfoRepository.save(userInfo));
    }
}
