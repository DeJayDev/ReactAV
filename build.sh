#!/usr/bin/env bash
mvn clean package -Dmaven.test.skip -DskipTests && cp target/reactav-1.0.0.jar ../test-server/plugins/
