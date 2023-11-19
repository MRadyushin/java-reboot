package ru.sberbank.edu.repository;


import ru.sberbank.edu.model.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CarDbRepositoryImpl implements CarRepository {
    private final Connection connection;
    private static final String CREATE_CAR_SQL = "INSERT INTO car (id, model) VALUES (?,?)";
    private static final String UPDATE_CAR_SQL = "UPDATE car SET model = ? WHERE id = ?";
    private static final String SELECT_CAR_BY_ID = "SELECT * FROM car WHERE id = ?";
    private static final String SELECT_CAR_BY_MODEL = "SELECT * FROM car WHERE model = ?";
    private static final String SELECT_ALL_CAR = "SELECT * FROM car";
    private static final String DELETE_CAR_BY_ID = "DELETE FROM car WHERE id = ?";
    private static final String DELETE_ALL_CAR = "DELETE FROM car";

    private final PreparedStatement createPreStmt;
    private final PreparedStatement updatePreStmt;
    private final PreparedStatement findByIdPreStmt;

    private final PreparedStatement findByModelPreStmt;
    private final PreparedStatement findAllCarsPreStmt;
    private final PreparedStatement deleteByIdPreStmt;
    private final PreparedStatement deleteAllCarsPreStmt;

    /**
     *
     * @param connection
     * @throws SQLException
     */
    public CarDbRepositoryImpl(Connection connection) throws SQLException {
        this.connection = connection;
        this.createPreStmt = connection.prepareStatement(CREATE_CAR_SQL);
        this.updatePreStmt = connection.prepareStatement(UPDATE_CAR_SQL);
        this.findByIdPreStmt = connection.prepareStatement(SELECT_CAR_BY_ID);
        this.findByModelPreStmt = connection.prepareStatement(SELECT_CAR_BY_MODEL);
        this.findAllCarsPreStmt = connection.prepareStatement(SELECT_ALL_CAR);
        this.deleteByIdPreStmt = connection.prepareStatement(DELETE_CAR_BY_ID);
        this.deleteAllCarsPreStmt = connection.prepareStatement(DELETE_ALL_CAR);
    }

    /**
     * Метод добавления и обновления данных
     * @param car
     * @return
     * @throws SQLException
     */
    @Override
    public Car createOrUpdate(Car car) throws SQLException {
        Optional<Car> optCar = findById(car.getId());
        if (optCar.isEmpty()) {
            createPreStmt.setString(1, car.getId());
            createPreStmt.setString(2, car.getModel());
            createPreStmt.executeUpdate();
        } else {
            updatePreStmt.setString(1, car.getModel());
            updatePreStmt.setString(2, car.getId());
            updatePreStmt.executeUpdate();
        }
        return car;
    }

    /**
     * Метод добавления всей коллекции
     * @param cars
     * @return
     */
    @Override
    public Set<Car> createAll(Collection<Car> cars) {

        Set<Car> carSet = new HashSet<>();

        cars.forEach(car ->
        {
            try {
                createOrUpdate(car);
                carSet.add(car);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }

    /**
     * Метод поиска всех элементов
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Car> findAll() throws SQLException {
        Set<Car> carSet = new HashSet<>();

        ResultSet resultSet = findAllCarsPreStmt.executeQuery();

        while (resultSet.next()) {
            Car car = new Car(resultSet.getString(1), resultSet.getString(2));
            carSet.add(car);
        }

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }

    /**
     * Метод для поиска элемента по id
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<Car> findById(String id) throws SQLException {
        // validation
        int rowsCount = countRowsById(id);
        if (rowsCount > 1) {
            throw new RuntimeException("Car with id = " + id + " was found many times (" + rowsCount + ").");
        } else if (rowsCount == 0) {
            return Optional.empty();
        }

        findByIdPreStmt.setString(1, id);
        ResultSet resultSet = findByIdPreStmt.executeQuery();

        resultSet.next();
        Car car = new Car(resultSet.getString(1), resultSet.getString(2));
        return Optional.of(car);
    }

    /**
     * Метод удаления элемента по id
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Boolean deleteById(String id) throws SQLException {
        deleteByIdPreStmt.setString(1, id);
        return deleteByIdPreStmt.execute();
    }

    /**
     * Метод удаления всех элементов
     * @return
     * @throws SQLException
     */
    public Boolean deleteAll() throws SQLException {
        return deleteAllCarsPreStmt.execute();
    }

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    private int countRowsById(String id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM car where id = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int rowCount = 0;
        while (resultSet.next()) {
            rowCount = resultSet.getInt(1);
        }
        return rowCount;
    }

    /**
     * Метод поиска по модели
     * @param model
     * @return
     * @throws SQLException
     */
    @Override
    public Set<Car> findByModel(String model) throws SQLException {

        Set<Car> carSet = new HashSet<>();

        findByModelPreStmt.setString(1, model);
        ResultSet resultSet = findByModelPreStmt.executeQuery();

        while (resultSet.next()) {
            Car car = new Car(resultSet.getString(1), resultSet.getString(2));
            carSet.add(car);
        }

        return carSet.isEmpty() ? Collections.emptySet() : carSet;
    }
}
