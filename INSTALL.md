## Запуск/Проверка/Настройка

## Docker:
- Запуск проекта

Вариант - PROD
```shell
docker-compose --project-name credit-conveyor --profile prod --file docker-compose.yaml up
```
Вариант - DEV
```shell
docker-compose --project-name credit-conveyor --file docker-compose.yaml up
```

## Микросервис Сделка (Deal)
- Просмотр метрик: http://localhost:8004/actuator/prometheus

## Prometheus
- Проверка, что Prometheus правильно прослушивает приложение: http://localhost:9090/targets

## Grafana:
- Просмотр Grafana: http://localhost:3000

Настройка
- Добавить Datasource: host -> http://prometheus:9090