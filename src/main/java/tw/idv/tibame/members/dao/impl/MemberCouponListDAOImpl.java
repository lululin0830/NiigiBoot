package tw.idv.tibame.members.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.idv.tibame.members.dao.MemberCouponListDAO;
import tw.idv.tibame.members.entity.MemberCouponList;

public class MemberCouponListDAOImpl implements MemberCouponListDAO{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";
	
	private static final String INSERT_STMT = 
			"INSERT INTO Member_Coupon_List (member_id,coupon_code,coupon_status) VALUES (?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT member_id,coupon_code,coupon_status FROM Member_Coupon_List order by member_id";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM Member_Coupon_List where member_id = ? and coupon_code = ?";
	private static final String UPDATE = 
			"UPDATE Member_Coupon_List set member_id=?, coupon_code=?, coupon_status=?, where member_id = ?";
	@Override
	public void insert(MemberCouponList memberCouponList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memberCouponList.getMemberId());
			pstmt.setString(2, memberCouponList.getCouponCode());
			pstmt.setString(3, memberCouponList.getCouponStatus());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void updateCoupon(MemberCouponList memberCouponList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memberCouponList.getMemberId());
			pstmt.setString(2, memberCouponList.getCouponCode());
			pstmt.setString(3, memberCouponList.getCouponStatus());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
//	@Override
	public MemberCouponList findByPrimaryKey(String member_id, String coupon_code) {
		MemberCouponList memberCouponList = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, member_id);
			pstmt.setString(2, coupon_code);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				memberCouponList = new MemberCouponList();
				memberCouponList.setMemberId(rs.getString("memberId"));
				memberCouponList.setCouponCode(rs.getString("couponCode"));
				memberCouponList.setCouponStatus(rs.getString("couponStatus"));
				
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memberCouponList;
	}
	@Override
	public List<MemberCouponList> getAll() {
		List<MemberCouponList> list = new ArrayList<MemberCouponList>();
		MemberCouponList memberCouponList = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
		
				memberCouponList = new MemberCouponList();
				memberCouponList.setMemberId(rs.getString("memberId"));
				memberCouponList.setCouponCode(rs.getString("couponCode"));
				memberCouponList.setCouponStatus(rs.getString("couponStatus"));
				
				list.add(memberCouponList); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		MemberCouponListDAOImpl dao = new MemberCouponListDAOImpl();
		

//		Member_Coupon_ListVO mcl = new Member_Coupon_ListVO();
//		mcl.setMember_id("M123456709");
//		mcl.setCoupon_code("12344");
//		mcl.setCoupon_status("0");
//		dao.insert(mcl);
		
		

		MemberCouponList mcl1 = new MemberCouponList();
		mcl1.setMemberId("M223456789");
		mcl1.setCouponCode("1234567");
		mcl1.setCouponStatus("1");
//		dao.update(mcl1);
		
		

		MemberCouponList mcl2 = new MemberCouponList();
		 mcl2 = dao.findByPrimaryKey("M123456709","12344");
		System.out.print(mcl2.getMemberId()+",");
		System.out.print(mcl2.getCouponCode()+",");
		System.out.print(mcl2.getCouponStatus()+",");
		System.out.println("----------------");
		

		List<MemberCouponList> list = dao.getAll();
		for(MemberCouponList a : list) {
//			System.out.print(a.getMember_id()+",");
//			System.out.print(a.getCoupon_code()+",");
//			System.out.print(a.getCoupon_status()+",");
//			System.out.println();
		}
	}



}
