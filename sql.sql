	#Tabla para reporte
CREATE TABLE `res_app_reporte` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ticket_id` int(10) unsigned NOT NULL,
  `animal_id` int(11) NOT NULL,
  `condicion_id` int(11) NOT NULL,
  `reporte_latitud` double NOT NULL,
  `reporte_longitud` double NOT NULL,
  `reporte_foto` varchar(128) NOT NULL,
  `reporte_contenido` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userid_idx` (`user_id`),
  KEY `fk_ticketid_idx` (`ticket_id`),
  CONSTRAINT `fk_ticketid` FOREIGN KEY (`ticket_id`) REFERENCES `ost_ticket` (`ticket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_userid` FOREIGN KEY (`user_id`) REFERENCES `ost_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


	#Tabla para Usuarios

	CREATE TABLE `res_app_user` (
	  `user_id` int(10) NOT NULL,
	  `user_pass` varchar(128) NOT NULL,
	  `puntos` int(11) NOT NULL DEFAULT '0',
	  `estado` varchar(1) DEFAULT 'A',
	  `latitud` double DEFAULT '0',
	  `longitud` double NOT NULL DEFAULT '0',
	  `date_created` datetime NOT NULL,
	  `date_lastreport` datetime DEFAULT NULL,
	  `date_lastlogin` datetime NOT NULL,
	  PRIMARY KEY (`user_id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE DEFINER=`root`@`localhost` PROCEDURE `transaction_sp_createuser`(

IN _correo  VARCHAR(128) ,
IN _pass  VARCHAR(128),
IN _name VARCHAR(128),
IN _phone MEDIUMTEXT,
IN _lat DOUBLE,
IN _log DOUBLE,
OUT _userid INT,
OUT p_message VARCHAR(128)
)
BEGIN
  DECLARE uid INT;
  DECLARE cid  INT;
  DECLARE exit handler for sqlexception
  BEGIN
    -- ERROR
	GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
	@errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
	SET p_message = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
	SELECT p_message;
    rollback;
  END;

  

START TRANSACTION;
INSERT INTO ost_user(org_id,default_email_id,status, name,created ) VALUES(0,(SELECT MAX(id) +1 FROM ost_user_email),0, _name, NOW() );
SET uid = (SELECT LAST_INSERT_ID());
INSERT INTO ost_user_email(user_id,flags,address) VALUES(LAST_INSERT_ID(),0,_correo);
SET cid = (SELECT LAST_INSERT_ID());
Update  ost_user set default_email_id = LAST_INSERT_ID() where id = (select user_id from ost_user_email where id = LAST_INSERT_ID());
INSERT INTO ost_user__cdata(user_id,email,name,phone) VALUES(uid,_correo,_name,_phone);
INSERT INTO res_app_user (user_id, user_pass, puntos, estado, latitud, longitud,  date_created, date_lastlogin )VALUES(uid, _pass,0 ,'A', _lat, _log, NOW(), NOW()  );
SET _userid = (SELECT LAST_INSERT_ID());
SELECT p_message;

COMMIT;
END
	#tabla de  crear log de puntos

CREATE TABLE `res_reporte_puntos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(10) unsigned NOT NULL,
  `ticket_id` int(10) unsigned NOT NULL,
  `puntos` int(11) NOT NULL,
  `codigo_bono` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_secundary` (`user_id`,`date_created`),
  KEY `fk_log_ ticket_id_idx` (`ticket_id`),
  CONSTRAINT `fk_log_ ticket_id` FOREIGN KEY (`ticket_id`) REFERENCES `ost_ticket` (`ticket_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_log_userid` FOREIGN KEY (`user_id`) REFERENCES `ost_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;




	//Procedimiento para Crear reporte
USE `logic_osticket`;
DROP procedure IF EXISTS `resGuardar_Reporte`;

DELIMITER $$
USE `logic_osticket`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `resGuardar_Reporte`(
IN _animal_id INT ,
IN _condicion_id INT,
IN _uid INT,

IN _reporte_latitud DOUBLE,
IN _reporte_logintud DOUBLE, 
IN _reporte_ticket INT,
IN _foto_ruta VARCHAR(128),
IN _reporte_cuerpo mediumtext
)
BEGIN 
INSERT INTO `logic_osticket`.`res_app_reporte`
(
`animal_id`,
`condicion_id`,
`user_id`,
`reporte_latitud`,
`reporte_longitud`,
`ticket_id`,
`reporte_foto`,
`reporte_contenido`)
VALUES
(
_animal_id,
_condicion_id,
_uid,
_reporte_latitud,
_reporte_logintud,
_reporte_ticket,
_foto_ruta,
_reporte_cuerpo);

END$$

DELIMITER ;



	INSERT INTO `logic_osticket`.`res_codigo_bono`
(`codigo`,
`cantidad_puntos`)
VALUES
('B',4);
	INSERT INTO `logic_osticket`.`res_codigo_bono`
(`codigo`,
`cantidad_puntos`)
VALUES
('R',4);

	INSERT INTO `logic_osticket`.`res_codigo_motivo`
(`codigo`,
`cantidad_puntos`)
VALUES
('B',4);
	INSERT INTO `logic_osticket`.`res_codigo_motivo`
(`codigo`,
`cantidad_puntos`)
VALUES
('R',4);


#Trigger para guardar log de puntos luego de actualizar o cerrar estado

DELIMITER $$
CREATE TRIGGER tg_log_puntos
    AFTER UPDATE ON ost_ticket
    FOR EACH ROW 
BEGIN
	DECLARE _puntos INT;
    IF NEW.status_id !=  OLD.status_id THEN
    IF NEW.status_id =2 THEN
	SELECT
		cantidad_puntos
	INTO
		_puntos
	FROM
		res_codigo_bono
	WHERE
		codigo = 'B';
        
    UPDATE  res_app_user SET puntos = puntos+_puntos  WHERE user_id = OLD.user_id;
    
    
    INSERT INTO res_reporte_puntos( 
    user_id, 
    date_created, 
    puntos,
    ticket_id, 
    codigo_bono     )
    value(
    old.user_id,
    Now(),
    _puntos,
    old.ticket_id,
    'B'
    );
    END IF; 
    END IF;
END$$
DELIMITER ;


#al crear un tiquete
DELIMITER $$
CREATE TRIGGER tg_log_puntos_insert
    BEFORE INSERT ON ost_ticket
    FOR EACH ROW 
BEGIN
	DECLARE _puntos INT;
	SELECT
		cantidad_puntos
	INTO
		_puntos
	FROM
		res_codigo_bono
	WHERE
		codigo = 'R';
    UPDATE  res_app_user SET  date_lastreport= now(), puntos = puntos+_puntos  WHERE user_id = NEW.user_id;
    
    INSERT INTO res_reporte_puntos( 
    user_id, 
    date_created, 
    puntos,
    ticket_id, 
    codigo_bono )
    value(
    NEW.user_id,
    Now(),
    _puntos,
    NEW.ticket_id,
    'R'
    );

END$$
DELIMITER ;


#SP PARA REGISTRAR USUARIO
CREATE DEFINER=`root`@`localhost` PROCEDURE `transaction_sp_createuser`(

IN _correo  VARCHAR(128) ,
IN _pass  VARCHAR(128),
IN _name VARCHAR(128),
IN _phone MEDIUMTEXT,
IN _lat DOUBLE,
IN _log DOUBLE,
OUT _userid INT,
OUT p_message VARCHAR(128)
)
BEGIN
  DECLARE uid INT;
  DECLARE cid  INT;
  DECLARE exit handler for sqlexception
  BEGIN
    -- ERROR
	GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
	@errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
	SET p_message = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
	SELECT p_message;
    rollback;
  END;

  

START TRANSACTION;
INSERT INTO ost_user(org_id,default_email_id,status, name,created ) VALUES(0,(SELECT MAX(id) +1 FROM ost_user_email),0, _name, NOW() );
SET uid = (SELECT LAST_INSERT_ID());
INSERT INTO ost_user_email(user_id,flags,address) VALUES(LAST_INSERT_ID(),0,_correo);
SET cid = (SELECT LAST_INSERT_ID());
Update  ost_user set default_email_id = LAST_INSERT_ID() where id = (select user_id from ost_user_email where id = LAST_INSERT_ID());
INSERT INTO ost_user__cdata(user_id,email,name,phone) VALUES(uid,_correo,_name,_phone);
INSERT INTO res_app_user (user_id, user_pass, puntos, latitud, longitud,  date_created, date_lastlogin )VALUES(uid, _pass, _name,_phone,0 ,_lat, _log, NOW(), NOW()  );
SET _userid = (SELECT LAST_INSERT_ID());
SELECT p_message;

COMMIT;
END

-------------
CREATE DEFINER=`root`@`localhost` PROCEDURE `transaction_sp_updateUser`(
IN _iduser  INT,
IN _correo  VARCHAR(128) ,
IN _name VARCHAR(128),
IN _phone MEDIUMTEXT
)
BEGIN
  DECLARE default_mail INT;
  DECLARE mailid INT;
  DECLARE cid  INT;
  DECLARE  p_message VARCHAR(128);
  DECLARE exit handler for sqlexception
  BEGIN
    -- ERROR
	GET DIAGNOSTICS CONDITION 1 @sqlstate = RETURNED_SQLSTATE, 
	@errno = MYSQL_ERRNO, @text = MESSAGE_TEXT;
	SET p_message = CONCAT("ERROR ", @errno, " (", @sqlstate, "): ", @text);
	SELECT p_message;
    rollback;
  END;

START TRANSACTION;
	IF (SELECT EXISTS( SELECT   * FROM ost_user_email WHERE user_id =  _iduser AND address =_correo )=1) THEN
        SELECT   id INTO  mailid FROM ost_user_email WHERE user_id =  _iduser AND address =_correo;
        SELECT  default_email_id INTO default_mail FROM  ost_user WHERE id = _iduser;
        IF (default_mail=mailid ) THEN
			UPDATE ost_user SET name = _name, updated= now()  WHERE id = _iduser;
            UPDATE ost_user__cdata SET  name = _name, phone= _phone WHERE  user_id = _iduser;
		ELSE
        	UPDATE ost_user SET default_email_id = mailid,     name = _name, updated= now()  WHERE id = _iduser;
            UPDATE ost_user__cdata SET email= _correo, name = _name, phone= _phone WHERE  user_id = _iduser;
        END IF;
    ELSE
		IF (SELECT EXISTS( SELECT * FROM ost_user_email WHERE address =_correo )=0) THEN
			INSERT INTO ost_user_email(user_id,flags,address) VALUES(_iduser,0,_correo);
			SET cid = (SELECT LAST_INSERT_ID());
			UPDATE  ost_user SET default_email_id = cid,   name = _name, updated= now()  WHERE id = _iduser; 
			UPDATE ost_user__cdata SET email= _correo, name = _name, phone= _phone WHERE  user_id = _iduser;
        ELSE
			SET p_message ='Existe un  usuario asociado a este correo';
        END IF;
    
	END IF;
    SELECT p_message ;
COMMIT;
END