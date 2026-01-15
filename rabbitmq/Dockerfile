# Use the official RabbitMQ image as the base image
FROM rabbitmq:management

COPY rabbitmq.conf /etc/rabbitmq
COPY definitions.json /etc/rabbitmq

RUN cat /etc/rabbitmq/rabbitmq.conf