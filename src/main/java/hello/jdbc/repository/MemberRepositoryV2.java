package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;


/**
 * jdbc DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    public MemberRepositoryV2(DataSource dataSource ) {
        this.dataSource = dataSource;
    }


    /**
     * 회원 저장
     * @param member
     * @return
     * @throws SQLException
     */
    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();

            pstmt = con.prepareStatement(sql); // 데이터 베이스에 전달할 SQL과 파라미터로 전달할 데이터를 준비
            pstmt.setString(1, member.getMemberId()); // SQL의 첫번째 파라미터 세팅
            pstmt.setInt(2, member.getMoney()); // 두번째 파라미터

            pstmt.executeUpdate(); // 준비된 SQL을 커넥션을 통해 실제 데이터 베이스에 전달 // return int -> 영향받은 DB ROW 수

            return member;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;

        } finally {
            close(con, pstmt, null);
        }
    }


    /**
     * 회원 조회
     * @param memberId
     * @return
     */
    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));

                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = " + memberId);
            }
        } catch ( SQLException e) {
            log.info("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }


    public Member findById(Connection con, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" +
                        memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally{
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
        }
    }


    /**
     * 회원 수정
     * @param memberId
     * @param money
     * @throws SQLException
     */
    public void update(String memberId, int money) throws SQLException{
        String sql = "update member set money = ? where member_id = ?";
        
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);

        }catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally{
            close(con, pstmt, null);
        }
    }

    public void update(Connection con, String memberId, int money) throws SQLException{
        String sql = "update member set money = ? where member_id = ?";

        PreparedStatement pstmt = null;

        try {
            con = getConnection();

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);

        }catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }finally{
            JdbcUtils.closeStatement(pstmt);
        }
    }


    public void delete(String memberId) {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, memberId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.error("error", e);
        } finally{
            close(con, pstmt, null);
        }
    }

    /**
     * 리소스 정리 <p>
     *     리소스 정리는 항상 역순으로 해야 함<br>
     *     리소스 정리는 항상 수행되어야 함 => 안하면 커넥션이 끊어지지 않고 계속 유지 => 리소스 누수 ( 커넥션 부족으로 장애 발생 가능 )
     * @param con
     * @param stmt
     * @param rs
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get Connection={}, class={}", con, con.getClass());

        return con;
    }

}
