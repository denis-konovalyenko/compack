#!/usr/bin/env bash

CI_PATH="ci"
OPENSSL_CLI_OPTS="enc -aes-256-cbc -K ${OPENSSL_ENC_KEY} -iv ${OPENSSL_ENC_IV} -a"

openssl ${OPENSSL_CLI_OPTS} -d -in ${CI_PATH}/artifacts-signing-key.asc.enc -out ${CI_PATH}/artifacts-signing-key.asc
