package viewer;

import java.util.Scanner;
import java.util.ArrayList;
import util.ScannerUtil;

import model.UserDTO;
import model.MovieDTO;

import controller.MovieController;

public class MovieViewer {
    private Scanner scanner;
    private MovieController movieController;
    private InfoViewer infoViewer;
    private RatingViewer ratingViewer;
    private UserDTO logIn;

    private final int CATEGORY_ADMIN = 1;
    private final String ERROR_MSG = new String("잘못된 접근이거나 권한이 없습니다.");

    public void setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLogIn(UserDTO login) {
        this.logIn = login;
    }

    public void setInfoViewer(InfoViewer infoViewer) {
        this.infoViewer = infoViewer;
    }

    public void setRatingViewer(RatingViewer ratingViewer) {
        this.ratingViewer = ratingViewer;
    }

    // 영화 목록 보기
    public void printAllMovie() {
        ArrayList<MovieDTO> list = movieController.selectAll();
        System.out.println("---------------------------------------------------------");
        System.out.println("[영화 번호]        영화 제목           |          장르    ");
        System.out.println("---------------------------------------------------------");

        for (MovieDTO m : list) {
            System.out.printf("[%d] %s | %s\n", m.getId(), m.getTitle(), m.getGenre());

        }
        System.out.println("---------------------------------------------------------");
        String msg;
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            msg = new String("1. 영화 상세 보기\n2. 새로운 영화 추가\n3. 기존 영화 수정\n4. 기존 영화 삭제  \t\t\t\t| 뒤로 가기 : 0");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 4);
            if (userChoice == 1) {
                selectOneMovie();
            } else if (userChoice == 2) {
                insertMovie();
            } else if (userChoice == 3) {
                msg = new String("수정할 영화 번호를 선택해주세요. | 뒤로 가기: 0");
                int movieId = ScannerUtil.nextInt(scanner, msg);
                while (movieController.selectOne(movieId) == null && userChoice != 0) {
                    System.out.println("잘못 입력하셨습니다.");
                    movieId = ScannerUtil.nextInt(scanner, msg);
                    if (userChoice == 0) {
                        break;
                    }
                }
                if (movieId != 0) {
                    updateMovie(movieId);
                }
            } else if (userChoice == 4) {
                msg = new String("삭제할 영화 번호를 선택해주세요. | 뒤로 가기: 0");
                int movieId = ScannerUtil.nextInt(scanner, msg);
                while (movieController.selectOne(movieId) == null && userChoice != 0) {
                    System.out.println("잘못 입력하셨습니다.");
                    movieId = ScannerUtil.nextInt(scanner, msg);
                    if (userChoice == 0) {
                        break;
                    }
                }
                if (movieId != 0) {
                    deleteMovie(movieId);
                }
            } else if (userChoice == 0) {
            }
        } else {
            selectOneMovie();
        }
    }

    // 개별 보기 선택
    public void selectOneMovie() {
        String msg = new String("상세보기할 영화 번호를 선택해주세요. | 뒤로 가기: 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg);
        while (movieController.selectOne(userChoice) == null && userChoice != 0) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(scanner, msg);
            if (userChoice == 0) {
                break;
            }
        }
        if (userChoice != 0) {
            printOneMovie(userChoice);
        } else {
        }
    }

    // 영화 개별 보기
    public void printOneMovie(int id) {
        MovieDTO m = movieController.selectOne(id);
        String rank = null;
        if (m.getRank() == 0) {
            rank = new String("전체 관람가");
        } else if (m.getRank() == 1) {
            rank = new String("12세 관람가");
        } else if (m.getRank() == 2) {
            rank = new String("15세 관람가");
        } else if (m.getRank() == 3) {
            rank = new String("청소년 관람불가");
        }
        System.out.println("===========================================================================");
        System.out.printf("[%d] %s\n", m.getId(), m.getTitle());
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("감독: %s | 평균 ★: %.1f\n", m.getDirector(), ratingViewer.averageRating(id));
        System.out.println("---------------------------------------------------------------------------");

        System.out.printf("장르: %s | 등급: %s \n", m.getGenre(), rank);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println();
        System.out.printf(m.getSummary());
        System.out.println();
        System.out.println();
        System.out.println("===========================================================================");
        String msg = new String("1. 상영 일정 2. 평점  | 뒤로가기 : 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 2);
        if (userChoice == 1) {
            infoViewer.printAllInfoByMovie(id);
        } else if (userChoice == 2) {
            ratingViewer.printAllRating(id);
        }
        printAllMovie();
    }

    // (관리자 전용) 새로운 영화 등록하기
    public void insertMovie() {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            MovieDTO m = new MovieDTO();
            String msg;
            msg = new String("새로운 영화 제목을 입력하세요.");
            m.setTitle(ScannerUtil.nextLine(scanner, msg));
            msg = new String("해당 영화의 감독을 입력하세요.");
            m.setDirector(ScannerUtil.nextLine(scanner, msg));
            msg = new String("해당 영화 줄거리를 입력하세요.");
            m.setSummary(ScannerUtil.nextLine(scanner, msg));
            msg = new String("해당 영화의 등급을 입력하세요. | 전체관람가: 0 / 12세 관람가: 1 / 15세 관람가: 2 / 청소년 관람불가: 3");
            m.setRank(ScannerUtil.nextInt(scanner, msg, 0, 3));
            msg = new String("입력한 영화의 정보를 추가하시겠습니까? [Y/N]");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("Y")) {
                movieController.insert(m);
            }
            printOneMovie(m.getId());
        } else {
            System.out.println(ERROR_MSG);
        }
    }

    // 기존 영화 정보 수정(관리자 전용)
    public void updateMovie(int id) {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            MovieDTO tmp = movieController.selectOne(id);
            String msg;
            String yesNo;
            msg = new String("해당 영화의 제목을 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 영화 제목을 입력해주세요.");
                tmp.setTitle(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("해당 영화의 감독을 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 감독 이름을 입력해주세요.");
                tmp.setDirector(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("해당 영화의 장르를 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 장르를 입력해주세요.");
                tmp.setGenre(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("해당 영화의 등급을 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 등급을 입력해주세요.| 전체관람가: 0 / 12세 관람가: 1 / 15세 관람가: 2 / 청소년 관람불가: 3");
                tmp.setRank(ScannerUtil.nextInt(scanner, msg, 0, 3));
            }

            msg = new String("해당 영화의 줄거리를 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 줄거리를 입력해주세요.");
                tmp.setSummary(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("입력하신 정보로 수정을 진행하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                movieController.update(tmp);
            }
            printOneMovie(id);
        } else {
            System.out.println(ERROR_MSG);
        }
    }

    // 기존 영화 정보 삭제하기 (관리자 전용)
    public void deleteMovie(int id) {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            String msg = new String("해당 영화 정보를 정말로 삭제하시겠습니까? [Y/N]");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("Y")) {
                movieController.delete(id);
            }
            printAllMovie();
        } else {
            System.out.println(ERROR_MSG);
        }
    }
}
