#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        withEnv(['JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64', 'PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin']) {
            sh "chmod +x mvnw"
            sh "./mvnw -ntp clean -P-webapp"
        }
    }
    stage('nohttp') {
        withEnv(['JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64', 'PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin']) {
            sh "./mvnw -ntp checkstyle:check"
        }
    }

    stage('install tools') {
        withEnv(['JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64', 'PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin']) {
            sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:install-node-and-npm@install-node-and-npm -X"
        }
    }

    stage('npm install') {
        withEnv(['JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64', 'PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin', 'PUPPETEER_SKIP_CHROMIUM_DOWNLOAD=true']) {
            sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:npm"
        }
    }
    stage('backend tests') {
        // try {
        //     sh "./mvnw -ntp verify -P-webapp"
        // } catch(err) {
        //     throw err
        // } finally {
        //     junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
        // }
    }

    stage('frontend tests') {
        // try {
        //     sh "./mvnw -ntp com.github.eirslett:frontend-maven-plugin:npm -Dfrontend.npm.arguments='run test'"
        // } catch(err) {
        //     throw err
        // } finally {
        //     junit '**/target/test-results/TESTS-results-jest.xml'
        // }
    }

    stage('packaging') {
        sh "./mvnw -ntp verify -P-webapp -Pprod -DskipTests"
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }

    def dockerImage
    stage('publish docker') {
    withCredentials([usernamePassword(credentialsId: 'dockerhub-login', passwordVariable:
    'DOCKER_REGISTRY_PWD', usernameVariable: 'DOCKER_REGISTRY_USER')]) {
     sh "./mvnw -ntp jib:build"
        }  
    }
}
