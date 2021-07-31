package util;

import java.util.Scanner;

public class ScannerUtil {

    // 1. nextLine 사용 시,
    // scanner 버그가 발생할 때는
    // 자동으로 버퍼 메모리를 비워서
    // 우리가 굳이 scanner.nexLine() 을 두번 안써도 되게 해주는
    // nextLine() 메소드
    public static String nextLine(Scanner scanner, String message) {
        System.out.println(message);
        System.out.println(">");
        String result = new String(scanner.nextLine());

        // String 클래스 변수 혹은 값이 아무런 내용이 없는 지 체크할 때는
        // isEmpty() 메소드를 실행해서 비어있으면 true, 값이 존재하면 false가 실행된다.
        if (result.isEmpty()) {
            result = new String(scanner.nextLine());

        }

        return result;
    }

    
    // 2. 사용자로부터 입력을 받을 때
    // 출력할 메시지와 Scanner 클래스 변수를 파라미터로 받아서
    // int 입력을 받아서 return을 해주는 nextInt() 메소드
    public static int nextInt(Scanner scanner, String message) {
        System.out.println(message);
        System.out.println(">");
        return scanner.nextInt();
    }

    
    // 3. 사용자로부터 입력을 받을 때
    // 출력할 메시지와 Scanner 클래스 변수, 최소값, 최대값을 파라미터로 받아서
    // 해당 범위 안의 int 값을 return 해주는 nextInt() 메소드
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        System.out.println(message);
        System.out.println(">");
        int number = scanner.nextInt();

        while (!(number >= min && number <= max)) {
            System.out.println("잘못 입력하셨습니다.");
            System.out.println(message);
            System.out.println("> ");
            number = scanner.nextInt();
        }

        return number;
    }

    
    // 4. 사용자가 파라미터로 넘겨주는 message를 출력하고
    //    double 입력을 받아서 리턴해주는 nextDouble() 메소드 

    public static double nextDouble(Scanner scanner, String message) {
        System.out.println(message);
        System.out.println("> ");
        return scanner.nextDouble();
    }

    public static double nextDouble(Scanner scanner, String message, double min, double max) {
        System.out.println(message);
        System.out.println("> ");
        double temp = scanner.nextDouble();

        while (!(temp >= min && temp <= max)) {
            System.out.println("잘못 입력하셨습니다.");
            System.out.println(message);
            System.out.println("> ");
            temp = scanner.nextDouble();
        }
        return temp;
    }

}
