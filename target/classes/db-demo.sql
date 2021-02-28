-- CONNECT 'jdbc:derby:cash_machine;create=true;user=root;password=root12345';

SET NAMES utf8;

DROP DATABASE IF EXISTS cash_machine;
CREATE DATABASE cash_machine CHARACTER SET utf8 COLLATE utf8_bin;
USE cash_machine;

CREATE TABLE user(
                     user_id INT NOT NULL AUTO_INCREMENT KEY,
                     user_name VARCHAR(255) NOT NULL,
                     user_surname VARCHAR(255) NOT NULL,
                     user_login VARCHAR(30) UNIQUE KEY,
                     user_password VARCHAR(60) UNIQUE NOT NULL,
                     role VARCHAR(20) DEFAULT 'CLIENT'
)ENGINE InnoDB DEFAULT CHARSET= UTF8MB4;

CREATE TABLE checks (
                        checks_id BIGINT NOT NULL AUTO_INCREMENT KEY,
                        checks_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        checks_status VARCHAR(12) DEFAULT 'CREATED',
                        user_login VARCHAR(30),
                        checks_price DOUBLE NOT NULL DEFAULT 0,
                        FOREIGN KEY (user_login) REFERENCES user(user_login)
                            ON UPDATE CASCADE
                            ON DELETE SET NULL
) ENGINE InnoDB DEFAULT CHARSET= UTF8MB4;

CREATE TABLE product(
                        product_code BIGINT NOT NULL UNIQUE KEY,
                        product_name VARCHAR(255) UNIQUE NOT NULL KEY,
                        product_price DOUBLE NOT NULL DEFAULT 0,
                        product_quantity INTEGER NOT NULL DEFAULT 0,
                        product_weight DOUBLE NOT NULL DEFAULT 0,
                        product_weightSold BOOLEAN DEFAULT false
) ENGINE = InnoDB DEFAULT CHARSET= UTF8MB4;

CREATE TABLE product_check(
                              checks_id BIGINT,
                              product_code BIGINT,
                              product_name VARCHAR(255),
                              product_check_quantity INTEGER NOT NULL DEFAULT 0,
                              product_check_weight DOUBLE NOT NULL DEFAULT 0,
                              product_weightSold BOOLEAN,
                              check_product_price DOUBLE NOT NULL DEFAULT 0,
                              FOREIGN KEY (checks_id) REFERENCES checks(checks_id)
                                  ON UPDATE CASCADE
                                  ON DELETE SET NULL,
                              FOREIGN KEY (product_code) REFERENCES product(product_code)
                                  ON UPDATE CASCADE
                                  ON DELETE SET NULL,
                              FOREIGN KEY (product_name) REFERENCES product(product_name)
                                  ON UPDATE CASCADE
                                  ON DELETE SET NULL
) ENGINE = InnoDB DEFAULT CHARSET= UTF8MB4;

CREATE TABLE report(
                       report_id BIGINT NOT NULL AUTO_INCREMENT KEY,
                       report_type VARCHAR(1) DEFAULT 'X',
                       report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       report_cash_before DOUBLE DEFAULT 0,
                       report_cash_after DOUBLE DEFAULT 0,
                       report_total DOUBLE DEFAULT 0
)ENGINE InnoDB DEFAULT CHARSET= UTF8MB4;