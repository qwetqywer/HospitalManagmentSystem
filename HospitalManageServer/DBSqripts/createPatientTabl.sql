CREATE TABLE `hospitalsystem`.`patient` (
  `idpatient` INT NOT NULL AUTO_INCREMENT,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `phonenumber` VARCHAR(13) NOT NULL,
  `idaddress` INT NOT NULL,
  `passportseries` VARCHAR(2) NOT NULL,
  `passportnumber` INT NOT NULL,
  `birthdaydate` DATE NOT NULL,
  `gender` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idpatient`),
  INDEX `idaddress_idx` (`idaddress` ASC) VISIBLE,
  CONSTRAINT `idpatient`
    FOREIGN KEY (`idpatient`)
    REFERENCES `hospitalsystem`.`outpatientcard` (`idoutpatientcard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FKidaddress`
    FOREIGN KEY (`idaddress`)
    REFERENCES `hospitalsystem`.`address` (`idaddress`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
