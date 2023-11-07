
# 개발 환경 설정
다음은 로컬 개발환경을 만들기 위한 설정들의 일련의 과정을 설명한다.
### 1) java 설치
1. sdkman 설치
   ```bash
   $ curl -s "https://get.sdkman.io" | bash
   $ source "$HOME/.sdkman/bin/sdkman-init.sh"
   $ sdk version
   ```
2. sdkman 에서 java corretto 17 설치
    ```bash
    $ sdk list java
    $ sdk install java ${java-version}
    ```
<br>

### 2) mysql 설치
1. [docker 설치](https://docs.docker.com/engine/install/)

2. docker-compose 설치
      ```bash
      $ sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
      $ sudo chmod +x /usr/local/bin/docker-compose
      $ docker-compose --version
      ```
3. mysql 띄우기
    ```bash
    $ cd docker; docker-compose up -d
    ```