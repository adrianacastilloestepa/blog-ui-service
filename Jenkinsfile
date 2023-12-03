node {
    def WORKSPACE = "/var/lib/jenkins/workspace/blog-ui-service-deploy"
    def dockerImageTag = "blog-ui-service:latest"
try{
    stage('Clone Repo') {
        // for display purposes
        // Get some code from a GitHub repository
        git url: 'https://github.com/adrianacastilloestepa/blog-ui-service.git',
            credentialsId: 'f8879d2f-f6dd-49d7-a882-4b7af4bfc661',
            branch: 'master'
     }
    stage('Build docker') {
         dockerImage = docker.build("blog-ui-service")
    }
    stage('Deploy docker'){
          echo "Docker Image Tag Name: ${dockerImageTag}"
          sh "docker stop blog-ui-service || true && docker rm blog-ui-service || true"
          sh "docker network ls | grep adripoli || docker network create adripoli"
          sh "docker container run --network adripoli -p 8091:8091 --name blog-ui-service -d blog-ui-service"
    }
}catch(e){
    currentBuild.result = "FAILED"
    throw e
}
}

