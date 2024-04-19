#!/bin/bash

curl -s -X POST -H "Content-Type: text/xml;charset=UTF-8" -d @demo.xml http://localhost:8080/calc
