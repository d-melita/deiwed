# DEIWED

- [Dependências](#dependências)
- [Correr a aplicação](#correr-a-aplicação)
- [Opções](#opções)

Projeto Realizado no âmbito da candidatura à BII BL204/2022, tendo como objetivo implementar um sistema muito simples de gestão de sessões da iniciativa DEI ÀS QUARTAS, sistema esse ligado à api FOODSTORE(documentação disponível [aqui](https://eindhoven.rnl.tecnico.ulisboa.pt/food-store/docs/#overview)).

## Dependências
Para correr a aplicação é necessário ter instalado:
- [Node.js](https://nodejs.org/en/), 19.0.1 ou superior
- [docker-compose](https://docs.docker.com/compose/install/), 2.12.2 ou superior
- [docker](https://docs.docker.com/engine/install/), 20.10.21 ou superior
- [maven](https://maven.apache.org/install.html), 3.8.6 ou superior
- [npm](https://www.npmjs.com/get-npm), 8.19.2 ou superior

As versões referidas acima foram as utilizadas no desenvolvimento da aplicação, mas versões mais antigas podem eventualmente funcionar.

## Correr a aplicação
Para correr a aplicação deve-se seguir os seguintes passos:
1. na diretoria src/database, correr o comando `docker-compose up -d --build`
2. na diretoria src/backend, correr o comando `mvn spring-boot:run`
3. na diretoria src/frontend, correr o comando `npm install` e depois `npm run start`
4. abrir o browser e ir para o endereço `localhost:8081` e começar a utilizar a aplicação

## Opções
- A aplicação assume que o input é sempre bem formatado contudo, com mais tempo, seria possível implementar um sistema de validação de input.

- Optou-se por permitir a alteração e a eliminação de pedidos de pratos à api FOODSTORE.

- Na home page é possível ir para as páginas de participantes, pratos e sessões de forma a ter uma página inicial mais limpa. 

- É possível navegar entre páginas clicando em "DEI ÀS QUARTAS" no canto superior direito.

- Com mais tempo ter-se-ia investido na parte visual da aplicação pelo que acabou por ficar algo bastante simples.