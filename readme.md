# 프로젝트 개요
해당 프로젝트는 30인 이하 소형 항공기를 단체 대여할 수 있는 시스템이다. 
시스템명은 `GetTA`이며, 해당 문서는 기획과 데모, 설계, 프로젝트 관리한 내용을 담고 있다.

## 1. 기획

![system-define.png](docs/plan/system-define.png)
![project-management.png](docs/plan/project-management.png)
![srs.png](docs/plan/srs.png)
![usecase-diagram.png](docs/plan/usecase-diagram.png)
![usecase-specification.png](docs/plan/usecase-specification.png)

## 2. 데모

![restAPI.png](docs/demo/restAPI.png)
![test.png](docs/demo/test.png)

## 3. 설계
### 1) 클래스 및 객체 설계
![class-structure.png](docs/architecture/class-structure.png)
![sequence-flow1.png](docs/architecture/sequence-flow1.png)
![sequence-flow2.png](docs/architecture/sequence-flow2.png)
### 2) 데이터베이스 설계
![database.png](docs/architecture/database.png)
### 3) 테스트 설계
![test-unit&integration.png](docs/architecture/test-unit&integration.png)
![test-unit1.png](docs/architecture/test-unit1.png)
![test-unit2.png](docs/architecture/test-unit2.png)
![test-system.png](docs/architecture/test-system.png)

## 3. 프로젝트 관리

![flow.png](docs/management/flow.png)
![outputs1.png](docs/management/outputs1.png)
![outputs2.png](docs/management/outputs2.png)

---
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