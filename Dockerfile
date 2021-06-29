FROM openjdk:11-jdk-slim

RUN groupadd -g 1000 nonroot && \
    useradd -m -d /home/nonroot -s /bin/bash -g 1000 -u 1000 nonroot

USER nonroot

RUN mkdir /home/nonroot/.m2

WORKDIR /app