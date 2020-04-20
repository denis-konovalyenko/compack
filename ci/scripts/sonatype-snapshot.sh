#!/usr/bin/env bash
# This script has to be run from the root project directory

CI_PATH="ci"
CI_SCRIPTS_PATH="${CI_PATH}/scripts"
MAVEN_CLI_OPTS="--settings ${CI_PATH}/.m2/settings.xml --batch-mode --errors --show-version --fail-at-end -DinstallAtEnd=true -DdeployAtEnd=true"

${CI_SCRIPTS_PATH}/artifacts-signing-key-decryption.sh
mvn deploy ${MAVEN_CLI_OPTS} -DskipTests --activate-profiles code-source,code-documentation,artifacts-signing,artifacts-deployment
