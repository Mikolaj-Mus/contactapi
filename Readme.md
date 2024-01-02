# Contact App
#### Backend

## Description
This repository contains the backend part of the Contact App,
implemented using Spring Boot. It collaborates with a PostgreSQL
database to manage contacts by providing functionalities for adding, modifying, and, in the future, deleting contacts. Additionally, it supports the capability to upload images for each contact.

The backend is seamlessly integrated with the 
UI part of the application, and the [repository](https://github.com/Mikolaj-Mus/contactapp)
for the frontend.

## Prerequisites
Make sure you have Docker installed on your machine. You can check this by entering the following command in the command prompt:

```bash
docker --version
```
If Docker is not installed, please refer to the official installation guides for your operating system:

- Docker Desktop for [Windows](https://docs.docker.com/desktop/install/windows-install/)
- Docker Desktop for [Mac](https://docs.docker.com/desktop/install/mac-install/)
- For Linux, follow the installation instructions for your specific distribution.
```bash
sudo apt-get update

sudo apt-get install docker.io
```

## Installation and Startup
For PostgreSQL:
```bash
docker pull postgres:14.10

docker network create db-network

docker run --name postgre-container --network db-network -e POSTGRES_PASSWORD=password postgres

docker run -it --rm --network db-network postgres psql -h postgre-container -U postgres
```
and create database after login
```bash
CREATE DATABASE contacts;
```
For Contact App Backend:
```bash
docker pull mikolajmus/contactapi
```
or
```bash
git clone https://github.com/mikolaj-mus/contactapi.git

cd contactapi

docker build -t mikolajmus/contactapi .
```
then
```bash
docker run -it --network db-network -p 8080:8080 mikolajmus/contactapi
```

## Usage
The backend exposes REST APIs for adding and editing contacts. 
You can test the application's functionality using Postman or 
in a [browser](http://localhost:8080/contacts).

## License

This project is currently not licensed. The project is continuously being developed by me, and it is a work in progress.
