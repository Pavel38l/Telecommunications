package ru.vsu.telecom.data.dbloader.dao;

import ru.vsu.telecom.data.dbloader.ConnectionBuilder;
import ru.vsu.telecom.data.entity.ChannelPackage;
import ru.vsu.telecom.data.entity.Customer;
import ru.vsu.telecom.data.entity.Sex;
import ru.vsu.telecom.factory.InjectByType;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage ChannelPackage
 * @author Burdyug Pavel
 */
public class ChannelPackageDAO implements DAO<ChannelPackage> {
    private static final String SELECT_ID = "select * from channel_package where id=?";
    private static final String INSERT = "insert into channel_package (id, name, description) values (?, ?, ?)";
    @InjectByType
    ConnectionBuilder connectionBuilder;

    @Override
    public Optional<ChannelPackage> get(int id) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(ChannelPackage.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<ChannelPackage> getAll() {
        return null;
    }

    @Override
    public void save(ChannelPackage model) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ){
            int count = 1;
            preparedStatement.setLong(count++, model.getId());
            preparedStatement.setString(count++, model.getName());
            preparedStatement.setString(count++, model.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<ChannelPackage> models) {

    }
}
