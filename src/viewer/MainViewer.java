package viewer;

import java.util.Scanner;
import controller.CinemaController;
import controller.InfoController;
import controller.MovieController;
import controller.RatingController;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class MainViewer {
    public static UserDTO logIn;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController();
        MovieController movieController = new MovieController();
        RatingController ratingController = new RatingController();
        CinemaController cinemaController = new CinemaController();
        InfoController infoController = new InfoController();

        UserViewer userViewer = new UserViewer();
        userViewer.setController(userController);
        userViewer.setScanner(scanner);

        MovieViewer movieViewer = new MovieViewer();
        movieViewer.setScanner(scanner);
        movieViewer.setMovieController(movieController);

        RatingViewer ratingViewer = new RatingViewer();
        ratingViewer.setScanner(scanner);
        ratingViewer.setUserController(userController);
        ratingViewer.setRatingController(ratingController);
        movieViewer.setRatingViewer(ratingViewer);

        CinemaViewer cinemaViewer = new CinemaViewer();
        cinemaViewer.setCinemaController(cinemaController);
        cinemaViewer.setScanner(scanner);

        InfoViewer infoViewer = new InfoViewer();
        infoViewer.setScanner(scanner);
        infoViewer.setMovieController(movieController);
        infoViewer.setCinemaController(cinemaController);
        infoViewer.setInfoController(infoController);
        infoViewer.setMovieViewer(movieViewer);
        infoViewer.setCinemaViewer(cinemaViewer);
        movieViewer.setInfoViewer(infoViewer);
        cinemaViewer.setInfoViewer(infoViewer);

        while (true) {
            String msg;
            System.out.println("===============*무비트*================");
            System.out.println();
            msg = new String("      1. 로그인 2. 회원가입 3. 종료\n\n=====================================");
            int userChoice = ScannerUtil.nextInt(scanner, msg, 1, 3);
            if (userChoice == 1) {
                UserDTO logIn = userViewer.logIn();
                if (logIn != null) {
                    movieViewer.setLogIn(logIn);
                    ratingViewer.setLogIn(logIn);
                    cinemaViewer.setLogIn(logIn);
                    infoViewer.setLogIn(logIn);
                    while (true) {
                        System.out.println("==============================");
                        msg = new String(
                                "1. 상영 정보\n2. 영화 정보\n3. 극장 정보\n4. 설정\n5. 로그아웃\n==============================");
                        userChoice = ScannerUtil.nextInt(scanner, msg, 1, 5);
                        if (userChoice == 1) {
                            infoViewer.printAllInfo();
                        } else if (userChoice == 2) {
                            movieViewer.printAllMovie();
                        } else if (userChoice == 3) {
                            cinemaViewer.printAllCinema();
                        } else if (userChoice == 4) {
                            userViewer.setting();
                        } else if (userChoice == 5) {
                            logIn = null;
                            break;
                        }
                    }
                }
            } else if (userChoice == 2) {
                userViewer.register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }
    }
}
