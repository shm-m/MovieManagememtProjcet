package viewer;

import java.util.Scanner;
import controller.RatingController;
import controller.UserController;
import model.RatingDTO;
import model.UserDTO;
import util.ScannerUtil;

public class RatingViewer {
    private Scanner scanner;
    private RatingController ratingController;
    private UserController userController;
    private UserDTO logIn;

    private final int CATEGORY_ADMIN = 1;
    private final int CATEGORY_REVIEWER = 2;

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setRatingController(RatingController ratingController) {
        this.ratingController = ratingController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setLogIn(UserDTO logIn) {
        this.logIn = logIn;
    }

    // (영화별) 전체 평점
    public void printAllRating(int movieId) {
        System.out.println("=========================================================================");
        System.out.printf("평균 평점: ★%.1f \n", averageRating(movieId));
        System.out.println("-------------------------------------------------------------------------");
        for (RatingDTO r : ratingController.selectAllByMovie(movieId)) {
            System.out.printf("[%d] ★%d | 작성자: %s \n", r.getId(), r.getRating(),
                    userController.selectOne(r.getWriterId()).getNickname());
        }
        System.out.println("-------------------------------------------------------------------------");
        String msg = new String("1. 평점 작성 2. 관람객 평점 보기 3. 전문 평론가 평점 및 평론 보기 | 뒤로 가기: 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 3);
        if (userChoice == 1) {
            insertRating(movieId);
        } else if (userChoice == 2) {
            printGeneralRating(movieId);
        } else if (userChoice == 3) {
            printReview(movieId);
        }
    }

    // 평균 평점
    public double averageRating(int movieId) {
        int allRating = 0;
        for (RatingDTO r : ratingController.selectAllByMovie(movieId)) {
            allRating = allRating + r.getRating();
        }
        double averageRating = allRating / (double) ratingController.size(movieId);
        return averageRating;
    }

    // 일반 관람객 평점 보기
    public void printGeneralRating(int movieId) {
        System.out.println("-----------------------------------------");
        for (RatingDTO r : ratingController.selectAllByMovie(movieId)) {
            if (userController.selectOne(r.getWriterId()).getCategory() == 3) {
                System.out.printf("[%d]\t", r.getId());
                int starNum = r.getRating();
                char[] stars = new char[starNum];
                for (int i = 0; i < starNum; i++) {
                    stars[i] = '★';
                    System.out.print(stars[i]);
                }
                if (r.getWriterId() == logIn.getId()) {
                    System.out.printf("\t| 작성자 : (본인)\n", userController.selectOne(r.getWriterId()).getNickname());
                } else {
                    System.out.printf("\t| 작성자 : %s\n", userController.selectOne(r.getWriterId()).getNickname());
                }
            }
        }
        System.out.println("-----------------------------------------");
        String msg = new String("1. 전문 평론가 평점 및 평론 | 뒤로가기: 0 ");
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 1);
        if (userChoice == 1) {
            printReview(movieId);
        }
        printAllRating(movieId);
    }

    // 평론가 평점 및 평론
    public void printReview(int movieId) {
        System.out.println("-----------------------------------------");
        for (RatingDTO r : ratingController.selectAllByMovie(movieId)) {
            if (userController.selectOne(r.getWriterId()).getCategory() == 2) {
                System.out.printf("[%d]\t", r.getId());
                int starNum = r.getRating();
                char[] stars = new char[starNum];
                for (int i = 0; i < starNum; i++) {
                    stars[i] = '★';
                    System.out.print(stars[i]);
                }
                if (r.getWriterId() == logIn.getId()) {
                    System.out.printf("\t| 작성자 : (본인)\n", userController.selectOne(r.getWriterId()).getNickname());
                } else {
                    System.out.printf("\t| 작성자 : %s\n", userController.selectOne(r.getWriterId()).getNickname());
                }
                System.out.println("-----------------------------------------");
                System.out.printf("%s\n", r.getReview());
                System.out.println("-----------------------------------------");
            }
        }
        String msg = new String("1. 관람객 평점 | 뒤로 가기: 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 1);
        if (userChoice == 1) {
            printGeneralRating(movieId);
        }
        printAllRating(movieId);
    }

    // 평점 입력
    public void insertRating(int movieId) {
        String msg;
        String yesNo;
        RatingDTO r = new RatingDTO();
        r.setMovieId(movieId);
        r.setWriterId(logIn.getId());
        if (logIn.getCategory() != CATEGORY_ADMIN) {
            if (logIn.getCategory() == CATEGORY_REVIEWER) {
                msg = new String("해당 영화의 평점과 평론을 입력하시겠습니까? [Y/N]");
            } else {
                msg = new String("해당 영화의 평점을 입력하시겠습니까? [Y/N]");
            }
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("Y")) {
                msg = new String("평점을 입력해주세요. | 5(최고의 영화!)/ 4(만족)/ 3(보통)/ 2(불만족)/ 1(최악의 영화!)");
                r.setRating(ScannerUtil.nextInt(scanner, msg, 1, 5));
                if (logIn.getCategory() == CATEGORY_REVIEWER) {
                    msg = new String("평론을 입력해주세요.");
                    r.setReview(ScannerUtil.nextLine(scanner, msg));
                }
                ratingController.insert(r);
            }
        } else {
            System.out.println("관리자는 평점 및 평론을 작성할 수 없습니다.");
        }
        printAllRating(movieId);
    }

}
