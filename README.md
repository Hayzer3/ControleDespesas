How To: Implantação do Ambiente DimDim em Docker
Este guia descreve o passo a passo para a migração e execução do ambiente de desenvolvimento da aplicação DimDim utilizando containers Docker nativos. A arquitetura foi desenhada para separar as responsabilidades entre um container de banco de dados Oracle e um container para a API Java.

1. Objetivo
Demonstrar a execução da aplicação em um ambiente isolado e containerizado, garantindo:

Comunicação entre serviços: Banco e API interligados via rede Docker customizada.

Persistência de dados: Uso de volumes para evitar perda de informações no banco.

Independência de ambiente: Execução baseada em imagens oficiais sem necessidade de Dockerfile customizado.

2. Pré-requisitos
Antes de iniciar, certifique-se de possuir:

Docker Desktop ou Engine instalado e configurado.

Imagem gvenzl/oracle-xe disponível no Docker Hub.

Arquivo .jar da aplicação gerado na pasta target/ (obtido após o comando mvn clean package).

3. Configuração da Infraestrutura Docker
3.1 Criar a rede Docker
Necessário para que os containers consigam se comunicar pelo nome (DNS interno do Docker).

Bash

docker network create cp2
3.2 Criar volume para persistência
Garante que os dados do Oracle sejam mantidos mesmo após o container ser removido.

Bash

docker volume create oracle_data
4. Execução dos Containers
4.1 Container do Banco de Dados (SQL)
Inicia o banco de dados Oracle XE. O nome do container deve seguir o padrão do projeto.

Bash

docker run -d \
  --name oracle-usuario \
  --network cp2 \
  -p 1521:1521 \
  -v oracle_data:/opt/oracle/oradata \
  -e ORACLE_PASSWORD=senha \
  gvenzl/oracle-xe
4.2 Container da Aplicação (Java)
Como o projeto não utiliza mais Dockerfile, iniciamos um container a partir da imagem oficial do Java, mapeando o arquivo .jar e passando as configurações via variáveis de ambiente.

Bash

docker run -d \
  --name api-rm566503 \
  --network cp2 \
  -p 8080:8080 \
  -v "${PWD}/target/dimdim-api.jar:/app/app.jar" \
  -e SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle-usuario:1521/XEPDB1 \
  -e SPRING_DATASOURCE_USERNAME=usuario \
  -e SPRING_DATASOURCE_PASSWORD=senha \
  eclipse-temurin:17-jre \
  java -jar /app/app.jar
Nota: Certifique-se de que o nome do arquivo em target/ corresponde ao caminho indicado no comando acima.

5. Acesso à Aplicação
Após o startup dos containers, a API estará disponível em:

Local: http://localhost:8080/expenses

Nuvem (Azure): http://20.151.202.7:8080/expenses

6. Testes de Aderência (CRUD)
Valide a integração entre os containers executando os comandos SQL abaixo diretamente no banco:

SQL

-- INSERT de teste para validação
INSERT INTO expense (amount, category, expense_date, description)
VALUES (150.50, 'Docker', TO_DATE('2026-04-28', 'YYYY-MM-DD'), 'Teste Checkpoint');

-- UPDATE para validar persistência
UPDATE expense
SET amount = 200.00
WHERE id = 1;

-- DELETE para encerrar o ciclo
DELETE FROM expense
WHERE id = 1;
7. Considerações Técnicas
Desacoplamento: A infraestrutura de banco é independente da aplicação.

DNS Docker: A API conecta-se ao Oracle utilizando o hostname oracle-usuario, resolvido automaticamente pela rede cp2.

Portabilidade: Ao remover o Dockerfile, o projeto utiliza imagens base padrão, facilitando a troca de versões do Java ou do Banco sem necessidade de novos builds de imagem.

8. Resultado
Ambiente containerizado com sucesso.

Integração entre aplicação e banco validada.

API funcional localmente e em nuvem (Azure).

9. Autores
Lucas Nunes Soares

Camily Vitória Pereira Maciel

Eduarda Weiss
