# Projedata-Iniflex - Gestão de Funcionários

Este é um guia rápido sobre como baixar, compilar e executar a aplicação.

## Pré-requisitos

Antes de começar, garanta que você tenha os seguintes softwares instalados em sua máquina:
* **Java Development Kit (JDK)**: Versão 21 ou superior.
* **Apache Maven**: Versão 3.8 ou superior (para compilação e gerenciamento de dependências).
* **Git**: Para clonar o repositório (caso não baixe oarquivo zip).

## Como Executar o Projeto

Siga os passos abaixo para colocar a aplicação em funcionamento.

### 1. Obtenha o Código-Fonte

Você pode baixar o projeto de duas maneiras:

* **Opção A: Clonar o repositório (Recomendado)**

    Abra seu terminal ou prompt de comando e execute o seguinte comando:
    ```bash
    git clone https://github.com/GustavoNiDD/Projedata-Iniflex.git
    ```

* **Opção B: Baixar o ZIP**

    1.  Acesse o link: [https://github.com/GustavoNiDD/Projedata-Iniflex](https://github.com/GustavoNiDD/Projedata-Iniflex)
    2.  Clique no botão verde "**<> Code**".
    3.  Selecione "**Download ZIP**".
    4.  Extraia o arquivo ZIP em um local de sua preferência.

### 2. Compile o Projeto com Maven

Navegue até o diretório raiz do projeto (a pasta `Projedata-Iniflex` que contém o arquivo `pom.xml`) pelo seu terminal.
```bash
cd Projedata-Iniflex
```

Execute o comando do Maven para compilar o projeto e baixar todas as dependências necessárias.
```bash
mvn clean install
```
Este comando irá gerar um arquivo `.jar` executável dentro da pasta `target/`.

### 3. Execute a Aplicação

Você pode executar a aplicação da segunite forma:

* **Usando o Maven**

    Ainda no diretório raiz, execute o comando:
    ```bash
    mvn spring-boot:run
    ```

### 4. Utilizando a Aplicação

Após a execução de um dos comandos acima, uma janela gráfica (Swing) será exibida.

* **Painel de Ações (Esquerda)**: Contém botões para executar todas as operações solicitadas (imprimir, remover, aplicar aumento, agrupar, etc.).
* **Tabela de Funcionários (Centro)**: Exibe a lista de funcionários. A tabela é atualizada conforme as ações são executadas.
* **Área de Mensagens (Inferior)**: Mostra informações e resultados das operações, como o valor total dos salários ou a lista de funcionários agrupada por função.

Para usar, simplesmente clique nos botões do painel esquerdo e observe os resultados na tabela e na área de mensagens.
