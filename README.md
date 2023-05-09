<div align="center" >
  <h1>🖥️ Sistema de Gerenciamento de Ordens de Serviço</h1>

![Most used language](https://img.shields.io/github/languages/top/jnaraujo/sistema-de-gerenciamento?style=flat-square)
![GitHub last commit](https://img.shields.io/github/last-commit/jnaraujo/sistema-de-gerenciamento?style=flat-square)
![License](https://img.shields.io/github/license/jnaraujo/sistema-de-gerenciamento)
![Java Build and Test Workflow](https://github.com/jnaraujo/sistema-de-gerenciamento/actions/workflows/build.yaml/badge.svg)
![Release](https://img.shields.io/github/v/release/jnaraujo/sistema-de-gerenciamento?style=flat-square)

</div>

<p>
Projeto da disciplina EXA 863 - MI de Algoritmos e Programação II da Universidade Estadual de Feira de Santana.
</p>

> Para acessar o Javadoc, [clique aqui](https://sistema-de-gerenciamento.jnaraujo.com/javadocs/).

## 💼 Objetivo

O objetivo do projeto é desenvolver um sistema de gerenciamento de ordens de serviço para uma empresa de manutenção de computadores.

## 📚 Como o projeto está estruturado

Para desenvolver o projeto, foi utilizado o padrão de arquitetura MVC (Model-View-Controller).

Nesse padrão, a aplicação é dividida em 3 camadas:

- Model: responsável por representar os dados da aplicação.
- View: responsável por representar a interface gráfica da aplicação.
- Controller: responsável por intermediar a comunicação entre a View e o Model.

## 📋 Funcionalidades

### Cadastro de clientes

É possível que o usuário cadastre novos clientes da empresa de manutenção de computadores, incluindo informações como nome, endereço, telefone, e-mail, entre outros.

### Cadastro de usuários (técnicos, administradores e recepcionistas)

Permite que o usuário cadastre novos usuários do sistema, incluindo informações como nome, e-mail, senha e cargo (técnico, administrador ou recepcionista).

### Cadastro de ordens de serviço

O usuário pode criar novas ordens de serviço, atribuindo clientes, técnicos responsáveis, peças utilizadas e serviços realizados, além de informações como data de criação, valor total etc.

### Cadastro de peças

Permite que o usuário cadastre novas peças utilizadas nas ordens de serviço, incluindo informações como nome, descrição, fabricante e quantidade em estoque.
Essas peças serão usadas nas ordens de serviço.

### Cadastro de serviços

Permite que o usuário cadastre novos serviços oferecidos pela empresa de manutenção de computadores, incluindo informações como nome, descrição e valor.
Exemplos de serviço são: limpeza, instalação de sistemas operacionais etc.

## Gerar relatórios

O sistema é capaz de gerar relatórios sobre tempo médio de espera, preço e custo médio das peças, preço médio das ordens, satisfação do cliente, etc;

## 🎭 Diagrama de Classes

![Diagrama de Classes](./diagrama%20de%20classe.png)

## 🔗 Diagrama de Casos de Uso

![Diagrama de Casos de Uso](./casos%20de%20uso.png)

## 🚀 Como rodar o projeto

### 🔍 Pré-requisitos

- [OpenJDK 20](https://jdk.java.net/20/)
- [JUnit 5](https://junit.org/junit5/)
- [JavaFX](https://openjfx.io/)
- [Maven](https://maven.apache.org/)
- [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/)
- [Git](https://git-scm.com/)

### 💻 Rodando o projeto

- Clone este repositório

```bash
$ git clone https://github.com/jnaraujo/sistema-de-gerenciamento
```

- Abra o projeto no IntelliJ IDEA

```bash
$ cd sistema-de-gerenciamento
```

## 📝 Licença

> Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](./LICENSE) para mais detalhes.
