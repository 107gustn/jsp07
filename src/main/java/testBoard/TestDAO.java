package testBoard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.DBConnect;

public class TestDAO {
	
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public TestDAO() { //생성자 생성
		try {
			con = DBConnect.getConnection(); //DB 연결
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<TestDTO> list(int start, int end) {
		System.out.println("start : " + start);
		if(start == 0) {
			start = 1;
		}
		ArrayList<TestDTO> li = new ArrayList<TestDTO>();
		//String sql = "select * from paging";
		//String sql = "select * from paging order by num desc";
		String sql = "select B.* from(select rownum rn, A.* from(select * from paging order by num desc)A)B where rn between ? and ?";
		try {
			ps = con.prepareStatement(sql); //명령어 전송
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			rs = ps.executeQuery(); //결과값을 받음
			while(rs.next()) {
				TestDTO dto = new TestDTO();
				dto.setCount(rs.getInt("count"));
				dto.setNum(rs.getInt("num"));
				dto.setPdate(rs.getString("pdate"));
				dto.setTitle(rs.getString("title"));
				
				li.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return li;
	}
	
	public void insert(TestDTO dto) {
		String sql = "insert into paging(num, title, pdate, count) values(test_num.nextval, ?, sysdate, 0)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void count(String cnt) {
		String sql = "update paging set count = count + 1 where num = " + cnt;
		try {
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTotalPage() {
		String sql = "select count(*) from paging";
		int cnt = 0;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1); //DB에서 1값을 통해 갯수를 가져옴
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
	
	public PageCount pagingNum(int start) {
		if(start == 0) {
			start = 1;
		}
		PageCount pc = new PageCount();
		int pageNum = 3; //한 페이지당 보여질 글 개수
		int totalPage = getTotalPage(); //글 총 개수 얻어옴
		int allPage = 0; //총 페이지 개수
		allPage = totalPage / pageNum;
		if(totalPage % pageNum != 0) {
			allPage += 1; //페이지 하나 더 추가해줌
		}
		pc.setTotEndPage(allPage);
		pc.setStartPage( (start - 1) * pageNum + 1);
		pc.setEndPage(pageNum * start);
		return pc;
	}
}
