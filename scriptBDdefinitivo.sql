-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.3-beta1
-- PostgreSQL version: 11.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: "smartStreet" | type: DATABASE --
-- DROP DATABASE IF EXISTS "smartStreet";
CREATE DATABASE "smartStreet"
	ENCODING = 'UTF8'
	LC_COLLATE = 'Spanish_Spain.1252'
	LC_CTYPE = 'Spanish_Spain.1252'
	TABLESPACE = pg_default
	OWNER = postgres;
-- ddl-end --


-- object: public."Zona" | type: TABLE --
-- DROP TABLE IF EXISTS public."Zona" CASCADE;
CREATE TABLE public."Zona" (
	"Codigo_Ciudad" smallint NOT NULL,
	"ID" smallint NOT NULL,
	"Nombre" character varying NOT NULL,
	CONSTRAINT "Zona_pk" PRIMARY KEY ("ID","Codigo_Ciudad")

);
-- ddl-end --
ALTER TABLE public."Zona" OWNER TO postgres;
-- ddl-end --

-- object: public."Ciudad" | type: TABLE --
-- DROP TABLE IF EXISTS public."Ciudad" CASCADE;
CREATE TABLE public."Ciudad" (
	"Codigo" smallint NOT NULL,
	"Nombre" character varying(50) NOT NULL,
	"Pais" character varying(40) NOT NULL,
	CONSTRAINT "Ciudad_pk" PRIMARY KEY ("Codigo")

);
-- ddl-end --
ALTER TABLE public."Ciudad" OWNER TO postgres;
-- ddl-end --

-- object: public."Calle" | type: TABLE --
-- DROP TABLE IF EXISTS public."Calle" CASCADE;
CREATE TABLE public."Calle" (
	"Codigo_Ciudad_Zona" smallint NOT NULL,
	"ID_Zona" smallint NOT NULL,
	"Nombre" character varying(70) NOT NULL,
	CONSTRAINT "Calle_pk" PRIMARY KEY ("Nombre","ID_Zona","Codigo_Ciudad_Zona")

);
-- ddl-end --
ALTER TABLE public."Calle" OWNER TO postgres;
-- ddl-end --

-- object: public."Registro" | type: TABLE --
-- DROP TABLE IF EXISTS public."Registro" CASCADE;
CREATE TABLE public."Registro" (
	"Codigo_Ciudad_Zona_Calle_Sensor" smallint NOT NULL,
	"ID_Zona_Calle_Sensor" smallint NOT NULL,
	"Nombre_Calle_Sensor" character varying(70) NOT NULL,
	"Tipo_Sensor" character varying(50) NOT NULL,
	"MarcaTemporal" timestamp NOT NULL,
	"UnidadMedida" character varying(50),
	"Valor" float NOT NULL,
	CONSTRAINT "Registro_pk" PRIMARY KEY ("MarcaTemporal","Tipo_Sensor","Nombre_Calle_Sensor","ID_Zona_Calle_Sensor","Codigo_Ciudad_Zona_Calle_Sensor")

);
-- ddl-end --
ALTER TABLE public."Registro" OWNER TO postgres;
-- ddl-end --

-- object: public."HoraPunta" | type: TABLE --
-- DROP TABLE IF EXISTS public."HoraPunta" CASCADE;
CREATE TABLE public."HoraPunta" (
	"Codigo_Ciudad_Zona_Calle" smallint NOT NULL,
	"ID_Zona_Calle" smallint NOT NULL,
	"Nombre_Calle" character varying(70) NOT NULL,
	"HoraInicio" time NOT NULL,
	"HoraFin" time NOT NULL,
	CONSTRAINT "HoraPunta_pk" PRIMARY KEY ("HoraInicio","HoraFin","Nombre_Calle","ID_Zona_Calle","Codigo_Ciudad_Zona_Calle")

);
-- ddl-end --
ALTER TABLE public."HoraPunta" OWNER TO postgres;
-- ddl-end --

-- object: public."Sensor" | type: TABLE --
-- DROP TABLE IF EXISTS public."Sensor" CASCADE;
CREATE TABLE public."Sensor" (
	"Codigo_Ciudad_Zona_Calle" smallint NOT NULL,
	"ID_Zona_Calle" smallint NOT NULL,
	"Nombre_Calle" character varying(70) NOT NULL,
	"Tipo" character varying(50) NOT NULL,
	"MarcaTemporal" timestamp NOT NULL,
	"UnidadMedida" character varying(50),
	"Valor" float NOT NULL,
	CONSTRAINT "Sensor_pk" PRIMARY KEY ("Tipo","Nombre_Calle","ID_Zona_Calle","Codigo_Ciudad_Zona_Calle")

);
-- ddl-end --
ALTER TABLE public."Sensor" OWNER TO postgres;
-- ddl-end --

-- object: "Calle_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Sensor" DROP CONSTRAINT IF EXISTS "Calle_fk" CASCADE;
ALTER TABLE public."Sensor" ADD CONSTRAINT "Calle_fk" FOREIGN KEY ("Nombre_Calle","ID_Zona_Calle","Codigo_Ciudad_Zona_Calle")
REFERENCES public."Calle" ("Nombre","ID_Zona","Codigo_Ciudad_Zona") MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Sensor_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Registro" DROP CONSTRAINT IF EXISTS "Sensor_fk" CASCADE;
ALTER TABLE public."Registro" ADD CONSTRAINT "Sensor_fk" FOREIGN KEY ("Tipo_Sensor","Nombre_Calle_Sensor","ID_Zona_Calle_Sensor","Codigo_Ciudad_Zona_Calle_Sensor")
REFERENCES public."Sensor" ("Tipo","Nombre_Calle","ID_Zona_Calle","Codigo_Ciudad_Zona_Calle") MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Ciudad_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Zona" DROP CONSTRAINT IF EXISTS "Ciudad_fk" CASCADE;
ALTER TABLE public."Zona" ADD CONSTRAINT "Ciudad_fk" FOREIGN KEY ("Codigo_Ciudad")
REFERENCES public."Ciudad" ("Codigo") MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Zona_fk" | type: CONSTRAINT --
-- ALTER TABLE public."Calle" DROP CONSTRAINT IF EXISTS "Zona_fk" CASCADE;
ALTER TABLE public."Calle" ADD CONSTRAINT "Zona_fk" FOREIGN KEY ("ID_Zona","Codigo_Ciudad_Zona")
REFERENCES public."Zona" ("ID","Codigo_Ciudad") MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Calle_fk" | type: CONSTRAINT --
-- ALTER TABLE public."HoraPunta" DROP CONSTRAINT IF EXISTS "Calle_fk" CASCADE;
ALTER TABLE public."HoraPunta" ADD CONSTRAINT "Calle_fk" FOREIGN KEY ("Nombre_Calle","ID_Zona_Calle","Codigo_Ciudad_Zona_Calle")
REFERENCES public."Calle" ("Nombre","ID_Zona","Codigo_Ciudad_Zona") MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "grant_CU_eb94f049ac" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO postgres;
-- ddl-end --

-- object: "grant_CU_cd8e46e7b6" | type: PERMISSION --
GRANT CREATE,USAGE
   ON SCHEMA public
   TO PUBLIC;
-- ddl-end --


