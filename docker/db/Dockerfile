FROM mysql:8.0.31

ENV MYSQL_ROOT_PASSWORD=prueba123

COPY ./schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306