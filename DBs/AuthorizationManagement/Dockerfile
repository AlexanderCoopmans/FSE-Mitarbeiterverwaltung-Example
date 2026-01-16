# Use the official MySQL image as the base image
FROM mysql:9
COPY create_table_authorizations.sql /docker-entrypoint-initdb.d/

# Delete the volume beforehand (docker volume rm <volume>),
# Scripts are only executed if the volume (e.g. /var/lib/mysql) does not exist