package week4.lecture;

import java.util.Scanner;

public class MainTwo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int secondAnswer = 0;
        int bokumbab = 0;
        int zzazang = 0;
        int tangsuS = 0;
        int tangsuM = 0;
        int tangsuL = 0;
        int thirdAnswer = -1;

        while (true) {
            System.out.println("[1.구구단 2.음식점 3.계산기 4.종료]");
            // 선택
            int selectCase = Integer.parseInt(sc.nextLine());


            // 구구단
            if (selectCase == 1) {
                int num = 2;
                int numTwo = 1;
                while (numTwo <= 9) {
                    System.out.printf("%d * %d = %d\n", num, numTwo, (num * numTwo++));
                }
                System.out.println("메인 메뉴로 돌아갑니다.");
            }


            // 음식점
            if (selectCase == 2) {
                if (bokumbab == 0 && zzazang == 0 && tangsuS == 0 && tangsuM == 0 && tangsuL == 0) {
                    System.out.print("볶음밥의 값을 입력해주세요 : ");
                    bokumbab = Integer.parseInt(sc.nextLine());
                    System.out.print("자장면의 값을 입력해주세요 : ");
                    zzazang = Integer.parseInt(sc.nextLine());
                    System.out.print("탕수육[소]의 값을 입력해주세요 : ");
                    tangsuS = Integer.parseInt(sc.nextLine());
                    System.out.print("탕수육[중]의 값을 입력해주세요 : ");
                    tangsuM = Integer.parseInt(sc.nextLine());
                    System.out.print("탕수육[대]의 값을 입력해주세요 : ");
                    tangsuL = Integer.parseInt(sc.nextLine());
                }

                while (true) {
                    System.out.println("[1.볶음밥 2.자장면 3.탕수육 4.종료]");
                    System.out.print("선택하세요 : ");
                    int selectNum = Integer.parseInt(sc.nextLine());
                    if (selectNum == 1) {
                        secondAnswer += bokumbab;
                        System.out.println("볶음밥 " + bokumbab + "원이 추가되어 현재 " + secondAnswer + "[누적금액]원입니다.");
                    }
                    if (selectNum == 2) {
                        secondAnswer += zzazang;
                        System.out.println("짜장면 " + zzazang + "원이 추가되어 현재 " + secondAnswer + "[누적금액]원입니다.");
                    }
                    if (selectNum == 3) {
                        System.out.println("[탕수육 사이즈 :: 1.소 2.중 3.대]");
                        System.out.print("사이즈를 선택해주세요 : ");
                        int size = Integer.parseInt(sc.nextLine());
                        if (size == 1) {
                            secondAnswer += tangsuS;
                            System.out.println("탕수육[소] " + tangsuS + "원이 추가되어 현재 " + secondAnswer + "[누적금액]원입니다.");
                        }
                        if (size == 2) {
                            secondAnswer += tangsuM;
                            System.out.println("탕수육[중] " + tangsuM + "원이 추가되어 현재 " + secondAnswer + "[누적금액]원입니다.");
                        }
                        if (size == 3) {
                            secondAnswer += tangsuL;
                            System.out.println("탕수육[대] " + tangsuL + "원이 추가되어 현재 " + secondAnswer + "[누적금액]원입니다.");
                        }
                    }
                    if (selectNum == 4) {
                        System.out.println("메인 메뉴로 돌아갑니다.");
                        break;
                    }
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    break;
                }
            }


            // 계산기
            if (selectCase == 3) {
                System.out.println("1.더하기 2.빼기");
                System.out.print("선택해주세요 : ");
                int selectNum = Integer.parseInt(sc.nextLine());
                if (selectNum == 1) {
                    if (thirdAnswer >= 0) {
                        System.out.print("두 번째 숫자");
                        int secondNum = Integer.parseInt(sc.nextLine());
                        System.out.println(thirdAnswer + " + " + secondNum + " = " + (thirdAnswer + secondNum));
                        thirdAnswer += secondNum;
                    }
                    if (thirdAnswer < 0) {
                        System.out.print("첫 번째 숫자 : ");
                        int firstNum = Integer.parseInt(sc.nextLine());
                        System.out.print("두 번째 숫자 : ");
                        int secondNum = Integer.parseInt(sc.nextLine());
                        thirdAnswer = firstNum + secondNum;
                        System.out.println(firstNum + " + " + secondNum + " = " + thirdAnswer);
                    }
                }

                if (selectNum == 2) {
                    if (thirdAnswer >= 0) {
                        System.out.print("두 번째 숫자 : ");
                        int secondNum = Integer.parseInt(sc.nextLine());

                        if (thirdAnswer < secondNum) {
                            System.out.println("두 번째 숫자가 더 클 수 없습니다.");
                            while (true) {
                                System.out.print("두 번째 숫자를 다시 입력해주세요 : ");
                                secondNum = Integer.parseInt(sc.nextLine());
                                if (thirdAnswer - secondNum >= 0) {
                                    break;
                                }
                            }
                        }

                        System.out.println(thirdAnswer + " - " + secondNum + " = " + (thirdAnswer - secondNum));
                        thirdAnswer -= secondNum;
                    }
                    if (thirdAnswer < 0) {
                        System.out.print("첫 번째 숫자 : ");
                        int firstNum = Integer.parseInt(sc.nextLine());
                        System.out.print("두 번째 숫자 : ");
                        int secondNum = Integer.parseInt(sc.nextLine());

                        if (firstNum < secondNum) {
                            System.out.println("두 번째 숫자가 더 클 수 없습니다.");
                            while (true) {
                                System.out.print("두 번째 숫자를 다시 입력해주세요 : ");
                                secondNum = Integer.parseInt(sc.nextLine());
                                if (firstNum - secondNum >= 0) {
                                    break;
                                }
                            }
                        }

                        thirdAnswer = firstNum - secondNum;
                        System.out.println(firstNum + " - " + secondNum + " = " + thirdAnswer);
                    }
                }
                System.out.println("메인 메뉴로 돌아갑니다.");
            }


            // 종료
            if (selectCase == 4) {
                System.out.println("[2번 문제] 합산된 금액 : " + secondAnswer);
                System.out.println("[3번 문제] 합산된 금액 : " + thirdAnswer);
                break;
            }

        }
    }
}
