#!/usr/bin/env bash
# This script has to be run from the root project directory

if [ -z "${TAG}" ]; then
    echo "TAG must be set"
    exit 1
fi

CI_PATH="ci"
CI_SCRIPTS_PATH="${CI_PATH}/scripts"
MAVEN_CLI_OPTS="--settings ${CI_PATH}/.m2/settings.xml --batch-mode --errors --show-version --fail-at-end -DinstallAtEnd=true -DdeployAtEnd=true"

git checkout $(git rev-parse HEAD)
mvn versions:set "-DnewVersion=${TAG}"
${CI_SCRIPTS_PATH}/gpg-import.sh
mvn deploy ${MAVEN_CLI_OPTS} -DskipTests --activate-profiles code-source,code-documentation,artifacts-signing,artifacts-deployment
git commit -am "Release ${TAG}"
git tag -f ${TAG}
