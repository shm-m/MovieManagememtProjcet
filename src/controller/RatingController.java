package controller;

import java.util.ArrayList;

import model.RatingDTO;

public class RatingController {
    private ArrayList<RatingDTO> list;
    private int nextId;

    public RatingController() {
        list = new ArrayList<>();
        nextId = 1;
        RatingDTO r = new RatingDTO();
        
        // 영화 1은 일반 관람객 평점만
        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(1);
        r.setWriterId(3);
        r.setRating(4);
        list.add(r);

        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(1);
        r.setWriterId(4);
        r.setRating(5);
        list.add(r);

        // 영화 2는 평론가 평점만
        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(2);
        r.setWriterId(1);
        r.setRating(3);
        r.setReview("이토록 값비싸고 희한한 사모곡.");
        list.add(r);

        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(2);
        r.setWriterId(2);
        r.setRating(2);
        r.setReview("곱빼기 먹고 설사하는 기분");
        list.add(r);

        // 3은 둘다
        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(3);
        r.setWriterId(1);
        r.setRating(5);
        r.setReview("치밀한 덫과 강력한 도끼를 함께 갖춘 괴력의 영화.");
        list.add(r);

        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(3);
        r.setWriterId(4);
        r.setRating(4);
        list.add(r);

        r = new RatingDTO();
        r.setId(nextId++);
        r.setMovieId(3);
        r.setWriterId(5);
        r.setRating(5);
        list.add(r);

    }

    // selectOne
    public RatingDTO selectOne(int id) {
        for (RatingDTO r : list) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    // selectAll
    public ArrayList<RatingDTO> selectAll() {
        return list;
    }

    // selectAllByMovie
    public ArrayList<RatingDTO> selectAllByMovie(int movieId) {
        ArrayList<RatingDTO> tmp = new ArrayList<>();
        for (RatingDTO r : list) {
            if (r.getMovieId() == movieId) {
                tmp.add(r);
            }
        }
        return tmp;
    }

    // size
    public int size(int movieId) {
        ArrayList<RatingDTO> tmp = selectAllByMovie(movieId);
        return tmp.size();
    }

    public int size() {
        return list.size();
    }
    
    // insert
    public void insert(RatingDTO r) {
        r.setId(nextId++);
        list.add(r);
    }

}
