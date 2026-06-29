#!/bin/bash
cd /home/admin/oop/grocerystore
mvn clean compile > maven_output.txt 2>&1
mvn test-compile >> maven_output.txt 2>&1
