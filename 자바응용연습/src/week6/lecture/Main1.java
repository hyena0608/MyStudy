package week6.lecture;

import java.util.Scanner;

public class Main1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int gumPrice = 5000;
        int snackPrice = 7000;
        int peachPrice = 4000;
        int totalPrice = 0;

        int gumCount = 0;
        int snackCount = 0;
        int peachCount = 0;

        int customerNum = 0;

        do {
            System.out.println("\n\n[1.껌5000원 2.과자7000원 3.복숭아4000원 4.환불 5.종료]");

            int n = Integer.parseInt(sc.nextLine());

            if (n == 1) {
                if (gumCount >= 5) {
                    System.out.println("껌은 5개를 초과할 수 없습니다.");
                }
                if (gumCount < 5) {
                    totalPrice += gumPrice;
                    gumCount++;
                    System.out.println(++customerNum + "번째 사람이 껌을 구매했습니다.");
                    System.out.println("지금까지 껌은 " + gumCount + "개 판매 되었고 누적금액은 " + (gumCount * gumPrice) + "원입니다.");
                }
            }

            if (n == 2) {
                totalPrice += snackPrice;
                snackCount++;
                System.out.println(++customerNum + "번째 사람이 과자를 구매했습니다.");
                System.out.println("지금까지 과자는 " + snackCount + "개 판매 되었고 누적금액은 " + (snackCount * snackPrice) + "원입니다.");
            }

            if (n == 3) {
                totalPrice += peachPrice;
                peachCount++;
                System.out.println(++customerNum + "번째 사람이 복숭아를 구매했습니다.");
                System.out.println("지금까지 복숭아는 " + peachCount + "개 판매 되었고 누적금액은 " + (peachCount * peachPrice) + "원입니다.");
            }

            if (n == 4) {
                if (gumCount == 0 && snackCount == 0 && peachCount == 0) {
                    System.out.println("환불할 물품이 없습니다.");
                }

                if (gumCount != 0 || snackCount != 0 || peachCount != 0) {
                    System.out.print("1.전체 환불, 2.개별 환불 : ");
                    int refundMenu = Integer.parseInt(sc.nextLine());

                    if (refundMenu == 1) {
                        System.out.println("전체 환불 모드 입니다.");
                        System.out.println(totalPrice + "원 만큼 환불이 되었습니다.");
                        gumCount = 0;
                        snackCount = 0;
                        peachCount = 0;
                        totalPrice = 0;
                    }

                    if (refundMenu == 2) {
                        System.out.println("개별 환별 모드입니다.");
                        System.out.println("[1.껌 2.과자 3.복숭아]");
                        System.out.print("환불할 메뉴를 입력해주세요 : ");
                        int eachRefundMenu = Integer.parseInt(sc.nextLine());

                        if (eachRefundMenu == 1) {
                            if (gumCount == 0) {
                                System.out.println("껌 구매이력이 없습니다.");
                            }
                            if (gumCount >= 1) {
                                System.out.println("껌 하나가 환불되었습니다.");
                                totalPrice -= gumPrice;
                                gumCount--;
                            }
                        }

                        if (eachRefundMenu == 2) {
                            if (snackCount == 0) {
                                System.out.println("과자 구매이력이 없습니다.");
                            }
                            if (snackCount >= 1) {
                                System.out.println("과자 한 봉지가 환불되었습니다.");
                                totalPrice -= snackPrice;
                                snackCount--;
                            }
                        }

                        if (eachRefundMenu == 3) {
                            if (peachCount == 0) {
                                System.out.println("복숭아 구매이력이 없습니다.");
                            }
                            if (peachCount >= 1) {
                                System.out.println("복숭아 하나가 환불되었습니다.");
                                totalPrice -= peachPrice;
                                peachCount--;
                            }
                        }
                    }
                }
            }

            if (n == 5) {
                if (snackCount == (snackCount / 10) * 10 && snackCount != 0) {
                    if (peachCount >= snackCount) {
                        System.out.println("프로그램을 종료합니다.");
                        break;
                    }
                }

                if (snackCount != (snackCount / 10) * 10 || snackCount == 0) {
                    System.out.println("과자가 10의 배수가 아닙니다.");
                    if (snackCount != (snackCount / 10) * 10) {
                        System.out.println("과자를 " + ((snackCount / 10 + 1) * 10 - snackCount) + "개 더 구매해야합니다.");
                    }
                    if (snackCount == 0) {
                        System.out.println("과자를 10개 더 구매하셔야 합니다.");
                    }
                }

                if (peachCount < snackCount) {
                    System.out.println("복숭아가 과자 개수와 같거나 많아야합니다.");
                    System.out.println("현재 과자는 " + snackCount + "개이고 복숭아는 " + peachCount + "개 입니다.");
                    System.out.println("복숭아를 " + (snackCount - peachCount) + "개 더 구매하셔야 합니다.");
                }
            }

            System.out.println("\n\n===매출표===");
            System.out.println("\n오늘의 매출 : " + totalPrice + "원");
            if (gumCount >= 1) {
                System.out.println("껌 x " + gumCount + "개 : " + (gumCount * gumPrice) + "원");
            }
            if (snackCount >= 1) {
                System.out.println("과자 x " + snackCount + "개 : " + (snackCount * snackPrice) + "원");
            }
            if (peachCount >= 1) {
                System.out.println("복숭아 x " + peachCount + "개 : " + (peachCount * peachPrice) + "원");
            }

        } while (true);
    }
}
