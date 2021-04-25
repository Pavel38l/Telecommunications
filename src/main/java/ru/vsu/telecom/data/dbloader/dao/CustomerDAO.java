package ru.vsu.telecom.data.dbloader.dao;

import ru.vsu.telecom.data.dbloader.ConnectionBuilder;
import ru.vsu.telecom.data.entity.Customer;
import ru.vsu.telecom.data.entity.Sex;
import ru.vsu.telecom.factory.InjectByType;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage Customer
 * @author Burdyug Pavel
 */
public class CustomerDAO implements DAO<Customer> {
    private static final String SELECT_ID = "select * from customer where id=?";
    private static final String INSERT = "insert into customer (id, full_name, date_of_birth, sex, passport_series_number) values (?, ?, ?, ?, ?)";
    @InjectByType
    ConnectionBuilder connectionBuilder;

    @Override
    public Optional<Customer> get(int id) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return Optional.of(Customer.builder()
                        .id(resultSet.getLong("id"))
                        .fullName(resultSet.getString("full_name"))
                        .dateOfBirth(resultSet.getDate("date_of_birth").toLocalDate())
                        .sex(Sex.valueOf(resultSet.getString("sex").toUpperCase()))
                        .passportSeriesNumber(resultSet.getInt("passport_series_number"))
                        .build());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void save(Customer model) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ){
            int count = 1;
            preparedStatement.setLong(count++, model.getId());
            preparedStatement.setString(count++, model.getFullName());
            preparedStatement.setDate(count++, Date.valueOf(model.getDateOfBirth()));
            preparedStatement.setString(count++, model.getSex().toString());
            preparedStatement.setInt(count++, model.getPassportSeriesNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveAll(List<Customer> models) {

    }
}
