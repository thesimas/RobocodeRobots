🤖 Robocode: Robozinho

Este repositório contém o código-fonte de um robô desenvolvido para o simulador Robocode. O projeto foi criado com o propósito duplo de estudar a API do Robocode em Java e praticar fluxos de trabalho com o Git.

🚀 Sobre o Projeto

O Robocode é um jogo de programação onde o objetivo é desenvolver um tanque de batalha robô para lutar contra outros tanques em Java ou .NET. As batalhas ocorrem em tempo real e na tela.
Objetivos de Aprendizado:

    Java: Implementação de herança, sobrescrita de métodos e lógica de eventos.

    Git: Versionamento de código, uso de commits semânticos e gestão de branches.

    Estratégia: Desenvolvimento de algoritmos de movimentação e mira.

🛠️ Tecnologias Utilizadas

    Linguagem: Java

    Ambiente: [Ex: IntelliJ IDEA / Eclipse / VS Code]

    Versionamento: Git & GitHub

    Motor de Batalha: Robocode API

📈 Evolução e Versionamento

Neste projeto, utilizei o Git para registrar cada melhoria na lógica do robô. Você pode conferir o histórico de commits para visualizar a evolução de:

    Setup Inicial: Configuração básica da classe estendendo Robot.

    Movimentação: Implementação de padrões de movimento (ex: Square, Wall-Smoothing).

    Sistema de Mira: Lógica para detecção e disparo contra inimigos.

    Gestão de Energia: Refinamento para evitar gastos desnecessários de munição.

🎮 Como Executar

    Instale o Robocode: Baixe em robocode.sourceforge.io.

    Clone este repositório:
    Bash

    git clone https://github.com/thesimas/RobocodeRobots.git

    Compile o código: Certifique-se de incluir o robocode.jar no seu classpath.

    Importe no Jogo:

        Abra o Robocode.

        Vá em Options -> Preferences -> Development Options.

        Adicione o caminho da pasta onde o código foi compilado.

    Batalhe: Adicione o seu robô em uma nova batalha (Battle -> New).

📝 Comandos Git Praticados

Durante o desenvolvimento, foram exercitados os seguintes comandos:

    git status : Para verificar alterações.

    git add . : Para preparar os arquivos.

    git commit -m "feat: descrição": Para registrar o progresso.

    git log --oneline: Para visualizar o histórico de forma concisa.