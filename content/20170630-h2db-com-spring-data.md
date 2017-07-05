---
layout: post
title:  H2 Database com Spring Data
date:   2017-06-30
category: Spring
image: /images/spring.png
---

##O Problema

Quando estamos começando um novo projeto e necessitamos de um banco de dados, muitas vezes perdemos um tempo desnecessário configurando bancos e criando a estrutura de dados. O intuíto deste post é mostrar que tudo pode ficar mais fácil ao se usar o **H2 Database**.

##[H2 Database Engine](http://www.h2database.com/html/main.html)

O **H2** é um banco de dados **Open Source** que funciona em memória com um console acessível pelo browser dentro do contexto da aplicação. Como ele funciona em memória todo seu armazenamento é **volátil**, ou seja, a cada sobe e desce da aplicação ele será reconstruído.
Seu intuíto é ser um banco de configuração rápida e fácil, visando favorecer a produtividade.
Outro banco de dados em memória é o [HSQLDB](http://hsqldb.org/), que também oferece os mesmos benefícios. Escolhemos o H2 pela sua fácil configuração em projetos Spring Boot.

##Maven
Para utilizar o **H2** em nosso projeto iremos necessitar de duas dependeências:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

###Spring Data
A dependência do **Spring Data** fará com que Spring dê suporte ao uso de Entidades e Repositorios para conexão com o banco. Trazendo consigo também **Hibernate** e **JPA**.

###H2
Na dependencia do **H2** definimos o escopo como `runtime`, pois necessitamos da biblioteca apenas durante a execução. Não utilizamos nenhuma classe deste pacote.


Adicionaremos também a dependência ao pacote de `Web` para que consigamos expor os dados através de endpoints **REST**, que serão mostrados posteriormente.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

##application.properties
Para configurar o banco de dados definiremos os seguintes parâmetros no `application.properties`:
```properties
server.port = 9000
#H2
# -Habilitará o acesso ao console do banco de dados
spring.h2.console.enabled=true 
# -URL na qual o console será habilitado
spring.h2.console.path=/h2
#Datasource
# -URL de acesso ao banco
spring.datasource.url=jdbc:h2:file:~/h2db
# -Usuário de acesso
spring.datasource.username=h2sa
# -Senha de acesso
spring.datasource.password=admin
# -Driver utilizado para conexão
spring.datasource.driver-class-name=org.h2.Driver
```

* Definimos a porta da aplicação para 9000 para evitar conflitos na 8080.
* As propriedades contidas dentro de **#H2** serviram somente se o acesso ao console for necessário. Caso queira utilizar uma IDE para acesso ao banco ele poderá ser desabilitado.
* As propriedades dentro de **#Datasource** configuram o acesso ao banco. `url`, `username` e `password` podem ser alterados de acordo com sua preferência.

##Entity
Configuraremos agora a entidade **Pessoa** com a seguinte estrutura:
```java
package br.com.fullstackninja.h2dbspringdata.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "PESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

}
```

* Pela anotação `@Entity` informamos o nome correspondente da nossa tabela
* Teremos no atributo `Id` nossa chave primária da tabela, que será auto incrementada ao usar a anotação `@GeneratedValue`


Por padrão ao rodar nossa aplicação, o **Spring Boot** identificando a existência do **H2** fará com que o **Hibernate** contido dentro do **Spring Data** se encarregue de criar todas nossas classes anotadas como `@Entity` no banco de dados.

##Repository
Para acessar a tabela pessoa iremos criar um Repositório Spring Data:
```java
package br.com.fullstackninja.h2dbspringdata.repository;

import br.com.fullstackninja.h2dbspringdata.entity.Pessoa;
import org.springframework.data.repository.CrudRepository;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
}
```

Como extendemos o `CrudRepository` já temos acesso aos metodos para criar e consultar os dados de **Pessoa**.

##REST Controller
Para que consigamos acessar nossas informações pela aplicação iremos criar nosso **Controller** próprio para a entidade **Pessoa**
```java
package br.com.fullstackninja.h2dbspringdata.controller;

import br.com.fullstackninja.h2dbspringdata.entity.Pessoa;
import br.com.fullstackninja.h2dbspringdata.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @GetMapping
    public Iterable<Pessoa> list() {
        return repository.findAll();
    }

}
```

A anotação `@GetMapping` faz com que o método `list` possa ser acessada pelo método **GET** através da url `/pessoas` configurada na anotação `@RequestMapping` definida para a classe.

##Testando
1. Rode o projeto como Spring-Boot
    ```
    mvn spring-boot:run
    ```
2. Acesse o console do **H2** através da url <http://localhost:9000/h2>, faça login com o usuário e senha definidos no `application.properties`.

    ![Console do H2](/images/20170705/console-h2.jpg)

3. No console do **H2** insira informações na tabela **Pessoa**

    ```
    insert into pessoa (nome) values ('Pedro');
    ```

4. Acesse a url <http://localhost:9000/pessoas> para consultar pela aplicação o que foi cadastrado diretamente no console

    ![Resultado do Teste](/images/20170705/test.jpg)

##Código Fonte 
<a href="https://github.com/pedrohrr/fullstackninja.com.br/tree/master/projects/h2db-spring-data" class="btn btn-success">
	GitHub
</a>
