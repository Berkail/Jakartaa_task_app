package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTO.User;

public class UserDAOImpl implements UserDAO {
	private Connection connection;

	public UserDAOImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User save(User user) {
		String query = "INSERT INTO users (username, email, password, creation_dt, deletion_dt, last_conn_dt) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setTimestamp(4, Timestamp.valueOf(user.getCreatedDt()));
			stmt.setTimestamp(5, user.getDeletedDt() != null ? Timestamp.valueOf(user.getDeletedDt()) : null);
			stmt.setTimestamp(6, user.getLastConnDt() != null ? Timestamp.valueOf(user.getLastConnDt()) : null);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					user.setId(rs.getLong(1));
				}
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(User user) {
		String query = "UPDATE users SET username = ?, email = ?, creation_dt = ?, deletion_dt = ?, last_conn_dt = ? WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmail());
			stmt.setTimestamp(3, user.getCreatedDt() != null ? Timestamp.valueOf(user.getCreatedDt()) : null);
			stmt.setTimestamp(4, user.getDeletedDt() != null ? Timestamp.valueOf(user.getDeletedDt()) : null);
			stmt.setTimestamp(5, user.getLastConnDt() != null ? Timestamp.valueOf(user.getLastConnDt()) : null);
			stmt.setLong(6, user.getId());
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void delete(long userId) {
		String query = "DELETE FROM users WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getById(long userId) {
		String query = "SELECT * FROM users WHERE id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setLong(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
						rs.getString("password"),
						rs.getTimestamp("creation_dt") != null ? rs.getTimestamp("creation_dt").toLocalDateTime()
								: null,
						rs.getTimestamp("deletion_dt") != null ? rs.getTimestamp("deletion_dt").toLocalDateTime()
								: null,
						rs.getTimestamp("last_conn_dt") != null ? rs.getTimestamp("last_conn_dt").toLocalDateTime()
								: null,
						null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getByEmail(String email) {
		String query = "SELECT * FROM users WHERE email = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, email); // Set the email parameter in the query
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
						rs.getString("password"),
						rs.getTimestamp("creation_dt") != null ? rs.getTimestamp("creation_dt").toLocalDateTime()
								: null,
						rs.getTimestamp("deletion_dt") != null ? rs.getTimestamp("deletion_dt").toLocalDateTime()
								: null,
						rs.getTimestamp("last_conn_dt") != null ? rs.getTimestamp("last_conn_dt").toLocalDateTime()
								: null,
						null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				users.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
						rs.getString("password"), rs.getTimestamp("createdDt").toLocalDateTime(),
						rs.getTimestamp("deletedDt").toLocalDateTime(), rs.getTimestamp("lastConnDt").toLocalDateTime(),
						null)// Assuming no taskSpaces for simplicity in this example
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
