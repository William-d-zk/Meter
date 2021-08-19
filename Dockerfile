FROM bellsoft/liberica-openjdk-alpine-musl
ENV LANG=zh_CN.UTF-8
ENV LANGUAGE=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
RUN set -eux &&\
    sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories &&\
    apk add alpine-conf &&\
    setup-timezone -z ${TZ} &&\
    apk add --update --no-cache sqlite

ARG JAR_FILE=target/*.jar
ARG SQLITE_NATIVE_FILE=libsqlitejdbc.so
COPY ${JAR_FILE} app.jar
COPY ${SQLITE_NATIVE_FILE} /usr/lib/libsqlitejdbc.so

EXPOSE 8080
EXPOSE 8000
EXPOSE 1883
