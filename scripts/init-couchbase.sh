#!/bin/bash

# Inspiration
# https://www.couchbase.com/forums/t/setting-up-a-cluster-and-a-bucket-programmatically-through-docker/32130/4

set -e

HOST="http://couchbase:8091"
QUERY_HOST="http://couchbase:8093"
USER="admin"
PASS="password"
BUCKET="users"

echo "Initializing Couchbase cluster..."
couchbase-cli cluster-init \
  --cluster "$HOST" \
  --cluster-username "$USER" \
  --cluster-password "$PASS" \
  --cluster-name dev-cluster \
  --services data,index,query \
  --cluster-ramsize 512 \
  --cluster-index-ramsize 256 || echo "Cluster already initialized"

sleep 3

echo "Creating bucket '$BUCKET'..."
couchbase-cli bucket-create \
  --cluster "$HOST" \
  --username "$USER" \
  --password "$PASS" \
  --bucket "$BUCKET" \
  --bucket-type couchbase \
  --bucket-ramsize 256 || echo "Bucket already exists"

echo "Waiting for bucket to be ready..."
sleep 5

echo "Creating primary index on '$BUCKET'..."
curl -sf -u "$USER:$PASS" "$QUERY_HOST/query/service" \
  -d "statement=CREATE PRIMARY INDEX IF NOT EXISTS ON \`$BUCKET\`" || \
curl -sf -u "$USER:$PASS" "$QUERY_HOST/query/service" \
  -d "statement=CREATE PRIMARY INDEX ON \`$BUCKET\`" || \
  echo "Primary index already exists"

echo "Couchbase initialization complete."
