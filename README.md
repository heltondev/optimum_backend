# Manager - Personal Data Storage

## About

* Consists in an API protected by JWT Tokenization approach which provides endpoints to Create, Read, Update and Delete items from the database. 

## Table of contents

Use for instance <https://github.com/heltondev/optimum>:

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
>       *  `spring.datasource.url`
>       *  `spring.datasource.username`
>       *  `spring.datasource.password`

## Usage
Choosing an IDE of your preference and run the application from there, the API should provide endpoints as following:

> * <b>`/authenticate`</b>
>   * <b>`POST`</b>
>       * Expected "username" and "password" as payload
>       * Returns a token as a string to be used as Authorization Bearer
>
> * Authentication Request
> ```json
> {
>    "username": "test@test.com",
>    "password": "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92"
> }
>```
> * Authentication Response
> ```json 
> {
>  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOi1111xvbGl2ZXI4NkBnbWFpbC5jb20iLCJleHAiOjE2MDU5MTM1OTcsImlhdCI6MTYwNTg5NTU5N30.vikg4oNGZaXB9K4F31N12N6C76JCgAhX106Ii91qT4I-0d0-L1_fQLCRK0dESBQmmQE6LWdcNvN9O-fQyLH5fQ"
> }
> ```
---
> * <b>`/api/v1/users`</b>
>   * <b>`GET`</b>
>       * Returns a collection of all items stored on the database
>   * <b>`POST`</b>
>       * Insert an item on the database
>
> * <b>`/api/v1/users/{id}`</b>
>   * <b>`GET`</b>
>       * Returns the item stored on the database where the ID matches with the criteria
>   * <b>`PUT`</b>
>       * Update the item stored on the database where the ID matches with the criteria
>   * <b>`DELETE`</b>
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
> * <b>`/api/v1/customers`</b>
>   * <b>`GET`</b>
>       * Returns a collection of all items stored on the database
>   * <b>`POST`</b>
>       * Insert an item on the database
>
> * <b>`/api/v1/customers/cpf/{cpf}`</b>
>   * <b>`GET`</b>
>       * Returns a collection of all items stored on the database
> * <b>`/api/v1/customers/zipcode/{zipcode}`</b>
>   * <b>`GET`</b>
>       * Returns a collection of all items stored on the database
> * <b>`/api/v1/customers/{id}`</b>
>   * <b>`GET`</b>
>       * Returns the item stored on the database where the ID matches with the criteria
>   * <b>`PUT`</b>
>       * Update the item stored on the database where the ID matches with the criteria
>   * <b>`DELETE`</b>
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
>  * The project is using MySQL dialect 8 here with `spring.jpa.show-sql=true`to see query execution. Notice, `spring.jpa.hibernate.ddl-auto=create-drop`. 
Which will Create, update and delete the tables/database — Entity automatically. <br>
Be aware the tables will be dropped when application closed. To persists data please change it to `spring.jpa.hibernate.ddl-auto=update`<br>
That is the reason why project has registered the Entity with JPA using `@EntityListeners(AuditingEntityListener.class)`.
<br><br>
>  * To retrieve token the user passed in the request payload should exist in the database. In order to facilitate it, the application will automatically add a predefined user as below:
>       * username: `test@test.com`
>       * password: `8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92`
>       * Retrieve token request payload example:
```json
            {
                "username": "test@test.com",
                "password": "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92"
            }
```

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