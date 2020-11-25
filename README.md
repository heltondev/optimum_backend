# Manager - Personal Data Storage

## About

* Consists in an API protected by JWT Tokenization approach which provides endpoints to Create, Read, Update and Delete items from the database. 

## Table of contents

Use for instance <https://github.com/heltondev/password-java-api>:

> * [Password Manager API](#title--repository-name)
>   * [About](#about)
>   * [Table of contents](#table-of-contents)
>   * [Requirements](#requirements)
>   * [Installation](#installation)
>   * [Usage](#usage)
>   * [License](#license)
>   * [Notes](#notes)

### Requirements

> * Java SDK
> * MySQL

## Installation
> * Download and install Java SDK
>   * See downloads page [here](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)
> * Set up MySQL on your local environment
>   * See how to set up MySQL on you local environment [here](<https://dev.mysql.com/doc/mysql-getting-started/en/>)
>   * Set up database connection on application.properties file under resources' folder by replacing the following data:
>       * spring.datasource.<b>url</b>
>       *  spring.datasource.<b>username</b>
>       *  spring.datasource.<b>password</b>

## Usage
Choosing an IDE of your preference and run the application from there, the API should provide endpoints as following:

> * <b>/authenticate</b>
>   * <b>POST</b>
>       * Expected "username" and "password" as payload
>       * Returns a token as a string to be used as Authorization Bearer
>
> * Authentication Request
> ```json
> {
>    "username": "email@email.com",
>    "password": "13e24f95474cb15d631b793e24eb32235a337d621b11111426ffb2ddee6c119"
> }
>```
> * Authentication Response
> ```json 
> {
>  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOi1111xvbGl2ZXI4NkBnbWFpbC5jb20iLCJleHAiOjE2MDU5MTM1OTcsImlhdCI6MTYwNTg5NTU5N30.vikg4oNGZaXB9K4F31N12N6C76JCgAhX106Ii91qT4I-0d0-L1_fQLCRK0dESBQmmQE6LWdcNvN9O-fQyLH5fQ"
> }
> ```
---
> * <b>/api/v1/users</b>
>   * <b>GET</b>
>       * Returns a collection of all items stored on the database
>   * <b>POST</b>
>       * Insert an item on the database
>
> * <b>/api/v1/users/{id}</b>
>   * <b>GET</b>
>       * Returns the item stored on the database where the ID matches with the criteria
>   * <b>PUT</b>
>       * Update the item stored on the database where the ID matches with the criteria
>   * <b>DELETE</b>
>       * Delete the item stored on the database where the ID matches with the criteria
> * User Request
> ```json
> {
>    "name": "John Doe",
>    "username": "email@email.com",
>    "password": "13e24f95474cb15d631b793e24eb32235a337d621b17ac34587ffb2ddee6c132"
> }
>```
> * User Response
> ```json 
> [
>    {
>        "id": 1,
>        "name": "John Doe",
>        "username": "email@email.com",
>        "createdAt": "2020-11-20T03:21:42.000+00:00",
>        "updatedAt": "2020-11-20T03:21:42.000+00:00"
>    }
> ]
> ```
---
> * <b>/api/v1/customers</b>
>   * <b>GET</b>
>       * Returns a collection of all items stored on the database
>   * <b>POST</b>
>       * Insert an item on the database
>
> * <b>/api/v1/customers/cpf/{cpf}</b>
>   * <b>GET</b>
>       * Returns a collection of all items stored on the database
> * <b>/api/v1/customers/zipcode/{zipcode}</b>
>   * <b>GET</b>
>       * Returns a collection of all items stored on the database
> * <b>/api/v1/customers/{id}</b>
>   * <b>GET</b>
>       * Returns the item stored on the database where the ID matches with the criteria
>   * <b>PUT</b>
>       * Update the item stored on the database where the ID matches with the criteria
>   * <b>DELETE</b>
>       * Delete the item stored on the database where the ID matches with the criteria
> * User Request
> ```json
> {
>        "name": "John Doe",
>        "dateOfBirth": "1986-01-01T16:00:00.000+00:00",
>        "state": "SP",
>        "city": "Sao Paulo",
>        "zipcode": "00000000",
>        "cpf": "00000000000",
>        "contacts": [
>            {
>                "email": "john@doe.com",
>                "phone": "1199990000",
>                "skypeId": "john.doe"
>            },
>            {
>                "email": "john2@doe.com",
>                "phone": "1199998888",
>                "skypeId": "john.doe2"
>            }
>        ]
> }
>```
> * User Response
> ```json
> {
>        "id": 1,
>        "name": "John Doe",
>        "dateOfBirth": "1986-01-01T16:00:00.000+00:00",
>        "state": "SP",
>        "city": "Sao Paulo",
>        "zipcode": "00000000",
>        "cpf": "00000000000",
>        "contacts": [
>            {
>                "email": "john@doe.com",
>                "phone": "1199990000",
>                "skypeId": "john.doe"
>            },
>            {
>                "email": "john2@doe.com",
>                "phone": "1199998888",
>                "skypeId": "john.doe2"
>            }
>        ]
> }
>```
---
## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Notes
> * Database
>  * The project is using MySQL dialect 8 here with <b>spring.jpa.show-sql=true</b> to see query execution. Notice, <b>spring.jpa.hibernate.ddl-auto=update</b>. 
Which will Create, update and delete the tables/database — Entity automatically. <br>
That is the reason why project has registered the Entity with JPA using <b>@EntityListeners(AuditingEntityListener.class)</b>.

## Tech Stack
> * [Java](https://www.java.com/en/download/help/index.html)
> * [MySQL](https://www.mysql.com/)
> * [Maven](https://maven.apache.org/)
> * [SpringBoot](https://spring.io/projects/spring-boot)
> * [Hibernate](https://hibernate.org/)
> * [Lombok](https://projectlombok.org/)
> * [JUnit](https://junit.org/junit5/)
> * [Mockito](https://site.mockito.org/)
> * [JPA](https://spring.io/projects/spring-data-jpa)
> * [JWT](https://jwt.io/)