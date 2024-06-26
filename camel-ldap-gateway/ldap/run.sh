#!/bin/bash

SCRIPT_DIR=$(dirname "$(readlink -f "$0")")

CONTAINER_NAME="openldap"
LDAP_PORT="1389"
LDAP_ADMIN_PASSWORD="adminpassword"
LDAP_IMAGE="docker.io/osixia/openldap:latest"
LDIF_FILE="data.ldif"

if podman inspect $CONTAINER_NAME &> /dev/null; then
    podman stop $CONTAINER_NAME &> /dev/null
    podman rm $CONTAINER_NAME &> /dev/null
fi

podman run -d --name $CONTAINER_NAME \
    -p $LDAP_PORT:389 \
    -e LDAP_ORGANISATION="example" \
    -e LDAP_DOMAIN="example.com" \
    -e LDAP_ADMIN_PASSWORD=$LDAP_ADMIN_PASSWORD \
    $LDAP_IMAGE

sleep 5

podman cp "$SCRIPT_DIR/$LDIF_FILE" $CONTAINER_NAME:/

podman exec -i $CONTAINER_NAME ldapadd -x -D "cn=admin,dc=example,dc=com" -w $LDAP_ADMIN_PASSWORD -f $LDIF_FILE
