package com.baziuk.spring.common.web.binding;

import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Maks on 11/9/16.
 */
@Component
public class UserDataBinder extends PropertyEditorSupport {

    @Autowired
    private UserService userService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Long userId = Long.parseLong(text);
        User user = userService.getUserById(userId).get();
        setValue(user);
    }

    @Override
    public String getAsText() {
        User user = (User) getValue();
        return Long.toString(user.getId());
    }
}
