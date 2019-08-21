/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云PostgreSql
 Source Server Type    : PostgreSQL
 Source Server Version : 110002
 Source Host           : 59.110.161.8:5432
 Source Catalog        : mybatis_example
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110002
 File Encoding         : 65001

 Date: 21/08/2019 16:36:19
*/


-- ----------------------------
-- Sequence structure for account_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."account_id_seq";
CREATE SEQUENCE "public"."account_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for m_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."m_user_id_seq";
CREATE SEQUENCE "public"."m_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for product_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."product_id_seq";
CREATE SEQUENCE "public"."product_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for u_order_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."u_order_id_seq";
CREATE SEQUENCE "public"."u_order_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS "public"."account";
CREATE TABLE "public"."account" (
  "id" int4 NOT NULL DEFAULT nextval('account_id_seq'::regclass),
  "user_id" int8,
  "info" varchar(255) COLLATE "pg_catalog"."default",
  "ctime" int8,
  "utime" int8
)
;

-- ----------------------------
-- Table structure for m_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."m_user";
CREATE TABLE "public"."m_user" (
  "id" int4 NOT NULL DEFAULT nextval('m_user_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "pwd" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS "public"."product";
CREATE TABLE "public"."product" (
  "id" int4 NOT NULL DEFAULT nextval('product_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "price" numeric(10,2),
  "categories" int2
)
;

-- ----------------------------
-- Table structure for u_order
-- ----------------------------
DROP TABLE IF EXISTS "public"."u_order";
CREATE TABLE "public"."u_order" (
  "id" int4 NOT NULL DEFAULT nextval('u_order_id_seq'::regclass),
  "user_id" int8,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "price" numeric(10,2),
  "order_list" varchar(255) COLLATE "pg_catalog"."default",
  "ctime" int8,
  "utime" int8
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."account_id_seq"
OWNED BY "public"."account"."id";
SELECT setval('"public"."account_id_seq"', 2, false);
ALTER SEQUENCE "public"."m_user_id_seq"
OWNED BY "public"."m_user"."id";
SELECT setval('"public"."m_user_id_seq"', 21, true);
ALTER SEQUENCE "public"."product_id_seq"
OWNED BY "public"."product"."id";
SELECT setval('"public"."product_id_seq"', 2, false);
ALTER SEQUENCE "public"."u_order_id_seq"
OWNED BY "public"."u_order"."id";
SELECT setval('"public"."u_order_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table account
-- ----------------------------
ALTER TABLE "public"."account" ADD CONSTRAINT "account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table m_user
-- ----------------------------
ALTER TABLE "public"."m_user" ADD CONSTRAINT "m_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table product
-- ----------------------------
ALTER TABLE "public"."product" ADD CONSTRAINT "product_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table u_order
-- ----------------------------
ALTER TABLE "public"."u_order" ADD CONSTRAINT "u_order_pkey" PRIMARY KEY ("id");
