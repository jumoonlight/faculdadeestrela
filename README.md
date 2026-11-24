# Sistema Matrícula da Faculdade Estrela
Olá! Seja muito bem-vindo(a) ao nosso sistema da Faculdade Estrela.
O nosso sistema integra amplas funcionalidades para cada um dos usuários a seguir:
## Administrador
- Pode cadastrar, atualizar e excluir alunos, bibliotecários e professores.
- Consegue realizar o CRUD (Create, Read, Update e Delete) nas entidades: Disciplina, Empréstimo, Matrícula, Curso, Livro, Referência
e Oferta de Disciplina.
- Restrição: Não é permitido deletar ou atualizar a conta do administrador, apenas se desconectar.
## Aluno
- Consegue atualizar, sair ou deletar sua conta a partir do momento que se autenticou no sistema.
- Consegue realizar o CRUD (Create, Read, Update e Delete) nas entidades: Empréstimo e Matricula.
- Restrição: Pode realizar o CRUD somente em cadastros feitos por ele próprio.
## Professor
- Consegue atualizar, sair ou deletar sua conta a partir do momento que se autenticou no sistema.
- Consegue realizar o CRUD (Create, Read, Update e Delete) nas entidade: Empréstimo.
- Restrição: Pode realizar o CRUD somente em cadastros feitos por ele próprio.
## Bibliotecário
- Consegue atualizar, sair ou deletar sua conta a partir do momento que se autenticou no sistema.
- Consegue realizar o CRUD (Create, Read, Update e Delete) nas entidade: Empréstimo.
- Restrição: Pode realizar o CRUD somente em cadastros feitos por ele próprio.
## Cookies
São inseridos a partir do instante que o usuário realiza login no sistema. Após 30 minutos de inatividade, a
conta é desconectada automaticamente.
## Requisitos para rodar o Sistema:
  - MySQL 8.0
  - Java versão 21 (pelo menos)
  - Um editor de código-fonte (VSCode recomendado)
  - Navegador atualizado para a execução correta do programa
## Como rodar o sistema:
- Baixe o arquivo em seu computador ou  copie o link desse repositório e execute no terminal do Java:
  "git clone <link-do-repositório>
- Após baixado, abra o programa MySql e execute o código:
  "create table sistemamatricula"
   OBS: Verifique o arquivo application.properties para conferir se seu usuário e senha são os mesmos do
  arquivo. Caso não seja, mude-os para os do seu computador.
- Acesse a pasta onde se encontra a faculdadeestrela  e abra com o editor de código fonte. Dentro do editor, inicie o terminal e digite:
   "mvn spring-boot:run"
- Abra seu navegador padrão. Na barra de endereços, digite:
  "localhost:8080/login"
- Você seria redirecionado a tela de login da faculdade estrela.

  
# Obrigadas por verem nosso sistema!!
