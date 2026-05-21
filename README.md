# 🤖 Robocode: Projeto Robozinho

Este repositório contém o código-fonte do **Robozinho**, um tanque de batalha autônomo desenvolvido para o simulador Robocode em Java.

Este projeto tem fins acadêmicos e visa o estudo prático de Programação Orientada a Objetos (POO), Estruturas de Dados e, principalmente, o fluxo de versionamento colaborativo utilizando **Git e GitHub**.

---

## 🎯 O que está sendo desenvolvido
Estamos desenvolvendo um robô inteligente que não apenas atira cegamente, mas que mapeia a arena e adapta seu comportamento. O robô herda as propriedades da classe `AdvancedRobot`, o que permite a execução de ações assíncronas (como girar o radar independentemente do canhão) e tomadas de decisão complexas.

### 🧠 Conceitos e Lógicas Aplicadas
* **Máquina de Estados (State Machine):** O robô utiliza uma estrutura de `switch-case` baseada na quantidade de inimigos vivos. Isso permite que ele alterne entre diferentes estratégias (ex: modo de sobrevivência contra muitos inimigos, e agressividade no modo duelo "1 contra 1").
* **Programação Orientada a Eventos:** O comportamento do robô reage ativamente ao ambiente através de métodos ouvintes, como:
    * `onScannedRobot`: Para rastrear inimigos.
    * `onRobotDeath`: Para atualizar o mapa de batalha quando um oponente é eliminado.
    * `onHitWall` / `onHitByBullet`: Para rotinas de evasão e defesa.

### 📦 Estrutura de Dados Utilizada
Para gerenciar o número de inimigos na arena, implementamos a interface **`Set`** utilizando a classe **`HashSet<String>`**.
* **Por que o HashSet?** O radar do Robocode varre o mesmo robô dezenas de vezes por segundo. O `HashSet` é uma coleção que **não permite elementos duplicados**. Assim, quando adicionamos o nome de um inimigo rastreado (`inimigosVistos.add(nome)`), garantimos que cada robô seja contabilizado apenas uma vez, mantendo uma contagem precisa de quantos oponentes únicos estão na arena.

---

## 🛠️ Fluxo de Trabalho com Git (Para o Professor e Equipe)
Abaixo está o guia de comandos Git essenciais utilizados no ciclo de desenvolvimento e atualização deste projeto.

### 1. Obtendo o Projeto (Primeiro Passo)
Para que os membros da equipe possam baixar o projeto em suas máquinas locais:
```bash
# Clona o repositório para o computador local
git clone https://github.com/thesimas/RobocodeRobots.git

# Baixa e mescla as alterações mais recentes do repositório remoto
git pull origin main

# Cria uma nova branch chamada 'colaborador' e já muda para ela
git checkout -b colaborador

# Verifica quais arquivos foram modificados (Sempre bom rodar para conferir)
git status

# Prepara todos os arquivos modificados para o próximo commit
git add .

# Salva uma "fotografia" do código com uma mensagem clara sobre o que foi feito
git commit -m "Identifique bem esse commit em"