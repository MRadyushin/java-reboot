package ru.sberbank.edu;

import org.assertj.core.api.Assertions;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.*;
import org.assertj.db.type.Table;
import static org.assertj.db.api.Assertions.assertThat;
import ru.sberbank.edu.dbconnection.H2DbEmbedded;
import ru.sberbank.edu.model.Car;
import ru.sberbank.edu.repository.CarDbRepositoryImpl;
import ru.sberbank.edu.repository.CarRepository;

import java.sql.SQLException;
import java.util.*;

public class RepositoryTest {
    private CarRepository carRepository;
    private Table table;
    private JdbcConnectionPool dataSource;
    private static H2DbEmbedded h2DbEmbedded;

    // Test Data
    Car car = new Car("1", "AUDI");
    Car car2 = new Car("2", "BMW");
    Car car3 = new Car("3", "AURUS");
    Car car4 = new Car("4", "HAVAL");

    @BeforeAll
    static void initDb() throws SQLException {
        h2DbEmbedded = new H2DbEmbedded();
        H2DbEmbedded.initDb();
    }

    @BeforeEach
    void initAssertJDbTable() {
        try {
            carRepository = new CarDbRepositoryImpl(H2DbEmbedded.getConnection());
            dataSource = JdbcConnectionPool.create(h2DbEmbedded.getURL_MEM(), h2DbEmbedded.getUSER(), h2DbEmbedded.getPASSWD());
            table = new Table(dataSource, "car");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void deleteTableCar() throws Exception {
        dataSource.getConnection().createStatement().execute("DELETE FROM car");
        dataSource.getConnection().close();
    }

    @Test
    void createTest() throws SQLException {
        carRepository.createOrUpdate(car);

        assertThat(table).column(0)
                .value().isEqualTo(car.getId());
        assertThat(table).column(1)
                .value().isEqualTo(car.getModel());
    }

    @Test
    void updateTest() throws SQLException {
        carRepository.createOrUpdate(car);
        carRepository.createOrUpdate(new Car("1","NISSAN"));

        assertThat(table).column(1)
                .value().isEqualTo("NISSAN");
    }

    @Test
    void createAllTest() {
        Collection<Car> cc = new ArrayList<>();
        cc.add(car);
        cc.add(car2);
        cc.add(car3);
        cc.add(car4);

        carRepository.createAll(cc);

        assertThat(table).column(0)
                .value().isEqualTo(car.getId())
                .value().isEqualTo(car2.getId())
                .value().isEqualTo(car3.getId())
                .value().isEqualTo(car4.getId());

        assertThat(table).column(1)
                .value().isEqualTo(car.getModel())
                .value().isEqualTo(car2.getModel())
                .value().isEqualTo(car3.getModel())
                .value().isEqualTo(car4.getModel());

        assertThat(table).hasNumberOfRows(4);
    }

    @Test
    void findAllTest() throws SQLException {
        Collection<Car> cc = new ArrayList<>();
        cc.add(car);
        cc.add(car2);
        cc.add(car3);
        cc.add(car4);

        carRepository.createAll(cc);

        Set<Car> cars = carRepository.findAll();

        Assertions.assertThat(cars).containsAll(cc);
        Assertions.assertThat(cars.size()).isEqualTo(4);
    }

    @Test
    void findByIdTest() throws SQLException {
        carRepository.createOrUpdate(car);

        Assertions.assertThat(carRepository.findById(car.getId())).isEqualTo(Optional.of(car));
    }

    @Test
    void deleteByIdTest() throws SQLException {
        carRepository.createOrUpdate(car);

        carRepository.deleteById(car.getId());

        assertThat(table).column(0).isEmpty();
        assertThat(table).column(1).isEmpty();
    }

    @Test
    void deleteAllTest() throws SQLException {
        carRepository.createOrUpdate(car);
        carRepository.createOrUpdate(car2);

        carRepository.deleteAll();

        assertThat(table).isEmpty();
    }

    @Test
    void findByModelTest() throws SQLException {
        carRepository.createOrUpdate(car);

        Assertions.assertThat(carRepository.findByModel(car.getModel()).size()).isEqualTo(1);
    }

    @Test
    void findByModelNoDataTest() throws SQLException {
        Assertions.assertThat(carRepository.findByModel(car.getModel()).size()).isEqualTo(0);
    }
}