package com.assessment.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.assessment.demo.model.UserInfo;
import com.assessment.demo.repository.UserRepository;
import com.assessment.demo.vo.UserChangePasswordModel;
import com.assessment.demo.vo.UserDisplayInfo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({ "rawtypes" })
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Boolean validatePassword(String password) {
        if (password != null && !(password.isEmpty()) && password.trim().length() >= 8) {
            String regex = "^[a-zA-Z0-9]*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches())
                return true;
        }
        return false;
    }

    private Boolean validateName(String name) {
        if (name != null && !(name.isEmpty()) && name.trim().length() <= 50)
            return true;
        else
            return false;
    }

    private Boolean valdiateEmail(String email) {
        if (email != null && !(email.isEmpty())) {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches())
                return true;
        }
        return false;
    }

    private String getHashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public ResponseEntity createUser(UserInfo user) {
        if (validatePassword(user.getPassword()) && validateName(user.getName()) && valdiateEmail(user.getEmailId())) {
            user.setPassword(getHashedPassword(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<UserDisplayInfo>> getAllUsers() {
        List<UserInfo> allUsersInfo = userRepository.findAll();
        List<UserDisplayInfo> allUsersDisplayInfo = new ArrayList<>();
        for (UserInfo user : allUsersInfo) {
            UserDisplayInfo userDisplayInfo = new UserDisplayInfo();
            BeanUtils.copyProperties(user, userDisplayInfo);
            allUsersDisplayInfo.add(userDisplayInfo);
        }
        return new ResponseEntity<List<UserDisplayInfo>>(allUsersDisplayInfo, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<UserDisplayInfo> getUserById(Long id) {
        Optional<UserInfo> optUserInfo = userRepository.findById(id);
        if (optUserInfo.isPresent()) {
            UserDisplayInfo userDisplayInfo = new UserDisplayInfo();
            BeanUtils.copyProperties(optUserInfo.get(), userDisplayInfo);
            return new ResponseEntity<UserDisplayInfo>(userDisplayInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity updateUserDetails(Long id, UserDisplayInfo userDisplayInfo) {
        Optional<UserInfo> optUserInfo = userRepository.findById(id);
        if (optUserInfo.isPresent()) {
            UserInfo userInfo = optUserInfo.get();
            if (validateName(userDisplayInfo.getName()) && valdiateEmail(userDisplayInfo.getEmailId())) {
                userInfo.setName(userDisplayInfo.getName());
                userInfo.setEmailId(userDisplayInfo.getEmailId());
            } else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            userRepository.save(userInfo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity changePassword(Long id, UserChangePasswordModel userNewPassword) {
        Optional<UserInfo> optUserInfo = userRepository.findById(id);
        if (optUserInfo.isPresent()) {
            UserInfo userinfo = optUserInfo.get();
            if (validatePassword(userNewPassword.getPassword())) {
                userinfo.setPassword(getHashedPassword(userNewPassword.getPassword()));
                userRepository.save(userinfo);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity deleteUser(Long id) {
        Optional<UserInfo> optUserInfo = userRepository.findById(id);
        if (optUserInfo.isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
