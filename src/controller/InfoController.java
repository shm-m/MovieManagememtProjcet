package controller;

import java.util.ArrayList;
import java.util.Calendar;

import model.InfoDTO;

public class InfoController {
    private ArrayList<InfoDTO> list;
    private int nextId;
    private Calendar cal = Calendar.getInstance();

    public InfoController() {
        list = new ArrayList<>();
        nextId = 1;
        InfoDTO i = new InfoDTO();
        // 1번 영화
        i.setId(nextId++);
        i.setMovieId(1);
        i.setCinemaId(1);
        cal.set(2021, 4, 10, 14, 10);
        i.setShowTime(cal);
        list.add(i);

        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(1);
        i.setCinemaId(1);
        cal.set(2021, 4, 11, 16, 10);
        i.setShowTime(cal);
        list.add(i);

        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(1);
        i.setCinemaId(2);
        cal.set(2021, 4, 12, 19, 0);
        i.setShowTime(cal);
        list.add(i);

        // 2번 영화
        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(2);
        i.setCinemaId(1);
        cal.set(2021, 4, 10, 15, 40);
        i.setShowTime(cal);
        list.add(i);

        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(2);
        i.setCinemaId(2);
        cal.set(2021, 4, 13, 18, 30);
        i.setShowTime(cal);
        list.add(i);

        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(2);
        i.setCinemaId(3);
        cal.set(2021, 4, 14, 12, 30);
        i.setShowTime(cal);
        list.add(i);

        // 3번 영화
        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(3);
        i.setCinemaId(2);
        cal.set(2021, 4, 10, 20, 00);
        i.setShowTime(cal);
        list.add(i);

        i = new InfoDTO();
        i.setId(nextId++);
        i.setMovieId(3);
        i.setCinemaId(3);
        cal.set(2021, 4, 16, 9, 20);
        i.setShowTime(cal);
        list.add(i);
    }

    // selectOne
    public InfoDTO selectOne(int id) {
        for (InfoDTO i : list) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    // selectAll
    public ArrayList<InfoDTO> selectAll() {
        return list;
    }

    // selectAll (영화를 기준으로)
    public ArrayList<InfoDTO> selectAllByMovie(int movieId) {
        ArrayList<InfoDTO> tmp = new ArrayList<>();
        for (InfoDTO i : list) {
            if (i.getMovieId() == movieId) {
                tmp.add(i);
            }
        }
        return tmp;
    }

    // selectAll (극장을 기준으로)
    public ArrayList<InfoDTO> selectAllByCinema(int cinemaId) {
        ArrayList<InfoDTO> tmp = new ArrayList<>();
        for (InfoDTO i : list) {
            if (i.getCinemaId() == cinemaId) {
                tmp.add(i);
            }
        }
        return tmp;
    }

    // insert
    public void insert(InfoDTO i) {
        i.setId(nextId++);
        list.add(i);
    }

    // update
    public void update(InfoDTO i) {
        int index = list.indexOf(i);
        if (index != -1) {
            list.set(index, i);
        }
    }

    // delete
    public void delete(int id) {
        InfoDTO i = new InfoDTO();
        i.setId(id);
        list.remove(list.indexOf(i));
    }

}
