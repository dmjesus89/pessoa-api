# Avaliação

### Tarefas

- Crie uma API pública em que seja possível listar, cadastrar, deletar e atualizar pessoas (C.R.U.D)
- Crie um endpoint público de busca por nome ou CPF


### O que esperamos ?
- Encaminhar o link do seu repositório do Github
- Explicação de como rodar localmente e também como seria possível realizar o deploy.
- Testes
- Legibilidade
- Escalabilidade
- Commits pequenos
- Ver sua experiência codificando
- CLEAN CODE
- Keep it simple =] 

### Prazo de entrega
Após recebimento do e-mail o candidato terá 48hrs para entregar o projeto.


Rodar a aplicação em DEV: 
Executar a classe AvaliacaoApplication.java, como classe Java.

Rodar a aplicação em HOMO ou PROD: 
Para realizar deploy em outros ambientes deve ser criar outros arquivos de configuração na pasta resource, application-homo ou prod e configurar todas as informações necessário ( DataSource, Porta e etc)

Servidores:
A aplicação roda no ambiente de dev com o container tomcat já embutido, porém é possível subir em um servidor web 
Para gerar um pacote deve ser executado o maven com o comando ( mvn clean instal) ou pelo eclipse maven build (clean install)

Banco Utilizado: H2 em memória.


SWAGGER

http://localhost:8080/swagger-ui.html 

Realizado com as bibliotecas do io.springfox
	
Os testes da camada service foram mockados e usei a configuração do spring para levantar o container do spring.
Também pode ser criado um banco de teste, ou banco em mémoria.
