@Library('Deployment-Jenskins-Library') _

pipeline {
  agent any
  tools {
     maven 'maven'
  }
  environment {
    app = ''
    pom = ''
    version = ''
    docker_registry = "${env.DOCKER_REGISTRY_SNAPSHOT}"
    docker_registry_url = "${env.DOCKER_REGISTRY_URL}"
    docker_registry_credentials_id = "${env.DOCKER_REGISTRY_CREDENTIALS_ID_SNAPSHOT}"
    m2_settings = "${env.M2_SETTINGS_PATH}"
    docker_image = "template-spring-service"
    sonar = 'sonar'
    mem = "${env.SERVICE_MEM}"
    spring_profile = "${env.SPRING_PROFILE}"
    spring_args = "${env.SPRING_ARGS}"
  }

  stages {
    stage('Initialize') {
      steps {
        checkout scm
        sh "java -version"

        script {
          pom = readMavenPom file: 'pom.xml'
          version = pom.version
        }
      }
    }

    stage('Build & Package Java') {
      steps {
        withSonarQubeEnv(sonar) {
          sh "mvn -s " + m2_settings + " clean org.jacoco:jacoco-maven-plugin:prepare-agent package -Dmaven.test.failure.ignore=false sonar:sonar"
        }
      }
    }

    stage('Publish Java'){
      when {
        branch 'master'
      }
      steps{
        sh "mvn -s "+ m2_settings + " deploy"
      }
    }

    stage('Build Docker') {
      steps {
        script {
           app = docker.build(docker_registry + "/" + docker_image , " --pull=true --build-arg DOCKER_REGISTRY_MIRROR=${env.DOCKER_REGISTRY_MIRROR} --build-arg build_version=" + version +" ${env.WORKSPACE}")
        }
      }
    }

    stage('Publish Docker') {
      when {
        branch 'master'
      }
      steps {
        script {
          docker.withRegistry(docker_registry_url, docker_registry_credentials_id) {
            app.push(version)
            app.push("latest")
          }
        }
      }
    }

    stage('Deployment') {
      when {
        branch 'master'
      }
      steps {
        script{
          echo 'Preparing configuration:'
          config = [
            id: "/" + docker_image,
            imageName : docker_registry + "/" + docker_image + ":latest",
            cpus: "0.5",
            mem: mem,
            disk: "1024",
            instances: "1",
            env: '{SPRING_PROFILE: "' + spring_profile +'", SPRING_ARGS: "' + spring_args+'"}'
          ]
          echo config.toString()

          echo 'Performing pre deploy actions'
          deployLibrary.preDeploy(config)
          echo 'Performing pre deploy'
          deployLibrary.deploy(config)
          echo 'Performing post deploy actions'
          deployLibrary.postDeploy(config)
        }
      }
    }
  }

  post {
    always{
      sendStatusEmail audience: "${JENKINS_NOTIFICATIONS_EMAIL}", changeLogSets: currentBuild.changeSets, buildStatus: currentBuild.currentResult
      junit testResults: '**/target/surefire-reports/TEST-*.xml', allowEmptyResults: true
      jacoco exclusionPattern: '**/*Test*.class', execPattern: '**/jacoco.exec', inclusionPattern: '**/*.class'
    }
  }
}
