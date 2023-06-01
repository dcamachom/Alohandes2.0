CREATE SEQUENCE alohandes_sequence;

CREATE TABLE CLIENTE
(
	Id NUMBER,
	Nombre varchar (255) NOT NULL,
	Correo varchar(255) NOT NULL,
	Telefono varchar (10) NOT NULL,
	TipoMiembro varchar(15) NOT NULL,
	CHECK (TipoMiembro in ('Estudiante', 'Profesor', 'Empleado', 'Egresado', 'PadreEstudiante')),
	CONSTRAINT Cliente_PK PRIMARY KEY (Id)
);
CREATE TABLE OPERADOR
(
	Id NUMBER,
	Nombre varchar (255) NOT NULL,
	TipoOperador varchar (21) NOT NULL,
	CHECK (TipoOperador in ('Estudiante','Vecino', 'Profesor', 'ServicioHotelero', 'Egresado', 'PadreEstudiante', 'ViviendaUniversitaria')),
	CONSTRAINT Operador_PK PRIMARY KEY (Id)
);
CREATE TABLE INMUEBLE
(
	Id NUMBER,
    Operador NUMBER,
	CostoBase numeric (7,0) NOT NULL,
	Estado varchar(15) NOT NULL,
	Tipo varchar (15) NOT NULL,
	FOREIGN KEY (Operador) REFERENCES operador(Id),
	CONSTRAINT Inmueble_PK PRIMARY KEY (Id),
	CHECK (Tipo in ('Suite', 'Semisuite', 'Estandar', 'Casa', 'Apartamento')),
	CHECK (Estado in ('Habilitada', 'Deshabilitada'))
);
CREATE TABLE SERVICIO
(
	Id NUMBER,
	Nombre varchar(20) NOT NULL, 
	Descripcion varchar(255) NOT NULL,
	CONSTRAINT Servicio_PK PRIMARY KEY (Id)
);
CREATE TABLE APARTAMENTO
(
    Id NUMBER,
	CantHabitaciones numeric (2,0) NOT NULL,
	Amoblado varchar(5) NOT NULL,
	FOREIGN KEY (Id) REFERENCES Inmueble(Id),
	CONSTRAINT Apartamento_PK PRIMARY KEY (Id)
);
CREATE TABLE CASA
(
    Id NUMBER,
	CantHabitaciones numeric (2,0) NOT NULL,
	Seguro varchar (255),
	FOREIGN KEY (Id) REFERENCES Inmueble(Id),
	CONSTRAINT Casa_PK PRIMARY KEY (Id)
);
CREATE TABLE HABITACION
(
    Id NUMBER,
	Capacidad numeric (2,0) NOT NULL,
	Compartida varchar(5) NOT NULL,
	Tipo varchar(9) NOT NULL,
	CHECK (Tipo in ('Suite', 'Estandar', 'Semisuite')),
	FOREIGN KEY (Id) REFERENCES Inmueble(Id),
	CONSTRAINT Habitacion_PK PRIMARY KEY (Id)
);
CREATE TABLE HOSTAL
(
    Id NUMBER,
	CantHabitaciones numeric (2,0) NOT NULL,
	RegistroCamaraComercio varchar (255) NOT NULL,
	RegistroSuperintendencia varchar (255) NOT NULL,
	HoraApertura varchar(255) NOT NULL,
	HoraCierre varchar(255) NOT NULL,
	FOREIGN KEY (Id) REFERENCES Operador(Id),
	CONSTRAINT Hostal_PK PRIMARY KEY (Id)
);
CREATE TABLE HOTEL
(
    Id NUMBER,
	CantHabitaciones numeric (2,0) NOT NULL,
	RegistroCamaraComercio varchar (255) NOT NULL,
	RegistroSuperintendencia varchar (255) NOT NULL,
	FOREIGN KEY (Id) REFERENCES Operador(Id),
	CONSTRAINT Hotel_PK PRIMARY KEY (Id)
);
CREATE TABLE PERSONANATURAL
(
    Id NUMBER,
	Correo varchar (255) NOT NULL,
	Telefono varchar (10) NOT NULL,
	FOREIGN KEY (Id) REFERENCES Operador(Id),
	CONSTRAINT PersonaNatural_PK PRIMARY KEY (Id)
);
CREATE TABLE RESERVACOLECTIVA
(
	Id NUMBER,
	TipoEvento varchar(30) NOT NULL,
	TipoAlojamiento varchar(30) NOT NULL,
	Cantidad number NOT NULL,
	CONSTRAINT ReservaColectiva_PK PRIMARY KEY (Id),
	Cancelado varchar(5) NOT NULL,
	CHECK (Cantidad>0),
	CHECK (TipoAlojamiento in ('Suite', 'Estandar','Semisuite', 'Apartamento','Casa'))
);


CREATE TABLE RESERVA
(
	Id NUMBER,
    Cliente NUMBER,
    Inmueble NUMBER,
	FechaInicio DATE NOT NULL,
	FechaFin DATE NOT NULL,
	Cancelado varchar(5) NOT NULL,
	ReservaColectiva NUMBER,
	FOREIGN KEY (Cliente) REFERENCES Cliente(Id),
	FOREIGN KEY (Inmueble) REFERENCES Inmueble(Id), 
	CONSTRAINT Reserva_PK PRIMARY KEY (Id), 
	FOREIGN KEY (ReservaColectiva) REFERENCES ReservaColectiva(Id) 
);


CREATE TABLE SERVICIOINMUEBLE
(
    Servicio NUMBER,
    Inmueble NUMBER,
	Incluido varchar(5) NOT NULL, 
	ValorAdicional numeric(6) NOT NULL,
	FOREIGN KEY (Servicio) REFERENCES Servicio(Id),
	FOREIGN KEY (Inmueble) REFERENCES Inmueble(Id),
	CONSTRAINT ServicioInmueble_PK PRIMARY KEY (Servicio, Inmueble)
);
CREATE TABLE SERVICIOSUSADOS
(
    Servicio NUMBER,
    Inmueble NUMBER,
    Reserva NUMBER,
	FOREIGN KEY (Servicio, Inmueble) REFERENCES ServicioInmueble(Servicio, Inmueble),
	FOREIGN KEY (Reserva) REFERENCES Reserva(Id),
	CONSTRAINT ServiciosUsados_PK PRIMARY KEY (Servicio, Inmueble, Reserva)
);
CREATE TABLE VIVIENDAUNIVERSITARIA
(
    Id NUMBER,
	CantHabitaciones numeric (3) NOT NULL,
	FOREIGN KEY (Id) REFERENCES Operador(Id),
	CONSTRAINT Vivienda_PK PRIMARY KEY (Id)
);

