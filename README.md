### Pessoa API ###

### Configurações: ### 
Banco Utilizado: H2 em memória.
Jav: JDK8

###  Servidores: ### 
A aplicação roda localmente já com um container tomcat embutido, porém é possível subir em um servidor web 
Para gerar um pacote deve ser executado o comando maven ( mvn clean instal) na pasta raiz do projeto ou pela IDE eclipse maven build (clean install)

### Rodar a aplicação em DEV:  ### 
Executar a classe AvaliacaoApplication.java, como Java application pela IDE (Eclipse) - Run As Java Application.

### Rodar a aplicação em HOMO ou PROD: ### 
Para realizar deploy em outros ambientes deve ser criar outros arquivos de configuração na pasta resource, application-homo.yml ou application-prod.yml e configurar todas as informações necessário ( DataSource, Porta e etc)

### Swagger: ### 
http://localhost:8080/swagger-ui.html 
É possivel consultar a documentação e realizar as chamadas da api pelo swagger 

### Testes: ### 	
Os testes da camada service foram mockados e usei a configuração do spring para levantar o container do spring.
Também pode ser criado um banco de teste ou banco em mémoria.



### Exemplos de Request: ### 
