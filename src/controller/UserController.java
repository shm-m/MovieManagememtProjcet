package controller;

import java.util.ArrayList;
import model.UserDTO;

public class UserController {
    private ArrayList<UserDTO> list;
    private int nextId = 0;
    private final int CATEGORY_ADMIN = 1;
    private final int CATEGORY_REVIEWER = 2;
    private final int CATEGORY_GENERAL = 3;

    public UserController() {
        // 관리자
        list = new ArrayList<>();
        UserDTO u = new UserDTO();
        u.setId(nextId++);
        u.setUsername("admin");
        u.setPassword("123");
        u.setNickname("관리자");
        u.setCategory(CATEGORY_ADMIN);
        list.add(u);
        
        // 평론가
        u = new UserDTO();
        u.setId(nextId++);
        // 아이디 1
        u.setUsername("r" + 1);
        u.setPassword("123");
        u.setNickname("짭동진");
        u.setCategory(CATEGORY_REVIEWER);
        list.add(u);
        
        u = new UserDTO();
        u.setId(nextId++);
        u.setUsername("r" + 2);
        u.setPassword("123");
        u.setNickname("짭평식");
        u.setCategory(CATEGORY_REVIEWER);
        list.add(u);

        // 일반 회원
        for (int i = 0; i < 3; i++) {
            u = new UserDTO();
            u.setId(nextId++);
            u.setUsername("g" + i);
            u.setPassword("123");
            u.setNickname("사용자" + i);
            u.setCategory(CATEGORY_GENERAL);
            list.add(u);
        }
    }

    // selectAll
    public ArrayList<UserDTO> selectAll() {
        return list;
    }

    // selectOne
    public UserDTO selectOne(int id) {
        for (UserDTO u : list) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // insert
    public void insert(UserDTO u) {
        u.setId(nextId++);
        u.setCategory(CATEGORY_GENERAL);
        list.add(u);
    }

    // update
    public void update(UserDTO u) {
        int index = list.indexOf(u);
        if (index != -1) {
            list.set(index, u);
        }
    }

    // delete
    public void delete(int id) {
        UserDTO u = new UserDTO();
        u.setId(id);
        list.remove(list.indexOf(u));
    }

    // username 중복 검사 
    public boolean validateUsername(String username) {
        for (UserDTO u : list) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // 로그인 확인 메소드
    public UserDTO auth(String username, String password) {
        for (UserDTO u : list) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }
}
