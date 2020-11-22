
FROM java:8-alpine

RUN mkdir ./app

COPY ./build/libs/auth.jar ./app/auth.jar
COPY ./web-client/build/distributions/web-client.js ./app/static/web-client.js

WORKDIR ./app

#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=7400,server=y,suspend=n

CMD  ["java","-jar","auth.jar"]