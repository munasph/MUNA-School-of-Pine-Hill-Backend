#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

if [[ ! -f .env ]]; then
  echo "Missing .env — copy .env.example and add your Neon credentials." >&2
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

export JAVA_HOME="${JAVA_HOME:-/opt/homebrew/opt/openjdk}"
exec ./mvnw spring-boot:run -Dspring-boot.run.profiles=local "$@"
