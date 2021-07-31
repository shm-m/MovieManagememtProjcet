package model;

import java.util.Calendar;

public class InfoDTO {

    // 상영정보 번호
    private int id;
    // 영화 번호
    private int movieId;
    // 영화관 번호
    private int cinemaId;
    // 상영시간
    private Calendar showTime;

    // 겟터셋터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int theaterId) {
        this.cinemaId = theaterId;
    }

    public Calendar getShowTime() {
        return showTime;
    }

    public void setShowTime(Calendar showTime) {
        this.showTime = showTime;
    }

    // equals()
    public boolean equals(Object o) {
        if (o instanceof InfoDTO) {
            InfoDTO i = (InfoDTO) o;
            if (id == i.id) {
                return true;
            }
        }
        return false;
    }
}
