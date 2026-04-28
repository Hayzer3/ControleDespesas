 Expenses API - Deploy em Nuvem (Azure)

Este projeto tem como objetivo demonstrar o deploy de uma aplicação Java com Spring Boot integrada a um banco de dados Oracle, utilizando containers Docker e execução em ambiente de nuvem (Microsoft Azure).

 Objetivo

Implementar e disponibilizar uma API REST em ambiente cloud, garantindo:

Execução em containers Docker
Separação de serviços (aplicação e banco)
Acesso público via IP
Persistência de dados em banco Oracle
 Arquitetura da Solução

A aplicação foi estruturada em dois containers:

Aplicação (Spring Boot) → responsável pelas regras de negócio e endpoints REST
Banco de Dados (Oracle) → responsável pela persistência dos dados

Ambos os containers foram orquestrados e executados em uma máquina virtual na Azure.

 Deploy na Azure

A aplicação está disponível publicamente no seguinte endereço:

 http://20.151.202.7:8080/expenses

 Execução com Docker

Para subir o projeto localmente:


 Endpoints disponíveis
 
 Listar todas as despesas
GET /expenses

 Criar nova despesa
POST /expenses

Exemplo de requisição:
{
  "amount": 150.75,
  "category": "Alimentação",
  "date": "2026-04-27",
  "description": "Almoço"
}
 Buscar por ID
 
GET /expenses/{id}
 Atualizar despesa
 
PUT /expenses/{id}
 Remover despesa
 
DELETE /expenses/{id}
 Banco de Dados


 Testes

Os testes da API podem ser realizados via:

Postman
Insomnia
Navegador (requisições GET)

 Resultados
Aplicação executando com sucesso em ambiente cloud
Containers funcionando de forma isolada
Comunicação entre aplicação e banco validada
API acessível publicamente via IP

Conclusão

O projeto atingiu o objetivo proposto, demonstrando a viabilidade de deploy de aplicações Java containerizadas em ambiente de nuvem, com integração a banco de dados Oracle e exposição de endpoints REST para consumo externo.
