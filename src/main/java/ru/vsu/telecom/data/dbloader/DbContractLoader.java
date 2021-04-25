package ru.vsu.telecom.data.dbloader;
import ru.vsu.telecom.data.dbloader.dao.ContractDAO;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.factory.InjectByType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Save contracts to database and load from db
 * @author Burdyug Pavel
 */
public class DbContractLoader implements ContractLoader {
    @InjectByType
    ContractDAO contractDAO;

    @InjectByType
    ConnectionBuilder connectionBuilder;

    @Override
    public void save(ContractRepository contractRepository) {
        clearDb();
        contractDAO.saveAll(contractRepository.getAll());
    }

    @Override
    public void load(ContractRepository contractRepository) {
        contractRepository.clear();
        contractRepository.addAll(contractDAO.getAll());
    }

    /**
     * for clear db before save contracts
     */
    private void clearDb() {
        try (
                Connection connection = connectionBuilder.getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("truncate contract; truncate customer cascade; truncate channel_package cascade");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
