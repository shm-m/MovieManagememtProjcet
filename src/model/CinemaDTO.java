package model;
// 극장 모델
public class CinemaDTO {

    // 극장 번호
    private int id;
    // 극장 이름
    private String cinemaName;
    // 극장 위치
    private String cinemaLocation;
    // 극장 전화번호
    private int cinemaNumber;

    // 겟터셋터
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaLocation() {
        return cinemaLocation;
    }

    public void setCinemaLocation(String cinemaLocation) {
        this.cinemaLocation = cinemaLocation;
    }

    public int getCinemaNumber() {
        return cinemaNumber;
    }

    public void setCinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }
    
    // equals()
    public boolean equals(Object o) {
        if (o instanceof CinemaDTO) {
            CinemaDTO t = (CinemaDTO) o;
            if (id == t.id) {
                return true;
            }
        }
        return false;
    }

}
