package model;

public class RatingDTO {

    // 평점 번호
    private int id;

    // 작성자 번호
    private int writerId;

    // 영화 번호
    private int movieId;

    // 평점
    private int rating;

    // 평론
    private String review;

    // 겟터셋터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    //equals()
    public boolean equals(Object o) {
        if (o instanceof RatingDTO) {
            RatingDTO r = (RatingDTO) o;
            if (id == r.id) {
                return true;
            }
        }
        return false;
    }
}
