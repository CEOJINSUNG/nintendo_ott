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

### root build.gradle

   buildscript {
      ext {
         springBootVersion = '2.6.2'
         dependencyManagementVersion = '1.0.11.RELEASE'
      }
      repositories {
         mavenCentral()
      }
      dependencies {
         dependencies {
            classpath "org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}"
            classpath "io.spring.gradle:dependency-management-plugin:${dependencyManagementVersion}"
         }
      }
   }

   subprojects {
      apply plugin: 'java'
      apply plugin: 'org.springframework.boot'
      apply plugin: 'io.spring.dependency-management'

      group = 'com.multi.module'
      version = '0.0.1-SNAPSHOT'
      sourceCompatibility = 11

      repositories {
         mavenCentral()
      }

      configurations {
         compileOnly {
            extendsFrom annotationProcessor
         }
      }

      // 공통 세팅 입력
      dependencies {
         implementation 'org.springframework.boot:spring-boot-starter'
         implementation 'org.springframework.boot:spring-boot-starter-webflux'
         implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
         implementation 'org.springframework.boot:spring-boot-starter-batch'
         implementation "io.r2dbc:r2dbc-pool"
         runtimeOnly "dev.miku:r2dbc-mysql"
         runtimeOnly "mysql:mysql-connector-java"
         compileOnly 'org.projectlombok:lombok'
         developmentOnly 'org.springframework.boot:spring-boot-devtools'
         annotationProcessor 'org.projectlombok:lombok'
         testImplementation 'org.springframework.boot:spring-boot-starter-test'
         testImplementation 'org.springframework.batch:spring-batch-test'
      }

      test {
         useJUnitPlatform()
      }
   }

   project(':core') {
      dependencies {

      }
   }

   project(':api') {
      dependencies {
         implementation project(':core')
      }
   }

   project(':batch') {
      dependencies {
         implementation project(':core')
      }
   }

### core build.gradle

  bootJar { enabled = false }
  jar { enabled = true }
