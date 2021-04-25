package ru.vsu.telecom.data.dbloader.dao;

import ru.vsu.telecom.data.dbloader.ConnectionBuilder;
import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.factory.InjectByType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO to manage Contract
 * @author Burdyug Pavel
 */
public class ContractDAO implements DAO<Contract> {
    private static final String SELECT_ALL = "select * from contract";
    private static final String INSERT = "insert into contract (id, start_date, end_date, contract_number, customer_id, " +
            "contract_type, number_of_minutes, number_of_sms, traffic_size, connection_speed, channel_package_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    @InjectByType
    ConnectionBuilder connectionBuilder;

    @InjectByType
    CustomerDAO customerDAO;
    @InjectByType
    ChannelPackageDAO channelPackageDAO;

    @Override
    public Optional<Contract> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Contract> getAll() {
        final List<Contract> result = new ArrayList<>();


        try (
                Connection connection = connectionBuilder.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet resultSet = stmt.executeQuery(SELECT_ALL)) {
                while (resultSet.next()) {
                    result.add(
                            createContractFromResult(resultSet)
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(Contract model) {

    }

    @Override
    public void saveAll(List<Contract> models) {
        try (
                Connection connection = connectionBuilder.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ){
            for (Contract contract : models) {
                configStatementForContract(contract, preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return contract from ResultSet
     * @param resultSet resultSet of contract query
     * @return contract from ResultSet
     * @throws SQLException when parameters are not read correctly
     */
    private Contract createContractFromResult(ResultSet resultSet) throws SQLException {
        Contract contract;
        String contractType = resultSet.getString("contract_type");
        if (contractType.equals(MobileConnectContract.class.getSimpleName())) {
            MobileConnectContract specContract = MobileConnectContract.builder()
                    .numberOfMinutes(resultSet.getInt("number_of_minutes"))
                    .numberOfSms(resultSet.getInt("number_of_sms"))
                    .trafficSize(resultSet.getDouble("traffic_size"))
                    .build();
            contract = specContract;
        } else if (contractType.equals(WiredInternetContract.class.getSimpleName())) {
            WiredInternetContract specContract = WiredInternetContract.builder()
                    .connectionSpeed(resultSet.getDouble("connection_speed"))
                    .build();
            contract = specContract;
        } else {
            ChannelPackage channelPackage = channelPackageDAO.get(
                    (int) resultSet.getLong("channel_package_id")
            ).orElseThrow(SQLException::new);
            DigitalTelevisionContract specContract = DigitalTelevisionContract.builder()
                    .channelPackage(channelPackage)
                    .build();
            contract = specContract;
        }
        contract.setId(resultSet.getLong("id"));
        contract.setStartDate(resultSet.getDate("start_date").toLocalDate());
        contract.setEndDate(resultSet.getDate("end_date").toLocalDate());
        contract.setContractNumber(resultSet.getLong("contract_number"));
        // customer insert
        Customer customer = customerDAO.get(
                (int)resultSet.getLong("customer_id")
        ).orElseThrow(SQLException::new);
        contract.setCustomer(customer);
        return contract;
    }

    /**
     * Config statement for contract to insert
     * @param contract contract to insert
     * @param preparedStatement insert statement
     * @throws SQLException when query params are wrong
     */
    private void configStatementForContract(Contract contract, PreparedStatement preparedStatement) throws SQLException {
        int count = 1;
        preparedStatement.setLong(count++, contract.getId());
        preparedStatement.setDate(count++, Date.valueOf(contract.getStartDate()));
        preparedStatement.setDate(count++, Date.valueOf(contract.getEndDate()));
        preparedStatement.setLong(count++, contract.getContractNumber());
        if (customerDAO.get(contract.getCustomer().getId().intValue()).isEmpty()) {
            customerDAO.save(contract.getCustomer());
        }
        preparedStatement.setLong(count++, contract.getCustomer().getId());
        preparedStatement.setString(count++, contract.getClass().getSimpleName());
        String simpleClassName = contract.getClass().getSimpleName();
        if (simpleClassName.equals(MobileConnectContract.class.getSimpleName())) {
            MobileConnectContract specContract = (MobileConnectContract) contract;
            preparedStatement.setInt(count++, specContract.getNumberOfMinutes());
            preparedStatement.setInt(count++, specContract.getNumberOfSms());
            preparedStatement.setDouble(count++, specContract.getTrafficSize());
            preparedStatement.setNull(count++, Types.DOUBLE);
            preparedStatement.setNull(count++, Types.BIGINT);
        } else if (simpleClassName.equals(WiredInternetContract.class.getSimpleName())) {
            WiredInternetContract specContract = (WiredInternetContract) contract;
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.DOUBLE);
            preparedStatement.setDouble(count++, specContract.getConnectionSpeed());
            preparedStatement.setNull(count++, Types.BIGINT);
        } else {
            DigitalTelevisionContract specContract = (DigitalTelevisionContract) contract;
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.INTEGER);
            preparedStatement.setNull(count++, Types.DOUBLE);
            preparedStatement.setNull(count++, Types.DOUBLE);
            // channel package insert
            if (channelPackageDAO.get(specContract.getChannelPackage().getId().intValue()).isEmpty()) {
                channelPackageDAO.save(specContract.getChannelPackage());
            }
            preparedStatement.setLong(count++, specContract.getChannelPackage().getId());
        }
    }

}
