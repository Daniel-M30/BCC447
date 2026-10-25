# BCC447 — Programação Paralela em Java

**Projeto acadêmico para estudar processamento concorrente com Java, tarefas e pool de threads.**

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![Concorrência](https://img.shields.io/badge/Java-ExecutorService-415a77)
![Projeto acadêmico](https://img.shields.io/badge/Tipo-Projeto_acad%C3%AAmico-555555)

## Sobre o projeto

Este repositório reúne uma implementação experimental de **operações numéricas em paralelo**, incluindo redução por soma e cálculos relacionados à multiplicação de matrizes.

O código usa a API de concorrência do Java (`ExecutorService` e `Future`) para distribuir tarefas entre threads. O objetivo é exercitar divisão de trabalho, execução paralela e combinação de resultados — não fornecer uma biblioteca pronta para produção.

## Funcionalidades e conceitos estudados

- Criação de um pool fixo de threads com base nos processadores disponíveis.
- Submissão de tarefas e obtenção de resultados por `Future`.
- Particionamento de listas para redução por soma.
- Geração de matrizes com tarefas concorrentes.
- Processamento de operações de matrizes por tarefas paralelas.

No arquivo `ParallelSum/src/App.java`, a chamada de multiplicação está ativa; a rotina de soma aparece como uma alternativa comentada.

## Estrutura

- `ParallelSum/src/App.java` — Ponto de entrada e cenários experimentais.
- `ParallelSum/src/Pool/ThreadPool.java` — Gerenciamento do `ExecutorService`.
- `ParallelSum/src/Sum/` — Implementação de divisão e redução de somas.
- `ParallelSum/src/Mult/` — Componentes das operações com matrizes.
- `ParallelSum/bin/` — Arquivos `.class` já compilados e versionados no histórico do projeto.

## Executando localmente

### Pré-requisitos

- JDK instalado (com `java` e `javac` disponíveis).
- Terminal PowerShell ou ambiente Java configurado no VS Code.

No **PowerShell**, a partir da raiz do repositório:

```powershell
git clone https://github.com/Daniel-M30/BCC447.git
cd BCC447/ParallelSum

New-Item -ItemType Directory -Force -Path out | Out-Null
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
& javac -d out @sources
& java -cp out App
```

Alternativamente, abra a pasta `ParallelSum` no VS Code com as extensões Java e execute `App.java`.

**Atenção:** o cenário ativo realiza operações sobre matrizes de dimensão 200 × 200 e pode consumir tempo e recursos consideráveis. Não é um teste rápido de desempenho.

Os comandos acima descrevem um caminho sugerido de compilação, mas não foram executados nesta revisão.

## Estado atual e limitações

- O projeto não possui sistema de build como Maven ou Gradle.
- Arquivos binários compilados (`.class`) estão versionados.
- O README antigo em `ParallelSum/README.md` ainda contém instruções genéricas do VS Code.
- Não foi identificada uma suíte automatizada que valide a correção dos cálculos ou o desempenho.
- Este é um exercício acadêmico e não deve ser interpretado como biblioteca de computação paralela pronta para uso.

## Licenciamento

Não foi identificado um arquivo `LICENSE`. A publicação do código não implica autorização irrestrita de reutilização.

## Autor

Desenvolvido por [Daniel Monteiro](https://github.com/Daniel-M30).
