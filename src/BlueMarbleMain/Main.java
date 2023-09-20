package BlueMarbleMain;
import BlueMarbleDAO.*;
import BlueMarbleDTO.Member;
import BlueMarbleDTO.Player;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {

        BlueMarbleSQL sql = new BlueMarbleSQL();

        Member mem = new Member();

        Player p = new Player();

        Scanner sc = new Scanner(System.in);


        boolean run1 = true;


        int menu = 0;

        String map =

                "+-----+-----+------+-----+-----+-----+---+-----+-----+-----+\n" +
                        "|  1  |  2  |  3   |  4  |  5  |  6  |  7|  8  |  9  |  10 |\n" +
                        "| 출발 | 서울 | 인천 | 대전 | 전주 |동두천|홍천|영종도| 남원 |무인도|\n" +
                        "+-----+-----+-----+------+-----+-----+---+-----+-----+-----+\n" +
                        "|  24 |                                              |  11 |\n" +
                        "| 파주 |                                              |울릉도|\n" +
                        "+-----+                                              +-----+\n" +
                        "|  23 |                                              |   2 |\n" +
                        "| 춘천 |                                              |의정부|\n" +
                        "+-----+-----+-----+-----+-----+-----+-----+-----+----+-----+\n" +
                        "|  22 |  21 |  20 |  19 |  18 |  17 |  16 |  15 | 14 |  13 |\n" +
                        "| 강릉 |강화도| 부산|남양주| 양양 |제주도| 남해 |영주시|횡성 |한번더|\n" +
                        "+-----+-----+-----+-----+-----+-----+-----+-----+----+-----+\n";
//        System.out.println(map);
        while (run1) {
            System.out.println("============================================");
            System.out.println();
            System.out.println("           B l u e M a r b l e            ");
            System.out.println();
            System.out.println("          시작하려면 Enter를 누르세요        ");
            System.out.println();
            System.out.println("============================================");
            sc.nextLine();
            sql.connect();
            boolean run = true;
            while (run) {
                System.out.println("============================================");
                System.out.println("[1]회원가입  [2]게임입장  [3]회원관리  [4]종료");
                System.out.println("============================================");
                System.out.print("선택 >> ");
                menu = sc.nextInt();

                switch (menu) {
                    case 1:
                        System.out.println("회원정보를 입력해주세요!");

                        int Num = sql.clientNumber() + 1;

                        System.out.print("아이디 >> ");
                        String Id = sc.next();

                        System.out.print("비밀번호 >> ");
                        String Pw = sc.next();

                        System.out.print("이름 >> ");
                        String Name = sc.next();

                        mem.setNum(Num);
                        mem.setId(Id);
                        mem.setPw(Pw);
                        mem.setName(Name);

                        sql.memberJoin(mem);

                        break;
                    case 2:
                        int numPlayers = 0;
                        System.out.println("게임 인원을 입력하세요");
                        numPlayers = sc.nextInt();
                        for (int i = 0; i < numPlayers; i++) {
                            System.out.print("아이디 >> ");
                            String Id1 = sc.next();
                            System.out.print("비밀번호 >> ");
                            String Pw1 = sc.next();

                            boolean check = sql.idcheck(Id1, Pw1);
                            if (check) {
                                sql.player(Id1);
                                System.out.println("님이 입장하셨습니다.");
                                int pNum = sql.playerNumber() + 1;

                                p.setPlayerNUm(pNum);
                                p.setName1(Id1);
                                p.setLocation(p.getLocation());
                                p.setBudget(p.getBudget());

                                sql.start1(p);
                            } else {
                                System.out.println("아이디와 비밀번호를 확인하세요");
                                i--;
                            }
                        }
                        // 게임 루프 시작
                        int numRounds = 0;
                        System.out.println("게임 라운드를 입력하세요");
                        numRounds = sc.nextInt();
                        sc.nextLine();

                        for (int round = 1; round <= numRounds; round++) {
                            System.out.println("============================================");
                            System.out.println("                라운드 " + round + " 시작!");
                            System.out.println("============================================");
                            // boolean run = true;

                            // 각 플레이어의 차례 진행
                            for (int playerNum = 1; playerNum <= numPlayers; playerNum++) {
                                // 플레이어 정보 출력
                                System.out.println(map);
                                System.out.println("플레이어 " + playerNum + "의 차례:");

                                sql.playercheck(playerNum);
                                // 주사위 굴리기
//                                while (run) {
//                                    sql.playercheck(playerNum);
//                                    int whereIam = sql.locationCheck(playerNum);
//                                    if (whereIam == 10) {
//                                        if (p.getJailCnt() == 1) {
//                                            p.setJailCnt(0);
//                                            playerNum++;
//                                        } else if (p.getJailCnt() != 1) {
//                                            System.out.println("무인도에서 한 턴 쉽니다...");
//                                            p.setJailCnt(1);
//                                            continue;
//                                        }
//                                    } else {
//                                        run = false;
//                                    }
//                                }
                                System.out.println("님 주사위를 던져주세요(엔터)");
                                sc.nextLine();
                                int diceValue = sql.dice();
                                System.out.println("주사위 결과에 따라 " + diceValue + "칸 앞으로 이동합니다.");
                                int budget = sql.getPlayerBudget(playerNum);
                                System.out.println("소지금 : " + budget);

                                int currentPlayerLocation = sql.findlocation(playerNum); // 현재 플레이어 위치 얻기
                                int newPosition = (currentPlayerLocation + diceValue) % 24; // 새로운 위치 계산

                                // 위치가 24를 넘어가면 0부터 다시 시작
                                if (newPosition < currentPlayerLocation) {
                                    System.out.println("출발 지점을 넘었습니다! 보너스를 획득합니다.");
                                    newPosition++;

                                    // 보너스 금액을 추가
                                    int bonusAmount = 100;
                                    sql.updatePlayerBudget(playerNum, bonusAmount);
                                    // 플레이어의 새로운 위치 업데이트

                                    sql.updatePlayerLocation(playerNum, newPosition);
                                } else {
                                    // 플레이어의 위치 업데이트
                                    sql.locationUpdate(diceValue, playerNum);
                                }
                                System.out.print("현재 위치는 : ");
                                int now = sql.findlocation(playerNum);
                                sql.nowLocation(now, playerNum);
                                System.out.print("입니다.");
                                System.out.println();

                                // 플레이어의 현재 위치 가져오기
                                currentPlayerLocation = sql.findlocation(playerNum);
                                // 현재 위치에 대한 게임 이벤트 처리
                                String land = sql.checkland(currentPlayerLocation);
                                switch (currentPlayerLocation) {
                                    case 1:
                                        System.out.println("출발점 입니다");
                                        System.out.println();

                                        break;
                                    case 10:
                                        System.out.println("한 번 쉽니다...");
                                        System.out.println();
//                                    sql.playercheck(playerNum);
//                                    int whereIam = sql.locationCheck(playerNum);
//                                        if (p.getJailCnt() == 1) {
//                                            p.setJailCnt(0);
//                                        } else if (p.getJailCnt() != 1) {
//                                            System.out.println("무인도에서 한 턴 쉽니다...");
//                                            p.setJailCnt(1);
//                                        }
                                        break;
                                    case 13 :
                                        // 이동한 후 위치
                                        int newLocation = currentPlayerLocation; // 초기값 설정 (현재 위치)

                                        // 13번 위치에 도착한 경우
                                        if (currentPlayerLocation == 13) {
                                            System.out.println("주사위를 한 번 더 던집니다.(엔터)");
                                            sc.nextLine();

                                            // 주사위를 한 번 더 던짐
                                            int secondDiceRoll = sql.dice();
                                            System.out.println("두 번째 주사위 결과: " + secondDiceRoll);

                                            // 어떤 숫자든 추가 이동 가능
                                            newLocation += secondDiceRoll;
                                            System.out.println("주사위 결과에 따라 " + secondDiceRoll + "칸 앞으로 이동합니다.");

                                            // 새로운 위치가 게임 보드를 벗어나면 보너스를 획득하도록 처리할 수 있음
                                            // 예를 들어, newLocation >= 24 일 때 보너스 처리
                                            if (newLocation >= 24) {
                                                System.out.println("게임 보드를 한 바퀴 돌았습니다! 보너스를 획득합니다.");
                                                int bonusAmount = 100; // 보너스 금액
                                                sql.updatePlayerBudget(playerNum, bonusAmount); // 보너스를 소지금에 추가
                                                newLocation %= 24; // 새로운 위치가 24를 넘어가면 0부터 다시 시작
                                            }
                                        }

                                        // 나머지 위치 업데이트 로직
                                        sql.locationUpdate(newLocation, playerNum);

                                        // 이동한 후 위치 출력
                                        System.out.println("이동한 후 위치: " + currentPlayerLocation);

                                        // 현재 위치 업데이트
                                        sql.updatePlayerLocation(playerNum, newLocation);

                                        // 현재 위치 출력

                                        System.out.println("도착한 후 현재 위치는 : " + currentPlayerLocation + " 입니다.");


                                        break;

                                    default:
                                        if (land != null) {
                                    String landOwner = sql.getLandOwner(currentPlayerLocation);
                                    System.out.println(landOwner + " : 님의 땅입니다.");
                                    int rentAmount = sql.landPrice(currentPlayerLocation);

                                    sql.payRent(playerNum, landOwner, rentAmount);
                                            int currentPlayerBudget = sql.getPlayerBudget(playerNum);

                                            if (currentPlayerBudget <= 0) {
                                                System.out.println("소지금이 0원 이하로 떨어져 탈락합니다.");
                                                // 탈락 처리 로직 추가 (소지금 초기화, 위치 초기화 등 필요한 처리 수행)
                                                // 여기서 필요한 탈락 처리를 구현하시면 됩니다.

                                                // 탈락 처리 후 게임 종료 여부 검사
                                                    System.out.println("게임이 종료되었습니다.");
                                                    sql.reset(); // 게임 데이터 초기화
                                                    run = false;

                                                // 탈락한 플레이어는 다음 플레이어의 턴으로 넘어가지 않고 continue를 사용하여 현재 턴 종료
                                                continue;
                                            }
                                } else {
                                    menu = 0;
                                    System.out.println("땅을 구매하시겠습니까?");
                                    System.out.println("1. 산다    2. 안산다");
                                    menu = sc.nextInt();
                                    sc.nextLine();

                                    switch (menu) {
                                        case 1:
                                            int r = sql.landPrice(currentPlayerLocation);
                                            String pName = sql.checkName(currentPlayerLocation);
                                            sql.purchaseLand(r, pName, currentPlayerLocation, playerNum);
                                            sql.getPlayerBudget(playerNum);
                                            System.out.println("소지금 : " + budget);
                                            System.out.println();

                                            break;
                                        case 2:

                                            break;
                                        default:
                                            break;
                                    }
                                }

                                        break;
                                }
                            }
                        }

// 게임 종료 로직 추가
            System.out.println("게임이 종료되었습니다.");
            sql.reset(); // 게임 데이터 초기화
            run = false;


            break;
                        case 3:
                            System.out.print("아이디를 입력해 주세요>> ");
                            String searchId = sc.next();
                            System.out.print("비밀번호를 입력해 주세요>> ");
                            String searchPw = sc.next();

                            if (sql.idcheck(searchId, searchPw)) {
                                System.out.println("로그인 성공!");

                                // 로그인 성공 후 메뉴 표시
                                System.out.println("[1] 아이디 비밀번호 수정");
                                System.out.println("[2] 아이디 비밀번호 이름 조회");
                                System.out.println("[3] 회원 계정 삭제");
                                System.out.print("메뉴 선택 >> ");
                                int adminMenu = sc.nextInt();

                                switch (adminMenu) {
                                    case 1:
                                        System.out.println("아이디 비밀번호 수정을 선택하셨습니다.");
                                        System.out.print("새로운 아이디 입력 >> ");
                                        String newId = sc.next();
                                        System.out.print("새로운 비밀번호 입력 >> ");
                                        String newPw = sc.next();

                                        sql.updateMemberId(newId, searchId);
                                        sql.updateMemberPassword(newId, newPw);
                                        break;

                                    case 2:
                                        System.out.println("아이디 비밀번호 이름 조회를 선택하셨습니다.");

                                        Member member = sql.getMemberInfo(searchId);

                                        if (member != null) {
                                            System.out.println("회원 정보:");
                                            System.out.println("아이디: " + member.getId());
                                            System.out.println("비밀번호: " + member.getPw());
                                            System.out.println("이름: " + member.getName());
                                        } else {
                                            System.out.println("해당 아이디로 등록된 회원이 없습니다.");
                                        }
                                        break;
                                    case 3:
                                        // 회원 계정 삭제 기능
                                        System.out.println("회원 계정 삭제를 선택하셨습니다.");

                                        System.out.print("정말로 회원 계정을 삭제하시겠습니까? (Y/N) >> ");
                                        String confirmDelete = sc.next();

                                        if (confirmDelete.equalsIgnoreCase("Y")) {
                                            // 회원 계정 삭제
                                            sql.deleteMember(searchId);
                                            System.out.println("회원 계정이 삭제되었습니다.");
                                        } else if (confirmDelete.equalsIgnoreCase("N")) {
                                            System.out.println("회원 계정 삭제가 취소되었습니다.");
                                        } else {
                                            System.out.println("잘못된 입력입니다. 회원 계정 삭제를 취소합니다.");
                                        }
                                        break;
                                    default:
                                        System.out.println("잘못된 메뉴를 선택하셨습니다.");
                                        break;
                                }
                            } else {
                                System.out.println("아이디와 비밀번호를 확인하세요");
                            }
                            break;
                        case 4:
                            sc.nextLine();
                            sql.reset();
                            sql.conClose();
                            run = false;
                            break;

                        default:
                            System.out.println("숫자를 다시 입력해주세요");
                            break;
                    }
                }
            }
          }
     }

