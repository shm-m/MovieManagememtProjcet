package model;
// 회원 모델
public class UserDTO {

    // 회원 번호
    private int id;
    // 아이디
    private String username;
    // 비밀번호
    private String password;
    
    // 닉네임
    private String nickname;
    
    // 등급 
    private int Category;

    
    // 겟터/셋터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }
    
    // equals()
    public boolean equals(Object o) {
        if (o instanceof UserDTO) {
            UserDTO u = (UserDTO) o;
            if(id == u.id) {
                return true;
            }
        }
        return false;
    }
}
