package viewer;

import java.util.Calendar;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import util.ScannerUtil;

import model.InfoDTO;
import model.UserDTO;

import controller.CinemaController;
import controller.InfoController;
import controller.MovieController;

public class InfoViewer {
    private Scanner scanner;
    private InfoController infoController;
    private MovieController movieController;
    private CinemaController cinemaController;
    private MovieViewer movieViewer;
    private CinemaViewer cinemaViewer;
    private Calendar cal = Calendar.getInstance();
    private UserDTO logIn;

    private final int CATEGORY_ADMIN = 1;
    private final String ERROR_MSG = new String("잘못된 접근이거나 권한이 없습니다.");
    private final SimpleDateFormat sdf = new SimpleDateFormat("M월 dd일 H시 mm분");

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setInfoController(InfoController infocontroller) {
        this.infoController = infocontroller;
    }

    public void setMovieController(MovieController movieController) {
        this.movieController = movieController;
    }

    public void setCinemaController(CinemaController cinemaController) {
        this.cinemaController = cinemaController;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    public void setMovieViewer(MovieViewer movieViewer) {
        this.movieViewer = movieViewer;
    }

    public void setCinemaViewer(CinemaViewer cinemaViewer) {
        this.cinemaViewer = cinemaViewer;
    }

    // 상영 목록
    public void printAllInfo() {

        System.out.println("=================================================================");
        System.out.printf("<%d년 %d월 상영 일정>\n", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
        System.out.println("-----------------------------------------------------------------");

        for (InfoDTO i : infoController.selectAll()) {
            System.out.printf("[%d] %s | %s | %s\n", i.getId(), movieController.selectOne(i.getMovieId()).getTitle(),
                    cinemaController.selectOne(i.getCinemaId()).getCinemaName(), sdf.format(i.getShowTime().getTime()));
        }
        System.out.println("=================================================================");
        String msg;
        int userChoice;
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            msg = new String("1. 상영 일정 추가 2. 상영 일정 수정 3. 상영 일정 삭제 | 뒤로 가기 : 0");
            userChoice = ScannerUtil.nextInt(scanner, msg);
            if (userChoice == 1) {
                insertInfo();
            } else if (userChoice == 2) {
                updateInfo();

            } else if (userChoice == 3) {
                deleteInfo();
            }
        } else {
            msg = new String("뒤로가기: 0");
            userChoice = ScannerUtil.nextInt(scanner, msg);
        }
    }

    // 상영 목록 (영화 별)
    public void printAllInfoByMovie(int movieId) {
        System.out.println("------------------------------------------");
        System.out.printf("<%s 상영 일정>\n", movieController.selectOne(movieId).getTitle());
        System.out.println("------------------------------------------");

        for (InfoDTO i : infoController.selectAllByMovie(movieId)) {
            System.out.printf("[%d]| %s | %s\n", i.getId(), cinemaController.selectOne(i.getCinemaId()).getCinemaName(),
                    sdf.format(i.getShowTime().getTime()));
        }
        System.out.println("------------------------------------------");
        String msg = new String("뒤로가기: 0 ");
        int userChoice = ScannerUtil.nextInt(scanner, msg);
        if (userChoice == 0) {
            movieViewer.printOneMovie(movieId);
        }
    }

    // 상영 목록 (극장 별)
    public void printAllInfoByCinema(int cinemaId) {
        System.out.println("------------------------------------------");
        System.out.println(cinemaController.selectOne(cinemaId).getCinemaName() + "상영 일정");
        System.out.println("------------------------------------------");

        for (InfoDTO i : infoController.selectAllByCinema(cinemaId)) {
            System.out.printf("[%d]| %s | %s\n", i.getId(), movieController.selectOne(i.getMovieId()).getTitle(),
                    sdf.format(i.getShowTime().getTime()));
        }
        System.out.println("------------------------------------------");
        String msg = new String("뒤로가기: 0 ");
        int userChoice = ScannerUtil.nextInt(scanner, msg);
        if (userChoice == 0) {
            cinemaViewer.printOneCinema(cinemaId);
        }
    }

    // 새로운 상영정보 등록
    public void insertInfo() {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            InfoDTO i = new InfoDTO();
            String msg;
            msg = new String("상영 정보를 추가할 영화 번호를 입력해주세요.");
            i.setMovieId(ScannerUtil.nextInt(scanner, msg));
            while (movieController.selectOne(i.getMovieId()) == null) {
                System.out.println("해당 번호를 가진 영화가 존재하지 않습니다.(새로운 영화를 추가하려면 영화 추가 카테고리로 이동해주세요.)");
                msg = new String("상영정보를 추가할 영화 번호를 입력해주세요. | 뒤로 가기 : 0");
                i.setMovieId(ScannerUtil.nextInt(scanner, msg));
                if (i.getMovieId() == 0) {
                    break;
                }
            }
            if (i.getMovieId() != 0) {
                msg = new String("상영 정보를 추가할 극장 번호를 입력해주세요.");
                i.setCinemaId(ScannerUtil.nextInt(scanner, msg));
                while (cinemaController.selectOne(i.getCinemaId()) == null) {
                    System.out.println("해당 번호를 가진 극장이 존재하지 않습니다.(새로운 극장을 추가하려면 극장 추가 카테고리로 이동해주세요.)");
                    msg = new String("상영정보를 추가할 극장 번호를 입력해주세요 | 뒤로 가기 : 0");
                    i.setCinemaId(ScannerUtil.nextInt(scanner, msg));
                    if (i.getCinemaId() == 0) {
                        break;
                    }
                }
                if (i.getCinemaId() != 0) {
                    String msg1 = new String("상영 날짜를 입력하세요.");
                    String msg2 = new String("상영 시각을 입력하세요.");
                    String msg3 = new String("상영 분을 입력하세요.");
                    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), ScannerUtil.nextInt(scanner, msg1),
                            ScannerUtil.nextInt(scanner, msg2), ScannerUtil.nextInt(scanner, msg3));
                    i.setShowTime(cal);
                    msg = new String("해당 상영정보를 추가하시겠습니까? [Y/N]");
                    String yesNo = ScannerUtil.nextLine(scanner, msg);
                    if (yesNo.equalsIgnoreCase("y")) {
                        infoController.insert(i);
                    }
                }
            }
        } else {
            System.out.println(ERROR_MSG);
        }
        printAllInfo();
    }

    // 수정 (관리자 전용)
    public void updateInfo() {
        String msg;
        msg = new String("수정할 상영 정보 번호를 입력하세요. | 뒤로 가기 : 0");
        int infoId = ScannerUtil.nextInt(scanner, msg);
        if (infoId != 0) {
            while (infoController.selectOne(infoId) == null) {
                System.out.println("잘못 입력하셨습니다.");
                infoId = ScannerUtil.nextInt(scanner, msg);
                if (infoId == 0) {
                    break;
                }
            }
            if (infoId != 0) {
                String yesNo;
                InfoDTO tmp = infoController.selectOne(infoId);
                if (logIn.getCategory() == CATEGORY_ADMIN) {
                    msg = new String("영화 번호를 수정하시겠습니까? [Y/N]");
                    yesNo = ScannerUtil.nextLine(scanner, msg);
                    if (yesNo.equalsIgnoreCase("y")) {
                        msg = new String("새로운 영화 번호를 입력해주세요.");
                        tmp.setMovieId(ScannerUtil.nextInt(scanner, msg));
                        while (movieController.selectOne(tmp.getMovieId()) == null) {
                            System.out.println("해당 번호를 가진 영화가 존재하지 않습니다.(새로운 영화를 추가하려면 영화 추가 카테고리로 이동해주세요.)");
                            msg = new String("새로운 영화 번호를 입력해주세요. | 뒤로 가기: 0");
                            tmp.setMovieId(ScannerUtil.nextInt(scanner, msg));
                            if (tmp.getMovieId() == 0) {
                                break;
                            }
                        }
                    }
                    if (tmp.getMovieId() != 0) {
                        msg = new String("극장 번호를 수정하시겠습니까? [Y/N]");
                        yesNo = ScannerUtil.nextLine(scanner, msg);
                        if (yesNo.equalsIgnoreCase("y")) {
                            msg = new String("새로운 극장 번호를 입력해주세요.");
                            tmp.setCinemaId(ScannerUtil.nextInt(scanner, msg));
                            while (cinemaController.selectOne(tmp.getCinemaId()) == null) {
                                System.out.println("해당 번호를 가진 극장이 존재하지 않습니다.(새로운 극장을 추가하려면 극장 추가 카테고리로 이동해주세요.)");
                                msg = new String("새로운 극장 번호를 입력해주세요. | 뒤로 가기: 0");
                                tmp.setCinemaId(ScannerUtil.nextInt(scanner, msg));
                                if (tmp.getCinemaId() == 0) {
                                    break;
                                }
                            }
                        }
                        if (tmp.getCinemaId() != 0) {
                            msg = new String("상영 날짜를 수정하시겠습니까? [Y/N]");
                            yesNo = ScannerUtil.nextLine(scanner, msg);
                            if (yesNo.equalsIgnoreCase("y")) {
                                String msg1 = new String("새로운 상영 날짜를 입력하세요.");
                                String msg2 = new String("새로운 상영 시각을 입력하세요.");
                                String msg3 = new String("새로운 상영 분을 입력하세요.");
                                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                                        ScannerUtil.nextInt(scanner, msg1), ScannerUtil.nextInt(scanner, msg2),
                                        ScannerUtil.nextInt(scanner, msg3));
                                tmp.setShowTime(cal);
                            }
                            msg = new String("입력하신 정보로 수정을 진행하시겠습니까? [Y/N]");
                            yesNo = ScannerUtil.nextLine(scanner, msg);
                            if (yesNo.equalsIgnoreCase("y")) {
                                infoController.update(tmp);
                            }
                        }
                    }
                } else {
                    System.out.println(ERROR_MSG);
                }
            }
        }
        printAllInfo();
    }

    // 삭제 (관리자 전용)
    public void deleteInfo() {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            String msg;
            msg = new String("삭제할 상영 정보 번호를 입력하세요. | 뒤로 가기 : 0");
            int infoId = ScannerUtil.nextInt(scanner, msg);
            if (infoId != 0) {
                while (infoController.selectOne(infoId) == null) {
                    System.out.println("잘못 입력하셨습니다.");
                    infoId = ScannerUtil.nextInt(scanner, msg);
                    if (infoId == 0) {
                        break;
                    }
                }
                if (infoId != 0) {
                    String yesNo;
                    msg = new String("해당 상영 정보를 정말로 삭제하시겠습니까? [Y/N]");
                    yesNo = ScannerUtil.nextLine(scanner, msg);
                    if (yesNo.equalsIgnoreCase("y")) {
                        infoController.delete(infoId);
                    }
                }
            }
        } else {
            System.out.println(ERROR_MSG);
        }
        printAllInfo();
    }
}
