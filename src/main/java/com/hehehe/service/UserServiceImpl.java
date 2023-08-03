package com.hehehe.service;

import com.hehehe.exception.BaseException;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.model.entity.UserEntity;
import com.hehehe.model.entity.dto.UserDTO;
import com.hehehe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public void singUp(UserDTO.Request request) {

        UserEntity user = modelMapper.map(request, UserEntity.class);
        userRepository.save(user);
    }

    @Override
    public UserDTO.Response login(UserDTO.Request request) {

        UserEntity user = modelMapper.map(request, UserEntity.class);
        UserEntity findUser = userRepository.findByUserName(user.getUserName());

        if (user.getUserName().equals(findUser.getUserName()) && user.getPassword().equals(findUser.getPassword())){

            return UserDTO.Response.builder()
                    .userId(findUser.getId())
                    .userName(findUser.getUserName())
                    .build();
        } else {
            return null;
        }
    }
}
