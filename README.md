# Exercise Web CRUD application with MySQL

Access the page ->https://start.spring.io/, and generate a Spring boot project with the following characteristics:

We have an entity called FlowerEntity, which has the following properties:

- int id

- String name

- String country

We also have a DTO called FlowerDTO, which will have the same properties as the Branch entity, adding one:

- String FlowerType

This property, depending on the country of origin of the flower, must indicate whether it is "EU" or "Non-EU". To do this, you can have a private list in the same DTO (for example: List<String> countries), with the countries that are part of the EU.

Taking advantage of the JPA specification, you will have to persist the FlorEntity entity to a MySQL database, following the MVC pattern.

The advice is to use FlorDTO in the Controller, and FlorEntity in the Repository. The service layer will be in charge of translating between the two.

For this, depending on the main package, you will create a package structure, where you will place the classes you need:

- cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.controllers

- cat.itacademy.barcelonactiva.surnames.nom.s05.t01.n02.model.domain

- cat.itacademy.barcelonactiva.surnames.name.s05.t01.n02.model.dto

- cat.itacademy.barcelonactiva.surnames.nom.s05.t01.n02.model.services

- cat.itacademy.barcelonactiva.cognoms.nom.s05.t01.n02.model.repository

The class located in the controllers package (FlorController, for example), must be able to respond to the following requests to update and consult information:

http://localhost:9001/flower/add

http://localhost:9001/flower/update

http://localhost:9001/flower/delete/{id}

http://localhost:9001/flower/getOne/{id}

http://localhost:9001/flower/getAll

OpenApi links at: 
- http://localhost:9001/v3/api-docs
- http://localhost:9001/swagger-ui/index.html

Application tested with: 
 Junit5, Hamcrest and Mockito. 
 Coverage
  - controller 33%
  - domain 50%
  - dto 50%
  - repository 100%
  - service 100%
  - Total 55%


As you can see, in the application.properties file, you must configure that the port to be used is 9001.
