package com.baziuk.spring.user.data.inmemory.dao;

import com.baziuk.spring.data.JSONDataPopulator;
import com.baziuk.spring.user.bean.User;
import com.baziuk.spring.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simple User DAO. Allows only GET operations!
 *
 * @author Maks
 */
@Repository("userDAO")
public class UserInMemoryDAO implements UserDAO {

    private static long currentMaxId = 0;

    private List<User> users = new ArrayList<>();

    @Autowired
    @Qualifier("userJSONDataPopulator")
    private JSONDataPopulator dataPopulator;

    @Override
    public User create(User item) {
        item.setId(++currentMaxId);
        users.add(item);
        return item;
    }

    @Override
    public User update(User item) {
        //Doing nothing, we don't have persisting logic
        // Good would be do copy of the object and persist it,
        // but I am too lazy to do it in educational project :)
        return item;
    }

    @Override
    public boolean remove(User item) {
        users.remove(item);
        return true;
    }

    @Override
    public User get(long id) {
        Optional<User> result = users.stream().filter(user -> user.getId() == id).findFirst();
        return result.orElse(null);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @PostConstruct
    private void initWithData() throws IOException {
        List<User> data = dataPopulator.getData(new User[0].getClass());
        data.forEach(cur -> {
            if (currentMaxId <= cur.getId()){
                currentMaxId = cur.getId();
            }
            if (cur.getBoughtTickets() == null){
                cur.setBoughtTickets(new ArrayList<>());
            }
            users.add(cur);
        });
    }

    public void setDataPopulator(JSONDataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
    }
}
