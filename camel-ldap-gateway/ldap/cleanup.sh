#!/bin/bash

CONTAINER_NAME="openldap"

if podman inspect $CONTAINER_NAME &> /dev/null; then
    podman stop $CONTAINER_NAME &> /dev/null
    podman rm $CONTAINER_NAME &> /dev/null
fi

