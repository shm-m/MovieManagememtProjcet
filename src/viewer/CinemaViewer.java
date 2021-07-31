package viewer;

import java.util.Scanner;
import controller.CinemaController;
import model.CinemaDTO;
import model.UserDTO;
import util.ScannerUtil;

public class CinemaViewer {
    private Scanner scanner;
    private CinemaController cinemaController;
    private InfoViewer infoViewer;
    private UserDTO logIn;

    private final int CATEGORY_ADMIN = 1;
    private final String ERROR_MSG = new String("잘못된 접근이거나 권한이 없습니다.");

    public void setCinemaController(CinemaController cinemaController) {
        this.cinemaController = cinemaController;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    public void setInfoViewer(InfoViewer infoViewer) {
        this.infoViewer = infoViewer;
    }

    // 극장 목록
    public void printAllCinema() {
        System.out.println("-------------------------------------------");
        for (CinemaDTO c : cinemaController.selectAll()) {
            System.out.printf("[%d] %s\n", c.getId(), c.getCinemaName());
        }
        System.out.println("-------------------------------------------");
        String msg = new String("상세 보기할 극장의 번호를 입력해주세요. | 뒤로 가기: 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg);
        while (cinemaController.selectOne(userChoice) == null && userChoice !=0) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(scanner, msg);
            if (userChoice == 0) {
                break;
            }
        }
        if (userChoice != 0) {
            printOneCinema(userChoice);
        }
    }

    // 극장 개별 보기
    public void printOneCinema(int id) {
        CinemaDTO c = cinemaController.selectOne(id);
        System.out.println("-------------------------------------------");
        System.out.printf("[%d] %s 정보\n", c.getId(), c.getCinemaName());
        System.out.println("-------------------------------------------");
        System.out.printf("☎: %d\n", c.getCinemaNumber());
        System.out.printf("도로명 주소: %s\n", c.getCinemaLocation());
        System.out.println("-------------------------------------------");
        String msg;
        if (logIn.getCategory() != CATEGORY_ADMIN) {
            // 평론가 및 일반 사용자
            msg = new String("1. 상영 일정  | 뒤로 가기: 0");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 1);
            if (userChoice == 1) {
                infoViewer.printAllInfoByCinema(id);
            } else {
                printAllCinema();
            }
        } else {
            // 관리자 전용
            msg = new String("1. 상영 일정 2. 극장 정보 수정 3. 극장 정보 삭제 | 뒤로 가기: 0");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 3);
            if (userChoice == 1) {
                infoViewer.printAllInfoByCinema(id);
            } else if (userChoice == 2) {
                updateCinema(id);
            } else if (userChoice == 3) {
                deleteCinema(id);
            } else {
                printAllCinema();
            }
        }

    }

    // 새로운 극장 등록 (관리자 전용)
    public void insertCinema() {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            CinemaDTO c = new CinemaDTO();
            String msg = new String("새로운 극장 이름을 입력해주세요.");
            c.setCinemaName(ScannerUtil.nextLine(scanner, msg));
            msg = new String("해당 극장의 위치를 입력해주세요.");
            c.setCinemaLocation(ScannerUtil.nextLine(scanner, msg));
            msg = new String("해당 극장의 전화번호를 입력해주세요.");
            c.setCinemaNumber(ScannerUtil.nextInt(scanner, msg));
            msg = new String("입력하신 극장의 정보를 추가하시겠습니까? [Y/N]");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                cinemaController.insert(c);
                printOneCinema(c.getId());
            }
        } else {
            System.out.println(ERROR_MSG);
        }
    }

    // 수정 (관리자 전용)
    public void updateCinema(int id) {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            CinemaDTO tmp = cinemaController.selectOne(id);
            String msg;
            String yesNo;

            msg = new String("해당 극장의 이름을 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 이름을 입력해주세요.");
                tmp.setCinemaName(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("해당 극장의 위치를 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 위치를 입력해주세요.");
                tmp.setCinemaLocation(ScannerUtil.nextLine(scanner, msg));
            }

            msg = new String("해당 극장의 전화번호를 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 전화번호를 입력해주세요.");
                tmp.setCinemaNumber(ScannerUtil.nextInt(scanner, msg));
            }

            msg = new String("입력하신 정보로 수정을 진행하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                cinemaController.update(tmp);
            }
        } else {
            System.out.println(ERROR_MSG);
        }
        printOneCinema(id);
    }

    // 삭제 (관리자 전용)
    public void deleteCinema(int id) {
        if (logIn.getCategory() == CATEGORY_ADMIN) {
            String msg;
            String yesNo;
            msg = new String("해당 극장 정보를 정말로 삭제하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                cinemaController.delete(id);
                printAllCinema();
            }
            printOneCinema(id);
        } else {
            System.out.println(ERROR_MSG);
        }

    }

}
