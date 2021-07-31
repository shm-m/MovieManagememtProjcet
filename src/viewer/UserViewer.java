package viewer;

import java.util.Scanner;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {
    private UserController controller;
    private Scanner scanner;
    private final String ERROR_MSG = new String("잘못된 접근이거나 권한이 없습니다.");

    public void setController(UserController controller) {
        this.controller = controller;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public UserDTO logIn;

    // 로그인
    public UserDTO logIn() {
        String username = ScannerUtil.nextLine(scanner, "회원 ID: ");
        String password = ScannerUtil.nextLine(scanner, "회원 PW: ");
        logIn = controller.auth(username, password);
        while (logIn == null) {
            System.out.println("존재하지 않는 아이디이거나, 잘못된 비밀번호입니다.");
            System.out.println("----------------------------------------");
            String msg = new String("회원 ID:              | 뒤로 가기: X");
            username = ScannerUtil.nextLine(scanner, msg);
            if (username.equalsIgnoreCase("x")) {
                break;
            }
            password = ScannerUtil.nextLine(scanner, "회원 PW: ");
            logIn = controller.auth(username, password);
        }
        if (logIn != null) {
            System.out.println("==============================");
            System.out.printf("       안녕하세요. %s님!\n", logIn.getNickname());
            return logIn;
        }
        return null;
    }

    // 회원가입
    public void register() {
        String msg;
        msg = new String("사용하실 아이디를 입력해주세요.");
        String username = ScannerUtil.nextLine(scanner, msg);
        while (controller.validateUsername(username) || username.equals("x")) {
            System.out.println("해당 아이디는 사용하실 수 없습니다.");
            msg = new String("사용하실 아이디를 입력해주세요.       | 뒤로 가기: X");
            username = ScannerUtil.nextLine(scanner, msg);
            if (username.equalsIgnoreCase("x")) {
                break;
            }
        }
        if (!username.equalsIgnoreCase("x")) {
            msg = new String("사용하실 비밀번호를 입력해주세요.");
            String password = ScannerUtil.nextLine(scanner, msg);
            msg = new String("사용하실 별명을 입력해주세요.");
            String nickname = ScannerUtil.nextLine(scanner, msg);
            msg = new String("입력하신 정보로 회원가입을 진행하시겠습니까? [Y/N]");
            String yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                UserDTO u = new UserDTO();
                u.setUsername(username);
                u.setPassword(password);
                u.setNickname(nickname);
                controller.insert(u);
            }
        }
    }

    // 개인 정보 설정
    public void setting() {
        String msg;
        userInfo(logIn.getId());
        msg = new String("1. 정보 수정 | 뒤로 가기: 0");
        int userChoice = ScannerUtil.nextInt(scanner, msg, 0, 1);
        if (userChoice == 1) {
            updateUser(logIn.getId());
        }
    }

    // 회원 정보
    public void userInfo(int id) {
        if (logIn.getId() == id) {
            System.out.println("==========================================");
            System.out.printf("%s님의 정보\n", logIn.getNickname());
            System.out.println("------------------------------------------");
            System.out.printf("회원 번호: %d | ID: %s\n", logIn.getId(), logIn.getUsername());
            System.out.printf("닉네임: %s | 등금: Lv.%d\n", logIn.getNickname(), logIn.getCategory());
            System.out.println("==========================================");
        } else {
            System.out.println(ERROR_MSG);
        }
    }

    // 정보 수정 (본인만 수정할 수 있도록 하셈)
    public void updateUser(int id) {
        String msg;
        String yesNo;
        if (logIn.getId() == id) {
            UserDTO tmp = controller.selectOne(id);
            msg = new String("비밀번호를 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 비밀번호를 입력해주세요.");
                tmp.setPassword(ScannerUtil.nextLine(scanner, msg));
            }
            msg = new String("별명을 수정하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                msg = new String("새로운 별명을 입력해주세요.");
                tmp.setNickname(ScannerUtil.nextLine(scanner, msg));
            }
            msg = new String("입력하신 정보로 수정을 진행하시겠습니까? [Y/N]");
            yesNo = ScannerUtil.nextLine(scanner, msg);
            if (yesNo.equalsIgnoreCase("y")) {
                controller.update(tmp);
            }

        } else {
            System.out.println(ERROR_MSG);
        }
        userInfo(id);
    }

}
