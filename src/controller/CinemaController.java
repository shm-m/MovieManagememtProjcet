package controller;

import java.util.ArrayList;
import model.CinemaDTO;
public class CinemaController {
    private ArrayList<CinemaDTO> list;
    private int nextId;
    
    public CinemaController() {
        list = new ArrayList<>();
        nextId = 1;
        CinemaDTO t = new CinemaDTO();
        t.setId(nextId++);
        t.setCinemaName("CGV 미아점");
        t.setCinemaLocation("서울특별시 강북구 도봉로 34");
        t.setCinemaNumber(15441122);
        list.add(t);
        t = new CinemaDTO();
        t.setId(nextId++);
        t.setCinemaName("롯데시네마 구리아울렛점");
        t.setCinemaLocation("경기 구리시 동구릉로136번길 47");
        t.setCinemaNumber(15448855);
        list.add(t);
        t = new CinemaDTO();
        t.setId(nextId++);
        t.setCinemaName("메가박스 별내");
        t.setCinemaLocation("경기 남양주시 두물로 19");
        t.setCinemaNumber(15440070);
        list.add(t);
    }
    
    // selectOne
    public CinemaDTO selectOne(int id) {
        for(CinemaDTO t : list) {
            if(t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    
    // selectAll
    public ArrayList<CinemaDTO> selectAll() {
        return list;
    }
    
    // insert
    public void insert(CinemaDTO t) {
        t.setId(nextId++);
        list.add(t);
    }
    
    // update
    public void update(CinemaDTO t) {
        int index = list.indexOf(t);
        if(index != -1) {
            list.set(index, t);
        }
    }
    
    // delete
    public void delete(int id) {
        CinemaDTO t = new CinemaDTO();
        t.setId(id);
        list.remove(list.indexOf(t));
    }
}
