#!/usr/bin/env bash

CI_PATH="ci"

GPG_CONF=".gnupg/gpg.conf"
GPG_AGENT_CONF=".gnupg/gpg-agent.conf"
OPENSSL_CLI_OPTS="enc -aes-256-cbc -K ${OPENSSL_ENC_KEY} -iv ${OPENSSL_ENC_IV}"

openssl ${OPENSSL_CLI_OPTS} -d -in ${CI_PATH}/artifacts-signing-key.asc.enc -out ${CI_PATH}/artifacts-signing-key.asc
gpg --quiet --batch --import ${CI_PATH}/artifacts-signing-key.asc

echo "use-agent" > ${HOME}/${GPG_CONF}
echo "pinentry-mode loopback" >> ${HOME}/${GPG_CONF}
echo "allow-loopback-pinentry" > ${HOME}/${GPG_AGENT_CONF}
