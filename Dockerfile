FROM ubuntu:latest

ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone 

RUN	apt update && apt upgrade -y

RUN apt update && apt install -y build-essential vim git curl wget zip unzip openjdk-11-jdk

RUN useradd -ms /bin/bash td
RUN adduser td sudo

USER td
WORKDIR /home/td/

RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "$HOME/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install kotlin"
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install gradle 7.2"

# https://hub.docker.com/repository/docker/tdadela/android#
