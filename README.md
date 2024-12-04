# Microsserviço de Gestão de Pedidos

Micro serviço com a responsabilidade de orquestrar os microserviços necessários para se construir e efetivar pedidos

- processamento de pedidos
- acionamento de microsserviço de logística de entrega para garantir a entrega dos produtos

## Stack

- Java 17
- Spring Boot
- Docker

## Iniciar ambiente dev/local

Inicia containers e configura aplicação. Url de acesso: http://localhost:8083/api/v1

``` shell
docker-compose up -d 
```

## Swagger
http://localhost:8083/swagger-ui/index.html