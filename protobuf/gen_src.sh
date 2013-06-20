#!/bin/sh

echo "Generating Protobuf sources from descriptors"
echo "Outputting results to src, import path is ."

protoc --java_out=src/ --proto_path=. core.proto

echo "Done."
