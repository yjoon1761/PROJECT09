package BlueMarbleDAO;

import BlueMarbleDTO.Member;
import BlueMarbleDTO.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class BlueMarbleSQL {
    Connection con;

    PreparedStatement pstmt;

    ResultSet rs;



    //접속
    public void connect() {
        con = DBConnection.DBConnect();
    }

    //해제
    public void conClose() {
        try {
            con.close();
            System.out.println("프로그램 종료");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int clientNumber() {
        int Num = 0;

        String sql = "SELECT MAX(MNUM) FROM MEMBER";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Num = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Num;
    }

    public int playerNumber() {
        int Num = 0;

        String sql = "SELECT MAX(PLAYERNUM) FROM PLAYER";

        try {
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Num = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Num;
    }

    public void memberJoin(Member mem) {
        String sql = "INSERT INTO MEMBER VALUES(?, ?, ?, ?)";
        try {

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, mem.getNum());
            pstmt.setString(2, mem.getId());
            pstmt.setString(3, mem.getPw());
            pstmt.setString(4, mem.getName());


            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("회원가입 성공!");
            } else {
                System.out.println("회원가입 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean idcheck(String Id, String Pw) {
        boolean result = false;
        String sql = "SELECT * FROM MEMBER WHERE MID=? AND MPW=?";
        try {

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, Id);
            pstmt.setString(2, Pw);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public void player(String Id) {

        String sql = "SELECT MNAME FROM MEMBER WHERE MID = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, Id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getNString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start1(Player P1) {
        String sql = "INSERT INTO PLAYER VALUES(?, ?, ?, ?)";
        try {

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, P1.getPlayerNUm());
            pstmt.setString(2, P1.getName1());
            pstmt.setInt(3, P1.getLocation());
            pstmt.setInt(4, P1.getBudget());


            int result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int dice() {
        Random rd = new Random();
        int dice1 = rd.nextInt(6) + 1;


        return dice1;
    }

//    public void locationUpdate(int diceValue, int i) {
//        String sql = "UPDATE PLAYER SET PLOCATION = PLOCATION + ? WHERE PLAYERNUM = ?";
//        try {
//            pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1, diceValue);
//            pstmt.setInt(2, i);
//
//            int result = pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public String playercheck(int i) {
        String PNAME = "";
        String sql = "SELECT PNAME FROM PLAYER WHERE PLAYERNUM= ? ";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, i);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getNString(1));
                PNAME = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return PNAME;
    }

    public void nowLocation(int now,int playerNum) {

        String sql = "SELECT BNAME FROM PLAYER P, BMAP B WHERE P.PLOCATION = B.PLOCATION AND P.PLOCATION = ? AND P.PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, now);
            pstmt.setInt(2, playerNum);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getNString(1));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int findlocation(int i) {
        int playerLocation = -1;
        String sql = "SELECT PLOCATION FROM PLAYER WHERE PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, i);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                playerLocation = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playerLocation;
    }

    public void reset() {
        String sql1 = "DELETE FROM PLAYER";

        String sql2 = "UPDATE BMAP SET B_OWNER = NULL";
        try {
            pstmt = con.prepareStatement(sql1);
            pstmt = con.prepareStatement(sql2);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("리셋 성공!");
            } else {
                System.out.println("리셋 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 땅 주인 체크
    public String checkland(int currentPlayerLocation) {
        String playerLocation = "";
        String sql = "SELECT B_OWNER FROM BMAP WHERE PLOCATION = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, currentPlayerLocation);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                playerLocation = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playerLocation;
    }

    public String checkOwner(int land) {
        String landOwner = "";
        String sql = "SELECT B_OWNER FROM BMAP WHERE PLOCATION = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, land);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getNString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return landOwner;
    }

    public void payLand(int r, int g, String landOwner) {
        String sql1 = "UPDATE PLAYER SET PBUDGET = PBUDGET - ? WHERE PLAYERNUM = ?";

        String sql2 = "UPDATE PLAYER SET PBUDGET = PBUDGET + ? WHERE PNAME = ?";
        try {
            pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, r);
            pstmt.setInt(2, g);
            pstmt.executeUpdate();

            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, r);
            pstmt.setString(2, landOwner);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("입장료 납부!");
            } else {
                System.out.println("납부 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int landPrice(int now) {
        int landprice = 0;
        String sql = "SELECT PURCHASE_PRICE FROM BMAP WHERE PLOCATION = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, now);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                landprice = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return landprice;
    }
    public Member getMemberInfo(String searchId) {
        Member member = null;
        String sql = "SELECT MID, MPW, MNAME FROM MEMBER WHERE MID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, searchId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                member = new Member();
                member.setId(rs.getString("MID"));
                member.setPw(rs.getString("MPW"));
                member.setName(rs.getString("MNAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return member;
    }

    public void updateMemberPassword(String modifyId, String newPw) {
        String sql = "UPDATE MEMBER SET MPW = ? WHERE MID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newPw);
            pstmt.setString(2, modifyId);
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("비밀번호 업데이트 성공!");
            } else {
                System.out.println("비밀번호 업데이트 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMember(String searchId) {
        String sql = "DELETE FROM MEMBER WHERE MID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, searchId);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("회원 계정 삭제 성공!");
            } else {
                System.out.println("해당 아이디로 등록된 회원이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMemberId(String newId, String searchId) {
        String sql = "UPDATE MEMBER SET MID = ? WHERE MID = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newId);
            pstmt.setString(2, searchId);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("아이디 업데이트 성공!");
            } else {
                System.out.println("아이디 업데이트 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkName(int now) {
        String landOwner = "";
        String sql = "SELECT PNAME FROM PLAYER WHERE PLOCATION = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, now);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.print(rs.getNString(1));
                landOwner = rs.getNString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return landOwner;
    }
    public void purchaseLand(int r,String pName,int currentPlayerLocation, int playernum) {
        String updateOwnerSQL = "UPDATE BMAP SET B_OWNER = ? WHERE PLOCATION = ?";

        String updateBudgetSQL = "UPDATE PLAYER SET PBUDGET = PBUDGET - ? WHERE PLAYERNUM = ?";

        try {
            // 트랜잭션 시작
            con.setAutoCommit(false);

            // 땅 소유자 업데이트
            pstmt = con.prepareStatement(updateOwnerSQL);
            pstmt.setString(1, pName);
            pstmt.setInt(2, currentPlayerLocation);
            pstmt.executeUpdate();

            // 구매자의 예산 차감
            pstmt = con.prepareStatement(updateBudgetSQL);
            pstmt.setInt(1, r);
            pstmt.setInt(2, playernum);
            pstmt.executeUpdate();

            // 트랜잭션 커밋
            con.commit();
            System.out.println(": 님 땅 구매 완료!");
        } catch (SQLException e) {
            try {
                // 예외 발생 시 롤백
                con.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
    }

    public void updatePlayerBudget(int playerNum, int bonusAmount) {
        String sql = "UPDATE PLAYER SET PBUDGET = PBUDGET + ? WHERE PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(2, playerNum);
            pstmt.setInt(1, bonusAmount);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("보너스 100 획득!");
            } else {
                System.out.println("보너스 100 획득 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
    }
    public void updatePlayerLocation(int playerNum, int newPosition) {
        String sql = "UPDATE PLAYER SET PLOCATION = ? WHERE PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newPosition);
            pstmt.setInt(2, playerNum);

            int result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
    }
    public void payRent(int currentPlayerNum, String targetPlayerName, int rentAmount) {
        String sql1 = "UPDATE PLAYER SET PBUDGET = PBUDGET - ? WHERE PLAYERNUM = ?";

        String sql2 = "UPDATE PLAYER SET PBUDGET = PBUDGET + ? WHERE PNAME = ?";

        try {
            // 현재 플레이어의 예산에서 대여료를 차감
            pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, rentAmount);
            pstmt.setInt(2, currentPlayerNum);
            int result1 = pstmt.executeUpdate();

            // 땅 주인의 예산에 대여료를 추가
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, rentAmount);
            pstmt.setString(2, targetPlayerName);
            int result2 = pstmt.executeUpdate();

            if (result1 > 0 && result2 > 0) {
                System.out.println("대여료를 성공적으로 지불했습니다.");
            } else {
                System.out.println("대여료 지불에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
    }
    public String getLandOwner(int landLocation) {
        String landOwner = ""; // 기본적으로 땅의 주인이 없는 경우를 -1로 설정하거나 다른 방식으로 표현할 수 있습니다.

        String sql = "SELECT B_OWNER FROM BMAP WHERE PLOCATION = ?";
        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, landLocation);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                landOwner = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }

        return landOwner;
    }

    public int locationCheck(int playerNum) {
        int whereIam = 0;
        String sql = "SELECT PLOCATION FROM PLAYER WHERE PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playerNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                whereIam = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
        return whereIam;
    }
    public int getPlayerBudget(int playerNum) {
        int budget = 0;
        String sql = "SELECT PBUDGET FROM PLAYER WHERE PLAYERNUM = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playerNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                budget = rs.getInt("PBUDGET");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
        return budget;
    }
    private void handleLocationEvent(int playerNum, int newLocation) {
        // 특정 위치에 도착했을 때 이벤트 처리 코드를 작성
        switch (newLocation) {
            case 1:
                System.out.println("출발 지점입니다.");
                break;
            case 10:
                System.out.println("무인도에서 한 턴 쉽니다.");
                break;
            case 13:
                System.out.println("13번 위치에 도착했습니다.");

                // 주사위를 한 번 더 던집니다.
                int secondDiceRoll = dice();
                System.out.println("두 번째 주사위 결과: " + secondDiceRoll);

                // 어떤 숫자든 추가 이동 가능
                int additionalMove = secondDiceRoll;
                System.out.println("주사위 결과에 따라 " + additionalMove + "칸 앞으로 이동합니다.");

                // 추가 이동 처리
                locationUpdate(additionalMove, playerNum);
                break;
            default:
                // 특정 위치 이벤트가 없을 경우 추가 처리 가능
                break;
        }
    }
    public void locationUpdate(int diceValue, int playerNum) {
        String updateSql = "UPDATE PLAYER SET PLOCATION = PLOCATION + ? WHERE PLAYERNUM = ?";
        String selectSql = "SELECT PLOCATION FROM PLAYER WHERE PLAYERNUM = ?";

        try {
            // 주사위 값을 이용하여 위치 업데이트
            pstmt = con.prepareStatement(updateSql);
            pstmt.setInt(1, diceValue);
            pstmt.setInt(2, playerNum);
            int updateResult = pstmt.executeUpdate();

            // 업데이트된 위치 가져오기
            pstmt = con.prepareStatement(selectSql);
            pstmt.setInt(1, playerNum);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int newLocation = rs.getInt("PLOCATION");

                // 위치에 따른 이벤트 처리
                handleLocationEvent(playerNum, newLocation);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                // 트랜잭션 종료 후 자원 해제 및 자동 커밋 설정 복원
                rs.close();
                pstmt.close();
                con.setAutoCommit(true);
            } catch (SQLException closeException) {
                throw new RuntimeException(closeException);
            }
        }
    }
}



