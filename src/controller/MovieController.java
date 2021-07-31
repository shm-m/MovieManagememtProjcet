package controller;

import java.util.ArrayList;
import model.MovieDTO;

public class MovieController {
    private ArrayList<MovieDTO> list;
    private int nextId;
    private final int G = 0;
    private final int TWELVE_RATING = 1;
    private final int FIFTEEN_RATING = 2;
    private final int R = 3;

    public MovieController() {

        list = new ArrayList<>();
        nextId = 1;
        MovieDTO m = new MovieDTO();
        m.setId(nextId++);
        m.setTitle("짱구는 못말려 : 암흑 마왕 대추적");
        m.setDirector("하라 케이이치");
        m.setGenre("애니메이션/코미디/어드벤처");
        m.setSummary("세상의 지키려는 주유랑족과, 지배하려는 주황천족 싸움에 우연찮게 말려든 짱구와 가족들.\n그들은 무사히 세상의 평화를 지켜낼 수 있을 것인가!");
        m.setRank(G);
        list.add(m);

        m = new MovieDTO();
        m.setId(nextId++);
        m.setTitle("배트맨 대 슈퍼맨: 저스티스의 시작");
        m.setDirector("잭 스나이더");
        m.setGenre("판타지/액션/SF/모험");
        m.setSummary("모든 대결에는 이유가 있다!\n배트맨은 세계의 미래를 위해 무모한 힘을 가진 슈퍼맨으로 인해 벌어졌던 일들을 바로 잡으려 하는데...");
        m.setRank(TWELVE_RATING);
        list.add(m);

        m = new MovieDTO();
        m.setId(nextId++);
        m.setTitle("올드보이");
        m.setDirector("박찬욱");
        m.setGenre("드라마/범죄/미스터리/스릴러");
        m.setSummary("오태수는 어느 날 술이 거나하게 취해 집에 돌아가는 길에 누군가에게 납치, 사설 감금방에 갇히게 된다.");
        m.setRank(R);
        list.add(m);
    }

    // selectOne
    public MovieDTO selectOne(int id) {
        MovieDTO tmp = new MovieDTO();
        for (MovieDTO m : list) {
            if (m.getId() == id) {
                tmp = m;
                return tmp;
            }
        }
        return null;
    }

    // selectAll
    public ArrayList<MovieDTO> selectAll() {
        return list;
    }

    // insert
    public void insert(MovieDTO m) {
        m.setId(nextId++);
        list.add(m);
    }

    // update
    public void update(MovieDTO m) {
        int index = list.indexOf(m);
        if (index != -1) {
            list.set(index, m);
        }
    }

    // delete
    public void delete(int id) {
        MovieDTO m = new MovieDTO();
        m.setId(id);
        list.remove(list.indexOf(m));
    }

}
