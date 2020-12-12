CREATE TABLE `hospitalsystem`.`employee` (
  `idemployee` INT NOT NULL AUTO_INCREMENT,
  `idpersonal_account` INT NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `idspecialty` INT NOT NULL,
  `birthdaydate` DATE NOT NULL,
  `passportseries` VARCHAR(2) NOT NULL,
  `passportnumber` INT NOT NULL,
  `idaddress` INT NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idemployee`),
  INDEX `idpersonal_account_idx` (`idpersonal_account` ASC) VISIBLE,
  INDEX `idspecialty_idx` (`idspecialty` ASC) VISIBLE,
  INDEX `idaddress_idx` (`idaddress` ASC) VISIBLE,
  CONSTRAINT `idpersonal_account`
    FOREIGN KEY (`idpersonal_account`)
    REFERENCES `hospitalsystem`.`personal_account` (`idpersonal_account`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idspecialty`
    FOREIGN KEY (`idspecialty`)
    REFERENCES `hospitalsystem`.`specialty` (`idspecialty`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idaddress`
    FOREIGN KEY (`idaddress`)
    REFERENCES `hospitalsystem`.`address` (`idaddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    ALTER TABLE `hospitalsystem`.`employee`
DROP FOREIGN KEY `idpersonal_account`;
ALTER TABLE `hospitalsystem`.`employee`
DROP COLUMN `idpersonal_account`,
DROP INDEX `idpersonal_account_idx` ;
;
ALTER TABLE `hospitalsystem`.`employee`
ADD CONSTRAINT `idpersonal_account`
  FOREIGN KEY (`idemployee`)
  REFERENCES `hospitalsystem`.`personal_account` (`idpersonal_account`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
