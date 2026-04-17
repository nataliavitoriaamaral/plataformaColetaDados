# plataformaColetaDados

# Monitoramento Ambiental da Amazônia (Padrão Observer)

Este projeto é uma atividade prática focada na implementação do Padrão de Projeto **Observer** (Observador e Sujeito) em Java. O cenário simula um sistema de monitoramento ambiental na região Amazônica, onde diferentes universidades brasileiras recebem atualizações em tempo real sobre mudanças climáticas e ambientais.

## Objetivo do Projeto

Criar um sistema de notificação eficiente onde bases de monitoramento na Amazônia coletam dados e notificam automaticamente as instituições interessadas, sem criar um forte acoplamento entre os sensores e as universidades.

### O Cenário
Na região Amazônica, existem várias **Estações de Monitoramento** espalhadas por diferentes localidades (ex: Manaus, Fronteira Leste). Cada estação coleta três tipos de dados:
* **Temperatura**
* **pH da água/solo**
* **Umidade do Ar**

Diversas **Universidades** (São José dos Campos, Porto Alegre, São Paulo, Rio de Janeiro e Brasília) têm interesse nesses dados para suas pesquisas. Cada universidade pode se inscrever para receber atualizações de sensores específicos de determinadas estações.

## Arquitetura e Padrão Observer

O projeto foi construído utilizando os conceitos de Orientação a Objetos (Herança, Polimorfismo e Composição) e aplica o padrão comportamental **Observer**.

* **Subject (Sujeito):** Classe base que gerencia a lista de observadores e notifica a todos quando há uma mudança de estado. Possui um atributo `local` para identificar a origem do dado.
* **Concrete Subjects (Sensores):** As classes `Temperatura`, `Ph` e `UmidadeAr` herdam de `Subject`. Quando seus valores são alterados via método `set`, disparam a notificação.
* **Observer (Observador):** Interface com o método `update(Subject s)` que deve ser implementada por quem deseja receber os dados.
* **Concrete Observer (Universidade):** Implementa a interface `Observer`. Quando notificada, verifica de qual tipo de sensor a informação veio (usando `instanceof`) e imprime a alteração na tela.
* **EstacaoMonitoramento (Composição):** Representa um local físico na Amazônia. Ao invés de uma estação ser um único sujeito gigante, ela *possui* os três sensores de forma encapsulada. Isso permite que uma universidade assine apenas o dado de seu interesse (ex: apenas pH).
