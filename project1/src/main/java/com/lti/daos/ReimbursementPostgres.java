package com.lti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lti.exceptions.IdMissingException;
import com.lti.models.Reimb;
import com.lti.util.ConnectionUtil;

public class ReimbursementPostgres implements ReimbursementDao {

	private static ReimbursementDao reimbursementPostgres;

	private ReimbursementPostgres() {
	};

	public static ReimbursementDao getReimbursementPostgres() {
		if (reimbursementPostgres == null) {
			reimbursementPostgres = new ReimbursementPostgres();
		}
		return reimbursementPostgres;
	}

	@Override
	public int addReimb(Reimb reimb) {
		String sql = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id) "
				+ "values (?, ?, ?, ?, ?);";
		int reimbId = -1;

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setFloat(1, reimb.getAmount());
			ps.setTimestamp(2, reimb.getSubmitted());
			ps.setInt(3, reimb.getAuthor());
			ps.setInt(4, reimb.getStatusId());
			ps.setInt(5, reimb.getTypeId());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next()) {
				reimbId = rs.getInt("reimb_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reimbId;
	}

	@Override
	public List<Reimb> getReimbByStatusAndUser(String status, int userId) {
		String sql = "select * from ers_reimbursement where reimb_author = ? and "
				+ "reimb_status_id in (select reimb_status_id from ers_reimbursement_status where reimb_status = ?);";
		List<Reimb> reimbs = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, userId);
			ps.setString(2, status);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int reimb_id = rs.getInt("reimb_id");
				float reimb_amount = rs.getFloat("reimb_amount");
				Timestamp reimb_submitted = rs.getTimestamp("reimb_submitted");
				Timestamp reimb_resolved = rs.getTimestamp("reimb_resolved");
				String reimb_description = rs.getString("reimb_description");
				byte[] reimb_receipt = rs.getBytes("reimb_receipt");
				int reimb_author = rs.getInt("reimb_author");
				int reimb_resolver = rs.getInt("reimb_resolver");
				int reimb_status_id = rs.getInt("reimb_status_id");
				int reimb_type_id = rs.getInt("reimb_type_id");

				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,
						reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id);

				reimbs.add(reimb);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reimbs;
	}

	@Override
	public int updateReimb(Reimb reimb) throws IdMissingException {
		if (reimb.getReimbId() == 0) {
			throw new IdMissingException();
		}
		
		int rowsChanged = 0;
		String sql = "update ers_reimbursement set reimb_amount = ?, reimb_submitted = ?, reimb_resolved = ?, reimb_description = ?, "
				+ "reimb_receipt = ?, reimb_author = ?, reimb_resolver = ?, reimb_status_id = ?, reimb_type_id = ? where reimb_id = ?;";

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setFloat(1, reimb.getAmount());
			ps.setTimestamp(2, reimb.getSubmitted());
			ps.setTimestamp(3, reimb.getResolved());
			ps.setString(4, reimb.getDescription());
			ps.setBytes(5, reimb.getReceipt());
			ps.setInt(6, reimb.getAuthor());
			ps.setInt(7, reimb.getResolver());
			ps.setInt(8, reimb.getStatusId());
			ps.setInt(9, reimb.getTypeId());
			ps.setInt(10, reimb.getReimbId());

			rowsChanged = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowsChanged;
	}

	@Override
	public List<Reimb> getReimbByStatus(String status) {
		String sql = "select * from ers_reimbursement where reimb_status_id in (select reimb_status_id from ers_reimbursement_status where reimb_status = ?);";
		List<Reimb> reimbs = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, status);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int reimb_id = rs.getInt("reimb_id");
				float reimb_amount = rs.getFloat("reimb_amount");
				Timestamp reimb_submitted = rs.getTimestamp("reimb_submitted");
				Timestamp reimb_resolved = rs.getTimestamp("reimb_resolved");
				String reimb_description = rs.getString("reimb_description");
				byte[] reimb_receipt = rs.getBytes("reimb_receipt");
				int reimb_author = rs.getInt("reimb_author");
				int reimb_resolver = rs.getInt("reimb_resolver");
				int reimb_status_id = rs.getInt("reimb_status_id");
				int reimb_type_id = rs.getInt("reimb_type_id");

				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,
						reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id);

				reimbs.add(reimb);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reimbs;
	}

	@Override
	public List<Reimb> getReimbByUser(int userId) {
		String sql = "select * from ers_reimbursement where reimb_author = ?;";
		List<Reimb> reimbs = new ArrayList<>();

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				int reimb_id = rs.getInt("reimb_id");
				float reimb_amount = rs.getFloat("reimb_amount");
				Timestamp reimb_submitted = rs.getTimestamp("reimb_submitted");
				Timestamp reimb_resolved = rs.getTimestamp("reimb_resolved");
				String reimb_description = rs.getString("reimb_description");
				byte[] reimb_receipt = rs.getBytes("reimb_receipt");
				int reimb_author = rs.getInt("reimb_author");
				int reimb_resolver = rs.getInt("reimb_resolver");
				int reimb_status_id = rs.getInt("reimb_status_id");
				int reimb_type_id = rs.getInt("reimb_type_id");

				Reimb reimb = new Reimb(reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,
						reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id);

				reimbs.add(reimb);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reimbs;
	}

	@Override
	public int deleteReimb(int reimbId) {
		int rowsChanged = 0;
		String sql = "delete from ers_reimbursement where reimb_id = ?;";
		
		try(Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, reimbId);
			
			rowsChanged = ps.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rowsChanged;
	}

}
