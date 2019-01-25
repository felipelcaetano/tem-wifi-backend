package br.com.temwifi.domains.infra.utils.factory;

import main.br.com.reklotericas.exception.InternalServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnFactory {

    private static final Logger LOGGER = LogManager.getLogger(MySQLConnFactory.class);
    private static final String LOCAL = "local";
    private static final String PROD = "prod";

    public static Connection getConn(String env) throws InternalServerErrorException {

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url;
            String username;
            String password;

            if(LOCAL.equalsIgnoreCase(env)) {
                username = "root";
                password = "feleca13";
                url = "jdbc:mysql://localhost:3306/reklotericas?useTimezone=true&serverTimezone=UTC";

            } else if(PROD.equalsIgnoreCase(env)) {
                username = "root";
                password = "ri{+{^0N41{}*(f";
                url = "jdbc:mysql:aurora-serverless-cluster.cluster-chgz15menifh.us-east-1.rds.amazonaws.com:3306/reklotericas?useTimezone=true&serverTimezone=UTC";

            } else {
                username = "sql10270541";
                password = "Kv9i7c3y43";
                url = "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10270541?useTimezone=true&serverTimezone=UTC";
            }

            connection = DriverManager.getConnection(url, username, password);

            if (connection == null) {
                LOGGER.error("Não foi possível conectar no banco de dados.");
                throw new InternalServerErrorException("Erro iterno");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            LOGGER.error("O driver expecificado nao foi encontrado.");
            LOGGER.error(e);
            throw new InternalServerErrorException("Erro iterno");

        } catch (SQLException e) {  //Não conseguindo se conectar ao banco
            LOGGER.error("Não foi possível conectar no banco de dados.");
            LOGGER.error(e);
            throw new InternalServerErrorException("Erro iterno");
        }
    }

    public static void main(String[] args) throws InternalServerErrorException {
        MySQLConnFactory.getConn("dev");
    }
}
