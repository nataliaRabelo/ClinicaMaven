# Projeto Clinica Web <!-- omit in toc -->

O Clinica Web trata-se de um sistema desenvolvido em disciplina de Desenvolvimento Web - UFF para gerenciamento de uma clínica que está sendo testado para fins de um requisito parcial de avaliação na disciplina Qualidade e Teste. 

* Objetivo do projeto:
  Fomentar o aprendizado de novas arquiteturas, tecnologias e métodos de desenvolvimento de produtos digitais através da experimentação.

* Data: 08/06/2023
* Versão atual: 1.0 

## 1. Pré-Requisitos

* Windows 10.
  * [NetBeans 12.4](https://netbeans.apache.org/download/nb124/nb124.html)
  * [Java 7](https://www.oracle.com/br/java/technologies/javase/javase7-archive-downloads.html) - Distribuição: Oracle
  * [Xampp 8.2.4](https://www.apachefriends.org/pt_br/download.html)
  * [MySql 8.0.33](https://dev.mysql.com/downloads/mysql/)
  * Glassfish 4.1.1 - obtido através da IDE
  * [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html)

## 2. Instruções de construção, execução e uso da API

Antes de executar certifique-se de que o PhpMyAdmin está funcionando corretamente com o banco de dados montado, para criar o banco execute o script `clinica.sql` localizado na raíz deste repositório.

Antes de construir o JAR para distribuição certifique-se de que o PhpMyAdmin esteja **sem autenticação** e com o banco de dados `clinica` criado corretamente, pois os testes unitários são executados automaticamente quando compila-se o JAR para distribuição. Caso contrário, você deve desativar o processo de testes automáticos durante compilação do JAR.

### 2.1. Compilando o JAR para distribuição

Recomenda-se utilizar este modo de compilação para implantar o back-end em ambientes externos de produção, desenvolvidmento, controle de qualidade e etc. A compilação do pacote pode ser feita através do NetBeans no Windows ou em qualquer ambiente com o comando:

```
mvn install
```

O executável da API será gerado automaticamente, o maven irá agregar também a pasta de assets junto do `.jar`. Se você deseja contruir o projeto sem realizar os testes, então utilize:

```
mvn install -DskipTests
```


### 2.3. Construindo e Executando diretamente

**Atenção:** Como mencionado anteriormente, certifique-se de que o banco `clinica` existe no PhpMyAdmin no ambiente e que esteja **sem autenticação**. 

Apesar do projeto ser facilmente construído e executado através do Maven, este modo de compilação e execução somente é recomendado para desenvolvimento local. Dessa forma, o desenvolvedor pode usar o NetBeans para executar em modo de desenvolvimento com ou sem debugger direto na própria interface da IDE. Você também pode compilar e executar de uma vez através do terminal no **diretório do POM.xml**:

```
mvn clean compile exec:java
```

### 2.4. Contruindo e Executando os testes unitários

O código fonte dos testes unitários está em `./src/test/`. Você pode executar diretamente pelo NetBeans com ou sem o debugger (recomendado). Porém, se você deseja compilar e executar os testes unitários através do console, execute o seguinte comando:

```
mvn clean test-compile
```

Para executar uma classe inteira do teste de unitário utilize o seguinte comando, onde `[CLASSE]` é a respectiva classe implementada dos testes:

```
mvn -Dtest=[CLASSE] test
```

Para executar um método específico de alguma classe dos testes unitários, utilize o seguinte comando, onde `[CLASSE]` é a respectiva classe e `[METODO]` o respectivo método do Junit.

```
mvn -Dtest=[CLASSE]$[METODO] test
```

## 3. Testes Unitário do Projeto

O código deste projeto é testado através de uma componente de teste unitário que fazem a cobertura parcial das componentes presentes no sistema. O teste de unidade utiliza a biblioteca do JUnit e todo seu código se encontra em `src/test`. 

Antes de executar os testes, certifique-se que o PhpMyAdmin e serviços do MySQL estão ligado **sem autenticação** e que o banco de dados `clinica` existe.

Os testes unitários são executados automaticamente na construção de qualquer JAR para a distribuição do projeto pelo *script* agregado. Qualquer outro detalhe para execução dos testes unitários pode ser vista na respectiva seção de execução dos testes relacionada anteriormente neste documento. Toda a implementação dos testes devem estar documentadas, seguindo fielmente a estrutura dos demais testes. 

## 4. Inspeção de código

Este projeto utiliza da ferramenta [SonarLint](https://www.sonarsource.com/products/sonarlint/?gads_campaign=SL-Class01-Brand&gads_ad_group=SonarLint&gads_keyword=sonarlint&gclid=Cj0KCQjwy9-kBhCHARIsAHpBjHh9O1p1KY3_286b9Nb4sB5o1GA_7LEy-ACkEiPcFblG-RP4LmKrCe8aAu2QEALw_wcB) para inspeção de código. Esta ferramenta é utilizada para inspecionar o código que está hospedado na branch principal (master). 




