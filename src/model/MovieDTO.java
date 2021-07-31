package model;

// 영화 모델
public class MovieDTO {

    // 영화 번호
    private int id;
    // 영화 제목
    private String title;
    // 감독
    private String director;
    // 장르
    private String genre;
    // 줄거리
    private String summary;
    // 등급
    private int rank;

    // 겟터셋터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // equals()
    public boolean equals(Object o) {
        if (o instanceof MovieDTO) {
            MovieDTO m = (MovieDTO) o;
            if (id == m.id) {
                return true;
            }
        }
        return false;
    }
}
