node {
   def mvnHome

   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/PauloFer1/pf-onboard.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.
      mvnHome = tool 'M3'
   }

   stage('Build') {
      // Run the maven build
      sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
   }

   stage('Tests') {
        sh "'${mvnHome}/bin/mvn' surefire:test"
   }

   stage('Dockerise') {
        sh "'${mvnHome}/bin/mvn' dockerfile:build"
        sh "docker push pfernand/pf-onboard:latest"
   }

   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}