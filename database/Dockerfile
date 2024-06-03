FROM liquibase/liquibase:4.16.1
ARG POSTGRES_USER
ARG POSTGRES_PASSWORD
ARG POSTGRES_ADDRESS
WORKDIR /changelog
COPY src/main/resources/migrations .
CMD liquibase --changelog-file index.xml --url $POSTGRES_ADDRESS --username $POSTGRES_USER --password $POSTGRES_PASSWORD update