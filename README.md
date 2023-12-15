# Documentação do Projeto de Microserviços

## Introdução
Este projeto consiste na implementação de um sistema de microserviços utilizando Spring Boot. O sistema é composto por dois serviços principais: ProdutoService e VendaService. 
Esses serviços interagem entre si e demonstram princípios de resiliência, rastreamento distribuído e comunicação eficiente em um ambiente de microserviços.

## Arquitetura
- Microserviços Utilizados:
  - ProdutoService: Gerencia informações de produtos.
  - VendaService: Lida com operações de venda de produtos.
- Discovery Service: Eureka Server para descoberta dinâmica de serviços.
- API Gateway: Utilizando Spring Cloud Gateway para roteamento e filtragem de tráfego.
- Client-side Load Balancer: Integração com Eureka para balanceamento de carga.
- Monitoramento e Rastreamento: Uso do Zipkin para rastreamento distribuído.

<img src=" capturas-de-telas /Eureka.png" width="100%" height="100%">
*Figura 1: Registro de serviço Eureka mostrando o status dos microserviços*

## Tecnologias e Ferramentas
- Spring Boot: Framework para facilitar a criação de microserviços.
- Eureka Server: Serviço de descoberta para gerenciamento de instâncias de microserviços.
- Resilience4j: Implementação de padrões de resiliência como Circuit Breaker e Retry.
- OpenFeign: Para comunicação declarativa entre microserviços.
- Swagger/OpenAPI: Para documentação dos endpoints dos serviços.
- Docker: Conteinerização dos serviços para fácil distribuição e implantação.
- PostgreSQL: Banco de dados para persistência de dados.
- Spring Cloud Gateway: Como gateway de API para roteamento e filtragem.
- Zipkin: Para rastreamento distribuído das chamadas entre serviços.
- Maven: Para gerenciamento de dependências e build do projeto.
- Prometheus e Grafana: Para monitoramento de métricas e visualização de dados.

<img src=" capturas-de-telas /DockerConfigs.png" width="100%" height="100%">
*Figura 2: Configurações Docker para a configuração de microserviços*

## Configuração e Instalação
### Pré-requisitos
- Java
- Docker
- Maven

### Passos para Configuração
1. Clone o repositório do projeto.
2. Navegue até cada microserviço e execute mvn clean install para construir os artefatos.
3. Use os comandos Docker fornecidos para iniciar os containers de cada serviço, incluindo Eureka Server e Zipkin.
4. Acesse os endpoints do Swagger para testar os serviços.

## Funcionalidades dos Serviços
### ProdutoService
- Endpoints: GET, POST, PUT, DELETE para gerenciamento de produtos.
- Resiliência: Circuit Breaker e Retry para lidar com falhas.

<img src=" capturas-de-telas /SwaggerProdutoService.png" width="100%" height="100%">
<img src=" capturas-de-telas /SwaggerProdutoServiceEndpoint.png" width="100%" height="100%">
<img src=" capturas-de-telas /SwaggerVendaService.png" width="100%" height="100%">

*Figura 3, 4 e 5: Swagger UI documentando os endpoints do ProdutoService*

### VendaService
- Endpoints: POST para registrar vendas.
- Comunicação com ProdutoService: Utiliza OpenFeign para chamadas de serviço.

## Resiliência e Segurança
- Implementação de Circuit Breaker e Retry com Resilience4j.

## Rastreamento e Monitoramento
- Rastreamento de chamadas de serviço com Zipkin.
- Configurações de logging para análise de desempenho e diagnóstico.
- Monitoramento de métricas com Prometheus e visualização no Grafana.

<img src=" capturas-de-telas /Grafana.png" width="100%" height="100%">

*Figura 6: Dashboard do Grafana visualizando métricas do serviço*

<img src=" capturas-de-telas /Zipkin.png" width="100%" height="100%">
<img src=" capturas-de-telas /Zipkin2.png" width="100%" height="100%">

*Figura 7 e 8: Rastreamento do Zipkin para sistemas distribuídos*

## Problemas Conhecidos e Limitações
- Verificação da configuração de rede para Docker e Eureka.

## Conclusão e Trabalhos Futuros
Este projeto demonstra a implementação eficaz de um sistema de microserviços com Spring Boot, integrando padrões de resiliência, rastreamento distribuído e descoberta de serviços. Futuras melhorias podem incluir a implementação de segurança robusta e otimizações de desempenho.

## Referências
- Documentação do Spring Boot
- Guia de Microserviços com Spring Cloud
- Documentação do Eureka Server
- Documentação do Zipkin
- Documentação do Prometheus
- Documentação do Grafana
