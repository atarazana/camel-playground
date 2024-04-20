#!/bin/bash

send_request() {
    local endpoint="http://localhost:8080/calc"
    local username_header="username: $1"
    local payload="@demo.xml"

    curl \
    -s \
    -X POST \
    -H "Content-Type: text/xml;charset=UTF-8" \
    -H "$username_header" \
    -d "$payload" \
    "$endpoint"

    echo
}

show_menu() {
    echo "Select an option:"
    echo "1. Send authorized request"
    echo "2. Send unauthorized request"
    echo "3. Exit"
    read -rp "Option: " choice

    case $choice in
        1)
            send_request "angel" "valid"
            ;;
        2)
            send_request "miguel" "invalid"
            ;;
        3)
            echo "Exiting menu"
            exit 0
            ;;
        *)
            echo "Invalid option. Please select 1, 2, or 3."
            ;;
    esac
}

while true; do
    show_menu
done
