package tw.idv.tibame.members.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import tw.idv.tibame.members.entity.Members;

public class MemberDAOImpl implements tw.idv.tibame.members.dao.MemberDAO{

	@Override
	public Boolean insert(Members entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Members> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members update(Members newMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectByLogin(String memberAcct, String password) {
		final String sql = "select * from Member where memberAcct =:memberAcct and password =:password";
		return getSession()
				.createNativeQuery(sql, Members.class)
				.setParameter("memberAcct", memberAcct)
				.setParameter("password", password)
				.uniqueResult();
	}

	@Override
	public Members selectOneByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectOneByMemberAcct(String memberAcct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberAcct(String memberAcct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberIdDate(String memberId, Timestamp regTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectManyByMemberIdRegStatusOpen(String memberId, String regStatusOpen) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Members selectByRecientPhoneNumDeliveryAddress(String lastRecipient, String lastPhoneNum,
			String lastDeliveryAddress) {
		// TODO Auto-generated method stub
		return null;
	}

}
