# How To: Implantação do Ambiente DimDim em Docker

Este guia descreve o passo a passo para a migração e execução do ambiente de desenvolvimento da aplicação DimDim utilizando containers Docker e banco de dados Oracle.

---

## 1. Objetivo

Demonstrar a containerização da aplicação e sua execução em ambiente isolado, garantindo:

* Comunicação entre serviços via rede Docker
* Persistência de dados
* Execução independente de ambiente local

---

## 2. Pré-requisitos

Antes de iniciar, certifique-se de possuir:

* Docker instalado e configurado
* Imagem `gvenzl/oracle-xe` disponível no Docker Hub
* Arquivo `.jar` da aplicação gerado na pasta `target/`

---

## 3. Configuração da Infraestrutura Docker

### 3.1 Criar a rede Docker

Para permitir a comunicação entre os containers:

```bash
docker network create cp2
```

---

### 3.2 Criar volume para persistência

Garante que os dados do banco não sejam perdidos:

```bash
docker volume create oracle_data
```

---

## 4. Execução dos Containers

### 4.1 Subir o banco de dados Oracle

```bash
docker run -d \
  --name oracle-usuario \
  --network cp2 \
  -p 1521:1521 \
  -v oracle_data:/opt/oracle/oradata \
  -e ORACLE_PASSWORD=senha \
  gvenzl/oracle-xe
```

Observação: o nome do container inclui o RM conforme regra do projeto.

---

### 4.2 Buildar e subir a API

```bash
# Build da imagem
docker build -t api-rm566503 .

# Execução do container
docker run -d \
  --name api-rm566503 \
  --network cp2 \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle-rm566503:1521/XEPDB1 \
  -e SPRING_DATASOURCE_USERNAME=usuario \
  -e SPRING_DATASOURCE_PASSWORD=senha\
  api-rm566503
```

---

## 5. Acesso à Aplicação

Após subir os containers, a API estará disponível em:

```
http://localhost:8080/expenses
```

Ou, em ambiente de nuvem (Azure):

```
http://20.151.202.7:8080/expenses
```

---

## 6. Testes de Aderência (CRUD)

Para validar o funcionamento da aplicação, execute comandos diretamente no banco:

```sql
-- INSERT
INSERT INTO expense (amount, category, expense_date, description)
VALUES (150.50, 'Docker', TO_DATE('2026-04-28', 'YYYY-MM-DD'), 'Teste Checkpoint');

-- UPDATE
UPDATE expense
SET amount = 200.00
WHERE id = 1;

-- DELETE
DELETE FROM expense
WHERE id = 1;
```

---

## 7. Considerações Técnicas

* O banco Oracle foi executado em container separado
* A aplicação se conecta ao banco via nome do container (`oracle-rm566503`)
* Foi utilizado volume Docker para persistência dos dados
* A comunicação entre serviços ocorre via rede Docker (`cp2`)

---

## 8. Resultado

* Ambiente containerizado com sucesso
* Integração entre aplicação e banco validada
* API funcional localmente e em nuvem (Azure)

---

## 9. Autores

Lucas Nunes Soares
Camily Vitória Pereira Maciel
Eduarda Weiss
