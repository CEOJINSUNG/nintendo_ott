# nintendo_ott

> 멀티 모듈을 활용한 Spring Nintendo Ott 서버입니다. 

## Tech Stack

0. Java, Spring Devtools, Lombok
1. Spring Batch + Spring Redis
2. Spring Webflux + Spring R2DBC + MySQL

- 이번 프로젝트의 성장 목표는 멀티 모듈을 활용한 Spring Project 입니다.

## Multi-Module Process

1. 먼저, start.spring.io에서 공통 라이브러리를 포함한 프로젝트를 생성합니다.
2. 사용하고자 하는 모듈을 정의한다음 각 모듈의 목표를 설정합니다. 
   
   core - Entity, Repository, Utility 등/batch - 배치 작업 프로세스/api - API 단계 설정

3. 기존 src 폴더를 삭제한 후 root 폴더에 있는 settings.gradle를 확인하고 build.gradle에서 subprojects로 감싸서 생성해줍니다. 
